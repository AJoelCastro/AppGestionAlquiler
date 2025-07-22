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
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

/**
 *
 * @author sanar
 */
public class panAutomoviles extends javax.swing.JPanel {

    /**
     * Creates new form panPrestamos
     */
    public panAutomoviles() {
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
        String estado = (String) modelo.getValueAt(filaSeleccionada, 4);  // ← Estado en columna 4
        String garaje = (String) modelo.getValueAt(filaSeleccionada, 5);  // ← Garaje movido a columna 5

        try {
            String disponibilidad = facade.verificarDisponibilidadAutomovil(placa);

            String mensaje = String.format(
                "INFORMACIÓN DEL AUTOMÓVIL:\n\n" +
                "Placa: %s\n" +
                "Modelo: %s\n" +
                "Color: %s\n" +
                "Marca: %s\n" +
                "Estado: %s\n" +
                "Garaje: %s\n\n" +
                "ESTADO ACTUAL: %s",
                placa, modeloAuto, color, marca, estado, garaje, disponibilidad
            );

            // Cambiar el tipo de mensaje según la disponibilidad
            int tipoMensaje = "disponible".equals(disponibilidad) ? 
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
        modelo.addColumn("Estado");      // ← Columna agregada
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
                // Verificar disponibilidad actual del automóvil
                String estadoActual = facade.verificarDisponibilidadAutomovil(auto.getPlaca());

                Object[] fila = {
                    auto.getPlaca(),
                    auto.getModelo(),
                    auto.getColor(),
                    auto.getMarca(),
                    estadoActual != null ? estadoActual : auto.getEstado(), // ← Estado agregado
                    auto.getNombreGaraje()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los automóviles: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarAutomovil() {
        int filaSeleccionada = tblAutomoviles.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un automóvil de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        String placa = (String) modelo.getValueAt(filaSeleccionada, 0);
        String modelo_auto = (String) modelo.getValueAt(filaSeleccionada, 1);
        String color = (String) modelo.getValueAt(filaSeleccionada, 2);
        String marca = (String) modelo.getValueAt(filaSeleccionada, 3);
        String estado = (String) modelo.getValueAt(filaSeleccionada, 4); // ← Corregido índice

        // Verificar disponibilidad antes de eliminar
        String disponibilidad = facade.verificarDisponibilidadAutomovil(placa);

        if ("reserva".equals(disponibilidad)) { // ← Corregido de "en reserva" a "reserva"
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar el automóvil porque está en reserva", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String mensaje = String.format(
            "¿Está seguro de que desea eliminar el siguiente automóvil?\n\n" +
            "Placa: %s\n" +
            "Modelo: %s\n" +
            "Color: %s\n" +
            "Marca: %s\n" +
            "Estado: %s\n\n" +
            "Esta acción no se puede deshacer.",
            placa, modelo_auto, color, marca, estado
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
                boolean exito = facade.eliminarAutomovil(placa);

                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                        "Automóvil eliminado correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarAutomovilesEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo eliminar el automóvil. Verifique que no tenga reservas activas.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar el automóvil: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarAutomovil() {
        int filaSeleccionada = tblAutomoviles.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un automóvil de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblAutomoviles.getModel();
        String placa = (String) modelo.getValueAt(filaSeleccionada, 0);
        String modeloActual = (String) modelo.getValueAt(filaSeleccionada, 1);
        String colorActual = (String) modelo.getValueAt(filaSeleccionada, 2);
        String marcaActual = (String) modelo.getValueAt(filaSeleccionada, 3);
        // El estado está en índice 4, garaje en índice 5

        // Mostrar la placa (no editable)
        JOptionPane.showMessageDialog(this, "Automóvil a actualizar - Placa: " + placa, 
                                    "Información", JOptionPane.INFORMATION_MESSAGE);

        String nuevoModelo = JOptionPane.showInputDialog(this, 
            "Ingrese el nuevo modelo:", modeloActual);

        if (nuevoModelo == null) return;

        String nuevoColor = JOptionPane.showInputDialog(this, 
            "Ingrese el nuevo color:", colorActual);

        if (nuevoColor == null) return;

        String nuevaMarca = JOptionPane.showInputDialog(this, 
            "Ingrese la nueva marca:", marcaActual);

        if (nuevaMarca == null) return;

        if (nuevoModelo.trim().isEmpty() || nuevoColor.trim().isEmpty() || nuevaMarca.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean exito = facade.editarAutomovil(placa, nuevoModelo.trim(), nuevoColor.trim(), nuevaMarca.trim());

            if (exito) {
                JOptionPane.showMessageDialog(this, "Automóvil actualizado correctamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarAutomovilesEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el automóvil. Verifique que la placa exista.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el automóvil: " + e.getMessage(), 
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
                Automovil auto = facade.buscarAutomovilPorPlaca(textoBusqueda.toUpperCase());

                if (auto != null) {
                    String estadoActual = facade.verificarDisponibilidadAutomovil(auto.getPlaca());

                    Object[] fila = {
                        auto.getPlaca(),
                        auto.getModelo(),
                        auto.getColor(),
                        auto.getMarca(),
                        estadoActual != null ? estadoActual : auto.getEstado(), // ← Estado agregado
                        auto.getNombreGaraje()
                    };
                    modelo.addRow(fila);
                    encontrado = true;
                }

            } else if (jRadioButton1.isSelected()) { // Búsqueda por Garaje
                ArrayList<Automovil> todosLosAutomoviles = facade.listarAutomoviles();
                String busquedaLower = textoBusqueda.toLowerCase();

                for (Automovil auto : todosLosAutomoviles) {
                    if (auto.getNombreGaraje() != null && 
                        auto.getNombreGaraje().toLowerCase().contains(busquedaLower)) {

                        String estadoActual = facade.verificarDisponibilidadAutomovil(auto.getPlaca());

                        Object[] fila = {
                            auto.getPlaca(),
                            auto.getModelo(),
                            auto.getColor(),
                            auto.getMarca(),
                            estadoActual != null ? estadoActual : auto.getEstado(), // ← Estado agregado
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
        btnActualizar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnEliminar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnRegistrar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
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

        btnActualizar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        btnActualizar.setText("  Actualizar Automoviles");
        btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("  Eliminar Automoviles");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        btnRegistrar.setText("  Registrar Automoviles");
        btnRegistrar.setActionCommand("Agregar Préstamo");
        btnRegistrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
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
        dspFondo.setLayer(btnActualizar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnEliminar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnRegistrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnListar2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnListar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JInternalFrame ifrm = new ifrmAutomoviles();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarAutomovil();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        cargarAutomovilesEnTabla();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarAutomovil();
    }//GEN-LAST:event_btnActualizarActionPerformed

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
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBusqueda;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnListar1;
    private javax.swing.JButton btnListar2;
    private javax.swing.JButton btnRegistrar;
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
