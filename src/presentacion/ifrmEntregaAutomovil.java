/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import datos.ComprobantePDF;
import entidades.Reserva;
import entidades.ReservaAutomovil;
import entidades.Cliente;
import java.awt.Color;
import logica.FacadeAlquiler;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ArcosArce
 */
public class ifrmEntregaAutomovil extends javax.swing.JInternalFrame {

    /**
     * Creates new form ifrmEntregaAutomovil
     */
    public ifrmEntregaAutomovil() {
        initComponents();
        facade = new FacadeAlquiler();
        configurarTabla();
        cargarReservas();
        configurarEventos();
    }

    private void configurarTabla() {
        String[] columnas = {"Placa", "Modelo", "Marca", "Color", "Precio Alquiler", "Litros Inicial"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(modeloTabla);
    }

    private void cargarReservas() {
        try {
            ArrayList<Reserva> reservasActivas = facade.listarReservasActivas();
            ArrayList<ReservaAutomovil> reservasAuto = facade.listarReservaAutomovil();

            reservasConAutomoviles = new ArrayList<>();

            for (Reserva reserva : reservasActivas) {
                if (!reserva.isEntregado()) { 
                    boolean tieneAutos = false;
                    for (ReservaAutomovil ra : reservasAuto) {
                        if (ra.getReservaId() == reserva.getReservaId()) {
                            tieneAutos = true;
                            break;
                        }
                    }
                    if (tieneAutos) {
                        reservasConAutomoviles.add(reserva);
                    }
                }
            }

            jComboBox1.removeAllItems();
            for (Reserva reserva : reservasConAutomoviles) {
                String item = "ID: " + reserva.getReservaId() + " - " + 
                             reserva.getNombreCliente() + " (" + reserva.getNombreAgencia() + ")";
                jComboBox1.addItem(item);
            }

            if (!reservasConAutomoviles.isEmpty()) {
                cargarDetallesReserva(0);
            } else {
                limpiarFormulario();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage(), "Error", 0);
        }
    }

    private void cargarDetallesReserva(int indiceSeleccionado) {
        if (indiceSeleccionado >= 0 && indiceSeleccionado < reservasConAutomoviles.size()) {
            Reserva reservaSeleccionada = reservasConAutomoviles.get(indiceSeleccionado);
            
            Cliente cliente = facade.buscarClientePorId(reservaSeleccionada.getClienteId());
            if (cliente != null) {
                jLabel3.setText(cliente.getNombre());
            } else {
                jLabel3.setText("Cliente no encontrado");
            }

            cargarAutomovilesReserva(reservaSeleccionada.getReservaId());
        }
    }

    private void cargarAutomovilesReserva(int reservaId) {
        try {
            modeloTabla.setRowCount(0);
            
            ArrayList<ReservaAutomovil> reservasAuto = facade.listarReservaAutomovil();
            double precioTotal = 0.0;

            for (ReservaAutomovil ra : reservasAuto) {
                if (ra.getReservaId() == reservaId) {
                    Object[] fila = new Object[6];
                    fila[0] = ra.getPlaca();
                    
                    var automovil = facade.buscarAutomovilPorPlaca(ra.getPlaca());
                    if (automovil != null) {
                        fila[1] = automovil.getModelo();
                        fila[2] = automovil.getMarca();
                        fila[3] = automovil.getColor();
                    } else {
                        fila[1] = "N/A";
                        fila[2] = "N/A";
                        fila[3] = "N/A";
                    }
                    
                    fila[4] = String.format("%.2f", ra.getPrecioAlquiler());
                    fila[5] = String.format("%.2f", ra.getLitrosInicial());
                    
                    modeloTabla.addRow(fila);
                    precioTotal += ra.getPrecioAlquiler();
                }
            }

            jLabel7.setText(String.format("%.2f", precioTotal));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar automóviles: " + e.getMessage(), "Error", 0);
        }
    }

    private void limpiarFormulario() {
        jLabel3.setText("N/N");
        modeloTabla.setRowCount(0);
        jLabel7.setText("0.00");
    }

    private void realizarPago() {
        int indiceSeleccionado = jComboBox1.getSelectedIndex();
        if (indiceSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva", "Error", 0);
            return;
        }

        Reserva reservaSeleccionada = reservasConAutomoviles.get(indiceSeleccionado);

        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Confirma realizar el pago y marcar la reserva como entregada?\n" +
            "Precio total: $" + jLabel7.getText(), 
            "Confirmar Pago", 
            JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                boolean estadoCambiado = facade.cambiarEstadoReserva(reservaSeleccionada.getReservaId(), true);

                if (estadoCambiado) {

                    JOptionPane.showMessageDialog(this, 
                        "Pago realizado exitosamente.\nReserva marcada como entregada.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);

                    cargarReservas();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Error al procesar el pago", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al realizar el pago: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarEventos() {
        jComboBox1.addActionListener(e -> {
            int indiceSeleccionado = jComboBox1.getSelectedIndex();
            if (indiceSeleccionado >= 0) {
                cargarDetallesReserva(indiceSeleccionado);
            }
        });

        jButton2.addActionListener(e -> realizarPago());

        jButton1.addActionListener(e -> generarOrdenPago());
    }

    private void generarOrdenPago() {
        int indiceSeleccionado = jComboBox1.getSelectedIndex();
        if (indiceSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Reserva reservaSeleccionada = reservasConAutomoviles.get(indiceSeleccionado);
            Cliente cliente = facade.buscarClientePorId(reservaSeleccionada.getClienteId());

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<ReservaAutomovil> autosReserva = new ArrayList<>();
            ArrayList<ReservaAutomovil> todasReservasAuto = facade.listarReservaAutomovil();

            for (ReservaAutomovil ra : todasReservasAuto) {
                if (ra.getReservaId() == reservaSeleccionada.getReservaId()) {
                    autosReserva.add(ra);
                }
            }

            if (autosReserva.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron automóviles para esta reserva", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean comprobanteGenerado = ComprobantePDF.generarComprobante(
                reservaSeleccionada,
                cliente,
                autosReserva,
                facade
            );

            if (comprobanteGenerado) {
                JOptionPane.showMessageDialog(this, 
                    "Orden de pago generada exitosamente.\nArchivo: comprobante_alquiler_" + reservaSeleccionada.getReservaId() + ".pdf", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al generar la orden de pago", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar orden de pago: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para debug
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new PanelPersonalizado();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new BotonPersonalizado("Inicio",botonMenu,presionadoMenu,encimaMenu);
        jButton2 = new BotonPersonalizado("Inicio",botonMenu,presionadoMenu,encimaMenu);

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Precio Total:");

        jLabel7.setText("00.00");

        jPanel2.setBackground(new java.awt.Color(8, 100, 60));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Automoviles asignados:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre del cliente:");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Seleccione una reserva:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("N/N");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(205, 205, 205)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jButton1.setText("Generar orden de pago");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Realizar Pago");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        generarOrdenPago();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        realizarPago();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private FacadeAlquiler facade;
    private ArrayList<Reserva> reservasConAutomoviles;
    private DefaultTableModel modeloTabla;
    private Color presionadoMenu = new Color(150,150,150);
    private Color encimaMenu = new Color(175,175,175);
    private Color botonMenu= new Color(200,200,200);
}
