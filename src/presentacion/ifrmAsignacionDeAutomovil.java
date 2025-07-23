/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import entidades.Automovil;
import entidades.Reserva;
import entidades.ReservaAutomovil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.FacadeAlquiler;

/**
 *
 * @author ArcosArce
 */
public class ifrmAsignacionDeAutomovil extends javax.swing.JInternalFrame {

    /**
     * Creates new form ifrmAsignacionDeAutomovil
     */
    public ifrmAsignacionDeAutomovil() {
        initComponents();
        facade = new FacadeAlquiler();
        cargarReservas();
        configurarTabla();
        cargarAutomoviles();

        tblAutomoviles.getModel().addTableModelListener(e -> {
            if (e.getColumn() == 0) {
                actualizarPreciosSeleccionados();
            } else if (e.getColumn() == 5) { 
                recalcularPrecioPorLitros(e.getFirstRow());
            }
            calcularPrecioTotal();
        });
    }
    private void recalcularPrecioPorLitros(int fila) {
    if (jComboBox1.getSelectedIndex() == -1) return;
    
    Reserva reservaSeleccionada = reservasDisponibles.get(jComboBox1.getSelectedIndex());
        Date fechaInicio = reservaSeleccionada.getFechaInicio().getTime();
        Date fechaFin = reservaSeleccionada.getFechaFin().getTime();

        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        Boolean seleccionado = (Boolean) modelo.getValueAt(fila, 0);

        if (seleccionado != null && seleccionado) {
            Object litrosObj = modelo.getValueAt(fila, 5);
            double litrosIniciales = 0.0;

            if (litrosObj != null) {
                try {
                    litrosIniciales = Double.parseDouble(litrosObj.toString());
                } catch (NumberFormatException e) {
                    litrosIniciales = 0.0;
                }
            }

            double precioCalculado = calcularPrecioAlquiler(fechaInicio, fechaFin, litrosIniciales);
            modelo.setValueAt(precioCalculado, fila, 4);
            calcularPrecioTotal();
        }
    }
    
    private double calcularPrecioAlquiler(Date fechaInicio, Date fechaFin, double litrosIniciales) {
        long diferenciaTiempo = fechaFin.getTime() - fechaInicio.getTime();
        int diasAlquiler = (int) (diferenciaTiempo / (1000 * 60 * 60 * 24));

        if (diasAlquiler <= 0) {
            diasAlquiler = 1;
        }

        double precioBase = diasAlquiler * PRECIO_POR_DIA;

        double precioLitro = 4.50;
        double costoLitros = litrosIniciales * precioLitro;

        return precioBase + costoLitros;
    }
    
    private void actualizarPreciosSeleccionados() {
        if (jComboBox1.getSelectedIndex() == -1) return;

        Reserva reservaSeleccionada = reservasDisponibles.get(jComboBox1.getSelectedIndex());
        Date fechaInicio = reservaSeleccionada.getFechaInicio().getTime();
        Date fechaFin = reservaSeleccionada.getFechaFin().getTime();

        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                Object litrosObj = modelo.getValueAt(i, 5);
                double litrosIniciales = 0.0;

                if (litrosObj != null) {
                    try {
                        litrosIniciales = Double.parseDouble(litrosObj.toString());
                    } catch (NumberFormatException e) {
                        litrosIniciales = 0.0;
                    }
                }

                double precioCalculado = calcularPrecioAlquiler(fechaInicio, fechaFin, litrosIniciales);
                modelo.setValueAt(precioCalculado, i, 4);

                if (litrosObj == null || litrosObj.equals(0.0)) {
                    modelo.setValueAt(20.0, i, 5);
                }
            } else {
                modelo.setValueAt(0.0, i, 4);
                modelo.setValueAt(0.0, i, 5);
            }
        }
    }
    
    private void cargarReservas() {
        reservasDisponibles = facade.listarReservasActivas();
        jComboBox1.removeAllItems();
        
        for (Reserva r : reservasDisponibles) {
            String item = "ID: " + r.getReservaId() + " - " + 
                         r.getNombreCliente() + " (" + r.getNombreAgencia() + ")";
            jComboBox1.addItem(item);
        }
    }
           
    private void configurarTabla() {
        String[] columnas = {"Seleccionar", "Placa", "Modelo", "Marca", "Precio Alquiler", "Litros Inicial"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) return Boolean.class;
                if (column == 4 || column == 5) return Double.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 5;
            }
        };
        tblAutomoviles.setModel(modelo);
    }
    
    private void cargarAutomoviles() {
        automovilesDisponibles = facade.listarAutomoviles();
        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        modelo.setRowCount(0);
        
        for (Automovil auto : automovilesDisponibles) {
            if ("disponible".equals(facade.verificarDisponibilidadAutomovil(auto.getPlaca()))) {
                Object[] fila = {
                    false,
                    auto.getPlaca(),
                    auto.getModelo(),
                    auto.getMarca(),
                    0.0,
                    0.0
                };
                modelo.addRow(fila);
            }
        }
    }
    
    private void calcularPrecioTotal() {
        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        double total = 0.0;
        
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                Object precioObj = modelo.getValueAt(i, 4);
                if (precioObj != null) {
                    try {
                        double precio = Double.parseDouble(precioObj.toString());
                        total += precio;
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        
        jLabel4.setText(String.format("%.2f", total));
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAsignar = new BotonPersonalizado("Inicio",botonMenu,presionadoMenu,encimaMenu);
        jPanel2 = new PanelPersonalizado();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAutomoviles = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Precio total:");

        jLabel4.setText("00.00");

        btnAsignar.setText("Asignar");
        btnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(8, 100, 60));

        tblAutomoviles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAutomoviles);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Seleccione los automoviles:");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Reserva:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(234, 234, 234)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(36, 36, 36)
                .addComponent(btnAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        if (jComboBox1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva", "Error", 0);
            return;
        }

        Reserva reservaSeleccionada = reservasDisponibles.get(jComboBox1.getSelectedIndex());

        ArrayList<ReservaAutomovil> autosAsignados = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modelo.getValueAt(i, 0);
            if (seleccionado != null && seleccionado) {
                String placa = modelo.getValueAt(i, 1).toString();

                try {
                    double precioAlquiler = Double.parseDouble(modelo.getValueAt(i, 4).toString());
                    double litrosInicial = Double.parseDouble(modelo.getValueAt(i, 5).toString());

                    if (litrosInicial < 0) {
                        JOptionPane.showMessageDialog(this, 
                            "Los litros iniciales no pueden ser negativos para el automóvil: " + placa, 
                            "Error de validación", 0);
                        return;
                    }

                    ReservaAutomovil ra = new ReservaAutomovil(
                        reservaSeleccionada.getReservaId(),
                        placa,
                        precioAlquiler,
                        litrosInicial
                    );
                    autosAsignados.add(ra);

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error en los valores numéricos del automóvil: " + placa, 
                        "Error de formato", 0);
                    return;
                }
            }
        }

        if (autosAsignados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un automóvil", "Error", 0);
            return;
        }

        boolean exito = facade.asignarAutomovilesAReserva(reservaSeleccionada.getReservaId(), autosAsignados);

        if (exito) {
            cargarReservas();
            cargarAutomoviles();
            jLabel4.setText("0.00");
        }
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAutomoviles;
    // End of variables declaration//GEN-END:variables
    private FacadeAlquiler facade;
    private ArrayList<Reserva> reservasDisponibles;
    private ArrayList<Automovil> automovilesDisponibles;
    private static final double PRECIO_POR_DIA = 80.0;
    private static final double PRECIO_POR_LITRO = 4.50;
    private Color presionadoMenu = new Color(175,175,175);
    private Color encimaMenu = new Color(200,200,200);
    private Color botonMenu= new Color(255,255,255);
}
