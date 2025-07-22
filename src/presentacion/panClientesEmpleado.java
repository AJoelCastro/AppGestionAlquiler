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
public class panClientesEmpleado extends javax.swing.JPanel {

    /**
     * Creates new form panPrestamos
     */
    public panClientesEmpleado() {
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
        modelo.addColumn("DNI");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Sponsor");
        tblClientes.setModel(modelo);

        tblClientes.setDefaultEditor(Object.class, null);
    }

    private void cargarClientesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Cliente> clientes = facade.listarClientes(); // Usar facade
            if (clientes != null && !clientes.isEmpty()) {
                for (Cliente cliente : clientes) {
                    Object[] fila = {
                        cliente.getIdCliente(),
                        cliente.getDni(),
                        cliente.getNombre(),
                        cliente.getDireccion(),
                        cliente.getTelefono(),
                        cliente.getSponsor() != null ? cliente.getSponsor() : "Sin sponsor"
                    };
                    modelo.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay clientes registrados", 
                                            "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los clientes: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarCliente() {
        int filaSeleccionada = tblClientes.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        int idCliente = (int) modelo.getValueAt(filaSeleccionada, 0);
        String dniActual = (String) modelo.getValueAt(filaSeleccionada, 1);
        String nombreActual = (String) modelo.getValueAt(filaSeleccionada, 2);
        String direccionActual = (String) modelo.getValueAt(filaSeleccionada, 3);
        String telefonoActual = (String) modelo.getValueAt(filaSeleccionada, 4);

        // Crear un panel personalizado para el diálogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtNuevoNombre = new JTextField(nombreActual, 20);
        JTextField txtNuevaDireccion = new JTextField(direccionActual, 20);
        JTextField txtNuevoTelefono = new JTextField(telefonoActual, 20);

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNuevoNombre);
        panel.add(new JLabel("Dirección:"));
        panel.add(txtNuevaDireccion);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtNuevoTelefono);

        String mensaje = String.format(
            "Actualizar datos del cliente:\n" +
            "ID: %d - DNI: %s\n\n" +
            "Modifique los campos que desee actualizar:",
            idCliente, dniActual
        );

        int opcion = JOptionPane.showConfirmDialog(
            this,
            new Object[]{mensaje, panel},
            "Actualizar Cliente",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNuevoNombre.getText().trim();
            String nuevaDireccion = txtNuevaDireccion.getText().trim();
            String nuevoTelefono = txtNuevoTelefono.getText().trim();

            // Validaciones básicas
            if (nuevoNombre.isEmpty() || nuevaDireccion.isEmpty() || nuevoTelefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                boolean exito = BLCliente.editarCliente(idCliente, nuevoNombre, nuevaDireccion, nuevoTelefono);

                if (exito) {
                    // Recargar la tabla inmediatamente después de la actualización exitosa
                    cargarClientesEnTabla();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al actualizar el cliente: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void buscarCliente() {
        String textoBusqueda = txtBusqueda.getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un término de búsqueda", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Cliente> todasLosClientes = facade.listarClientes(); // Usar facade
            boolean encontrado = false;

            try {
                // Buscar por ID
                int idBusqueda = Integer.parseInt(textoBusqueda);
                for (Cliente cliente : todasLosClientes) {
                    if (cliente.getIdCliente() == idBusqueda) {
                        Object[] fila = {
                            cliente.getIdCliente(),
                            cliente.getDni(),
                            cliente.getNombre(),
                            cliente.getDireccion(),
                            cliente.getTelefono(),
                            cliente.getSponsor() != null ? cliente.getSponsor() : "Sin sponsor"
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Buscar por texto (nombre, dirección, DNI)
                String busquedaLower = textoBusqueda.toLowerCase();
                for (Cliente cliente : todasLosClientes) {
                    if (cliente.getNombre().toLowerCase().contains(busquedaLower) || 
                        cliente.getDireccion().toLowerCase().contains(busquedaLower) ||
                        cliente.getDni().toLowerCase().contains(busquedaLower)) {
                        Object[] fila = {
                            cliente.getIdCliente(),
                            cliente.getDni(),
                            cliente.getNombre(),
                            cliente.getDireccion(),
                            cliente.getTelefono(),
                            cliente.getSponsor() != null ? cliente.getSponsor() : "Sin sponsor"
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                    }
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "No se encontraron clientes que coincidan con: " + textoBusqueda, 
                                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                cargarClientesEnTabla();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar clientes: " + e.getMessage(), 
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
        btnListar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnActualizar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnRegistrar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jLabel1 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnBusqueda = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        dspFondo.setBackground(new java.awt.Color(255, 255, 255));

        btnListar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lista.png"))); // NOI18N
        btnListar.setText("  Listar Clientes");
        btnListar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        btnActualizar.setText("  Actualizar Clientes");
        btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        btnRegistrar.setText("  Registrar Clientes");
        btnRegistrar.setActionCommand("Agregar Préstamo");
        btnRegistrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setText("Control de Clientes");

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    buscarCliente();
                }
            }
        });
        txtBusqueda.setBorder(null);

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

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblClientes);

        dspFondo.setLayer(btnListar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnActualizar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnRegistrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(txtBusqueda, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnBusqueda, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dspFondoLayout = new javax.swing.GroupLayout(dspFondo);
        dspFondo.setLayout(dspFondoLayout);
        dspFondoLayout.setHorizontalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dspFondoLayout.createSequentialGroup()
                        .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator1)
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
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
                    .addComponent(btnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(80, 80, 80)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        buscarCliente();
    }//GEN-LAST:event_btnBusquedaActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JInternalFrame ifrm = new ifrmCliente();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        cargarClientesEnTabla();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarCliente();
    }//GEN-LAST:event_btnActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBusqueda;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JDesktopPane dspFondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
    private Color botonBlanco = new Color(255,255,255);
    private Color presionadoBuscar = new Color(200,200,200);
    private Color encimaBuscar = new Color(225,225,225);
    private FacadeAlquiler facade;
}
