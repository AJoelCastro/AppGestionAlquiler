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
public class panAutomovilesEmpleado extends javax.swing.JPanel {

    /**
     * Creates new form panPrestamos
     */
    public panAutomovilesEmpleado() {
        initComponents();
        facade = new FacadeAlquiler(); // Inicializar el facade
        configurarTabla(); // Configurar las columnas de la tabla
    }
    
    private void verificarDisponibilidadSeleccionado() {
        int filaSeleccionada = tblAutomoviles.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un automóvil de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        String placa = (String) modelo.getValueAt(filaSeleccionada, 0); // Placa está en columna 0
        String modeloAuto = (String) modelo.getValueAt(filaSeleccionada, 1);
        String color = (String) modelo.getValueAt(filaSeleccionada, 2);
        String marca = (String) modelo.getValueAt(filaSeleccionada, 3);
        String garaje = (String) modelo.getValueAt(filaSeleccionada, 4);

        try {
            String disponibilidad = facade.verificarDisponibilidadAutomovil(placa);

            String mensaje = String.format(
                "INFORMACIÓN DEL AUTOMÓVIL:\n\n" +
                "Placa: %s\n" +
                "Modelo: %s\n" +
                "Color: %s\n" +
                "Marca: %s\n" +
                "Garaje: %s\n\n" +
                "ESTADO: %s",
                placa, modeloAuto, color, marca, garaje, disponibilidad
            );

            // Cambiar el tipo de mensaje según la disponibilidad
            int tipoMensaje = "Disponible".equals(disponibilidad) ? 
                             JOptionPane.INFORMATION_MESSAGE : 
                             JOptionPane.WARNING_MESSAGE;

            JOptionPane.showMessageDialog(this, 
                mensaje, 
                "Estado del Automóvil", 
                tipoMensaje);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al verificar la disponibilidad: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
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
        modelo.addColumn("Placa");
        modelo.addColumn("Modelo");
        modelo.addColumn("Color");
        modelo.addColumn("Marca");
        modelo.addColumn("Garaje");
        tblAutomoviles.setModel(modelo);

        tblAutomoviles.setDefaultEditor(Object.class, null);
    }

    private void cargarAutomovilesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Automovil> automoviles = facade.listarAutomoviles();
            for (Automovil auto : automoviles) {
                Object[] fila = {
                    auto.getPlaca(),
                    auto.getModelo(),
                    auto.getColor(),
                    auto.getMarca(),
                    auto.getNombreGaraje()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los automóviles: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void buscarAutomovil() {
        String textoBusqueda = jTextField1.getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un término de búsqueda", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        modelo.setRowCount(0);

        try {
            boolean encontrado = false;

            if (jRadioButton2.isSelected()) { // Búsqueda por ID (placa)
                // Usar el método específico del facade para buscar por placa
                Automovil auto = facade.buscarAutomovilPorPlaca(textoBusqueda.toUpperCase());

                if (auto != null) {
                    Object[] fila = {
                        auto.getPlaca(),
                        auto.getModelo(),
                        auto.getColor(),
                        auto.getMarca(),
                        auto.getNombreGaraje()
                    };
                    modelo.addRow(fila);
                    encontrado = true;
                }

            } else if (jRadioButton1.isSelected()) { // Búsqueda por Garaje
                // Para búsqueda por garaje, necesitamos primero obtener el ID del garaje
                // Esto requiere un método adicional en el facade para buscar garaje por nombre
                // Por ahora, usamos el método existente que lista todos y filtra
                ArrayList<Automovil> todosLosAutomoviles = facade.listarAutomoviles();
                String busquedaLower = textoBusqueda.toLowerCase();

                for (Automovil auto : todosLosAutomoviles) {
                    if (auto.getNombreGaraje() != null && 
                        auto.getNombreGaraje().toLowerCase().contains(busquedaLower)) {
                        Object[] fila = {
                            auto.getPlaca(),
                            auto.getModelo(),
                            auto.getColor(),
                            auto.getMarca(),
                            auto.getNombreGaraje()
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un tipo de búsqueda", 
                                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!encontrado) {
                String tipoBusqueda = jRadioButton2.isSelected() ? "placa" : "garaje";
                JOptionPane.showMessageDialog(this, 
                    "No se encontraron automóviles que coincidan con la " + tipoBusqueda + ": " + textoBusqueda, 
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                cargarAutomovilesEnTabla(); // Mostrar todos los automóviles nuevamente
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar automóviles: " + e.getMessage(), 
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
        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/fondoPrestamos.png"));
        Image image = icon.getImage();
        dspFondo = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        btnListar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnBusqueda = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAutomoviles = new javax.swing.JTable();
        btnListar1 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        btnListar2 = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);

        setBackground(new java.awt.Color(255, 255, 255));

        dspFondo.setBackground(new java.awt.Color(255, 255, 255));

        btnListar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lista.png"))); // NOI18N
        btnListar.setText("  Listar Automoviles");
        btnListar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setText("Control de Automoviles");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    buscarAutomovil();
                }
            }
        });
        jTextField1.setBorder(null);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOpaque(true);

        btnBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        btnBusqueda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        btnBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaActionPerformed(evt);
            }
        });

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

        btnListar1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnListar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/24-em-check.png"))); // NOI18N
        btnListar1.setText("  Verificar Disponibilidad");
        btnListar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListar1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar por:");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Nombre Garaje");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Placa");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        btnListar2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnListar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/coche.png"))); // NOI18N
        btnListar2.setText("   Asignar Automovil ");
        btnListar2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListar2ActionPerformed(evt);
            }
        });

        dspFondo.setLayer(btnListar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnBusqueda, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnListar1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jRadioButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jRadioButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnListar2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dspFondoLayout = new javax.swing.GroupLayout(dspFondo);
        dspFondo.setLayout(dspFondoLayout);
        dspFondoLayout.setHorizontalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(416, 416, 416))
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(143, 143, 143)
                        .addComponent(jRadioButton1)
                        .addGap(140, 140, 140)
                        .addComponent(jRadioButton2))
                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                            .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        dspFondoLayout.setVerticalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(43, 43, 43)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnListar2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaActionPerformed
        buscarAutomovil();
    }//GEN-LAST:event_btnBusquedaActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        cargarAutomovilesEnTabla();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnListar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListar1ActionPerformed
        verificarDisponibilidadSeleccionado();
    }//GEN-LAST:event_btnListar1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void btnListar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListar2ActionPerformed
        JInternalFrame ifrm = new ifrmAsignacionDeAutomovil();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_btnListar2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusqueda;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnListar1;
    private javax.swing.JButton btnListar2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JDesktopPane dspFondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblAutomoviles;
    // End of variables declaration//GEN-END:variables
    private Color botonBlanco = new Color(255,255,255);
    private Color presionadoBuscar = new Color(200,200,200);
    private Color encimaBuscar = new Color(225,225,225);
    private FacadeAlquiler facade;
}
