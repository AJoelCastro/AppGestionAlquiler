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
public class panAgencias extends javax.swing.JPanel {

    /**
     * Creates new form panPrestamos
     */
    public panAgencias() {
        initComponents();
        facade = new FacadeAlquiler();
        configurarTabla();
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
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        tblAgencias.setModel(modelo);

        tblAgencias.setDefaultEditor(Object.class, null);
    }

    private void cargarAgenciasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblAgencias.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Agencia> agencias = facade.listarAgencias();
            for (Agencia agencia : agencias) {
                Object[] fila = {
                    agencia.getAgenciaId(),
                    agencia.getNombre(),
                    agencia.getDireccion()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las agencias: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarAgencia() {
        int filaSeleccionada = tblAgencias.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una agencia de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAgencias.getModel();
        int idAgencia = (int) modelo.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) modelo.getValueAt(filaSeleccionada, 1);
        String direccionActual = (String) modelo.getValueAt(filaSeleccionada, 2);

        String nuevoNombre = JOptionPane.showInputDialog(this, 
            "Ingrese el nuevo nombre:", nombreActual);

        if (nuevoNombre == null) return;

        String nuevaDireccion = JOptionPane.showInputDialog(this, 
            "Ingrese la nueva dirección:", direccionActual);

        if (nuevaDireccion == null) return;

        if (nuevoNombre.trim().isEmpty() || nuevaDireccion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean exito = facade.editarAgencia(idAgencia, nuevoNombre.trim(), nuevaDireccion.trim());

            if (exito) {
                JOptionPane.showMessageDialog(this, "Agencia actualizada correctamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarAgenciasEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar la agencia. Verifique que el ID exista.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la agencia: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void eliminarAgencia() {
        int filaSeleccionada = tblAgencias.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una agencia de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAgencias.getModel();
        int idAgencia = (int) modelo.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modelo.getValueAt(filaSeleccionada, 1);
        String direccion = (String) modelo.getValueAt(filaSeleccionada, 2);

        String mensaje = String.format(
            "¿Está seguro de que desea eliminar la siguiente agencia?\n\n" +
            "ID: %d\n" +
            "Nombre: %s\n" +
            "Dirección: %s\n\n" +
            "Esta acción no se puede deshacer.",
            idAgencia, nombre, direccion
        );

        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                boolean exito = facade.eliminarAgencia(idAgencia);

                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                        "Agencia eliminada correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarAgenciasEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo eliminar la agencia. Verifique que no tenga registros relacionados.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar la agencia: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void buscarAgencia() {
        String textoBusqueda = jTextField1.getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un término de búsqueda", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAgencias.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Agencia> todasLasAgencias = facade.listarAgencias();
            boolean encontrado = false;

            try {
                int idBusqueda = Integer.parseInt(textoBusqueda);
                for (Agencia agencia : todasLasAgencias) {
                    if (agencia.getAgenciaId() == idBusqueda) {
                        Object[] fila = {
                            agencia.getAgenciaId(),
                            agencia.getNombre(),
                            agencia.getDireccion()
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                String busquedaLower = textoBusqueda.toLowerCase();
                for (Agencia agencia : todasLasAgencias) {
                    if (agencia.getNombre().toLowerCase().contains(busquedaLower) || 
                        agencia.getDireccion().toLowerCase().contains(busquedaLower)) {
                        Object[] fila = {
                            agencia.getAgenciaId(),
                            agencia.getNombre(),
                            agencia.getDireccion()
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                    }
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "No se encontraron agencias que coincidan con: " + textoBusqueda, 
                                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                cargarAgenciasEnTabla();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar agencias: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondoPrestamos.png"));
        Image image = icon.getImage();
        dspFondo = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jButton4 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jButton5 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jButton3 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jButton2 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAgencias = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        dspFondo.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lista.png"))); // NOI18N
        jButton4.setText("  Listar Agencia");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        jButton5.setText("  Actualizar Agencia");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jButton3.setText("  Eliminar Agencia");
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        jButton2.setText("  Agregar Agencia");
        jButton2.setActionCommand("Agregar Préstamo");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setText("Control de Agencias");

        jTextField1.setBorder(null);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOpaque(true);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    buscarAgencia();
                }
            }
        });
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblAgencias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAgencias);

        dspFondo.setLayer(jButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dspFondoLayout = new javax.swing.GroupLayout(dspFondo);
        dspFondo.setLayout(dspFondoLayout);
        dspFondoLayout.setHorizontalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                        .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(196, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(416, 416, 416))
        );
        dspFondoLayout.setVerticalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        buscarAgencia();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JInternalFrame ifrm = new ifrmAgencia();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        eliminarAgencia();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cargarAgenciasEnTabla();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        actualizarAgencia();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JDesktopPane dspFondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblAgencias;
    // End of variables declaration//GEN-END:variables
    private Color botonBlanco = new Color(255,255,255);
    private Color presionadoBuscar = new Color(200,200,200);
    private Color encimaBuscar = new Color(225,225,225);
    private FacadeAlquiler facade;
}
