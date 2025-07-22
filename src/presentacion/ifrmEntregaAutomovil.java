/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import datos.ComprobantePDF;
import entidades.Reserva;
import entidades.ReservaAutomovil;
import entidades.Cliente;
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
                return false; // Hacer la tabla no editable
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
                    // Obtener detalles del automóvil
                    Object[] fila = new Object[6];
                    fila[0] = ra.getPlaca();
                    
                    // Buscar detalles del automóvil
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

        jToggleButton1.addActionListener(e -> realizarPago());

        jToggleButton2.addActionListener(e -> generarOrdenPago());
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
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Seleccione una reserva:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Nombre del cliente:");

        jLabel3.setText("N/N");

        jLabel4.setText("Automoviles asignados:");

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

        jLabel6.setText("Precio Total:");

        jLabel7.setText("00.00");

        jToggleButton1.setText("Realizar Pago");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Generar orden de pago");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(205, 205, 205)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
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

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        realizarPago();
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        generarOrdenPago();
    }//GEN-LAST:event_jToggleButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    // End of variables declaration//GEN-END:variables
    private FacadeAlquiler facade;
    private ArrayList<Reserva> reservasConAutomoviles;
    private DefaultTableModel modeloTabla;
}
