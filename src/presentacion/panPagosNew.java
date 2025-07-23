/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package presentacion;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import datos.*;
import entidades.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;
import logica.*;


/**
 *
 * @author sanar
 */
public class panPagosNew extends javax.swing.JPanel {

    /**
     * Creates new form panPrestamos
     */
    public panPagosNew() {
        initComponents();
        facade = new FacadeAlquiler();
        cargarAgencias();
        cargarMesesYAnios();
    }
    
    private void cargarMesesYAnios() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cmbMeses.removeAllItems();
        // Remover esta línea: cmbMeses.addItem("Seleccionar mes");
        for (String mes : meses) {
            cmbMeses.addItem(mes);
        }

        cmbAnios.removeAllItems();
        // Remover esta línea: cmbAnios.addItem("Seleccionar año");
        int anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = anioActual - 5; i <= anioActual + 2; i++) {
            cmbAnios.addItem(String.valueOf(i));
        }

        int mesActual = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        cmbMeses.setSelectedIndex(mesActual); // Cambiar de mesActual + 1 a mesActual
        cmbAnios.setSelectedItem(String.valueOf(anioActual));
    }
    
    private void centrarInternalFrame (JInternalFrame interna) {
        int x,y;
        
        x=dspFondo.getWidth()/2 - interna.getWidth()/2;
        y=dspFondo.getHeight()/2- interna.getHeight()/2;
        if(interna.isShowing())
        interna.setLocation(x,y);
        
        else {
            dspFondo.add(interna);
            interna.setLocation(x,y);
            interna.show();
        };
        
    }
    
    private void cargarAgencias() {
        cmbAgencias.removeAllItems();
        // Remover esta línea: cmbAgencias.addItem("Agencias");
        ArrayList<Agencia> agencias = facade.listarAgencias();
        for (Agencia ag : agencias) {
            cmbAgencias.addItem(ag.getNombre());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondoPrestamos.png"));
        Image image = icon.getImage();
        dspFondo = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        btnEliminar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnRegistrar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        jPanel1 = new PanelPersonalizado();
        btnGenerarReporte = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        cmbAnios = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbMeses = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbAgencias = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        dspFondo.setBackground(new java.awt.Color(255, 255, 255));

        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("  Devolucion Automovil");
        btnEliminar.setToolTipText("");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        btnRegistrar.setText("  Pago/Entrega Automovil");
        btnRegistrar.setToolTipText("");
        btnRegistrar.setActionCommand("Agregar Préstamo");
        btnRegistrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setText("Control de Pagos");

        tblReservas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblReservas);

        jPanel1.setBackground(new java.awt.Color(8, 100, 60));

        btnGenerarReporte.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnGenerarReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lista.png"))); // NOI18N
        btnGenerarReporte.setText("Generar reporte");
        btnGenerarReporte.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporteActionPerformed(evt);
            }
        });

        cmbAnios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Año" }));
        cmbAnios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAniosActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Año:");

        cmbMeses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mes" }));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mes:");

        cmbAgencias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agencias" }));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Agencias:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnGenerarReporte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbAgencias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbMeses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbAnios, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbAgencias, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbAnios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jLabel5.setText("Generar reporte de ingresos:");

        dspFondo.setLayer(btnEliminar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnRegistrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dspFondoLayout = new javax.swing.GroupLayout(dspFondo);
        dspFondo.setLayout(dspFondoLayout);
        dspFondoLayout.setHorizontalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(493, 493, 493))
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(75, 75, 75)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(191, Short.MAX_VALUE))
        );
        dspFondoLayout.setVerticalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addGap(75, 75, 75)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dspFondo)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dspFondo, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        JInternalFrame ifrm = new ifrmDevolucionAutomovil1();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JInternalFrame ifrm = new ifrmEntregaAutomovil();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        String agencia = (String) cmbAgencias.getSelectedItem();
        String mesSeleccionado = (String) cmbMeses.getSelectedItem();
        String anioSeleccionado = (String) cmbAnios.getSelectedItem();

        if (agencia == null || mesSeleccionado == null || anioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener selecciones.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, Integer.parseInt(anioSeleccionado));
        cal.set(java.util.Calendar.MONTH, cmbMeses.getSelectedIndex());
        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
        java.util.Date fecha = cal.getTime();

        String[] columnas = {"Concepto", "Valor"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        double ingresos = facade.obtenerIngresosPorMesAgencia(agencia, fecha);
        String fechaFormateada = mesSeleccionado + " " + anioSeleccionado;

        modelo.addRow(new Object[]{"Agencia", agencia});
        modelo.addRow(new Object[]{"Período", fechaFormateada});
        modelo.addRow(new Object[]{"Ingresos Totales", "$" + String.format("%.2f", ingresos)});

        tblReservas.setModel(modelo);

        JOptionPane.showMessageDialog(this, 
            "Reporte generado exitosamente.\nIngresos totales: $" + String.format("%.2f", ingresos), 
            "Reporte Generado", 
            JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnGenerarReporteActionPerformed

    private void cmbAniosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAniosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAniosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbAgencias;
    private javax.swing.JComboBox<String> cmbAnios;
    private javax.swing.JComboBox<String> cmbMeses;
    private javax.swing.JDesktopPane dspFondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReservas;
    // End of variables declaration//GEN-END:variables
    private Color botonBlanco = new Color(255,255,255);
    private Color presionadoBuscar = new Color(200,200,200);
    private Color encimaBuscar = new Color(225,225,225);
    private FacadeAlquiler facade;
}
