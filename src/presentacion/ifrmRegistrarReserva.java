/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;
import datos.*;
import entidades.*;
import logica.*;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
/**
 *
 * @author Asus
 */
public class ifrmRegistrarReserva extends javax.swing.JInternalFrame {

    /**
     * Creates new form ifrmname
     */
    public ifrmRegistrarReserva() {
        initComponents();
        initComponentsCustom();
    }
    private void initComponentsCustom() {
        facade = new FacadeAlquiler();

        dateInicio = new JDateChooser();
        dateFin = new JDateChooser();

        timeInicioHora = new JSpinner(new SpinnerNumberModel(9, 0, 23, 1)); 
        timeInicioMinuto = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
        timeFinHora = new JSpinner(new SpinnerNumberModel(17, 0, 23, 1));
        timeFinMinuto = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));

        dateInicio.setBounds(147, 120, 140, 22);
        timeInicioHora.setBounds(295, 120, 45, 22);
        timeInicioMinuto.setBounds(350, 120, 45, 22);

        dateFin.setBounds(147, 150, 140, 22);
        timeFinHora.setBounds(295, 150, 45, 22);
        timeFinMinuto.setBounds(350, 150, 45, 22);

        jPanel1.add(dateInicio);
        jPanel1.add(timeInicioHora);
        jPanel1.add(timeInicioMinuto);
        jPanel1.add(dateFin);
        jPanel1.add(timeFinHora);
        jPanel1.add(timeFinMinuto);

        javax.swing.JLabel lblHoraInicio = new javax.swing.JLabel(":");
        lblHoraInicio.setForeground(new java.awt.Color(255, 255, 255));
        lblHoraInicio.setBounds(342, 120, 10, 22);
        jPanel1.add(lblHoraInicio);

        javax.swing.JLabel lblHoraFin = new javax.swing.JLabel(":");
        lblHoraFin.setForeground(new java.awt.Color(255, 255, 255));
        lblHoraFin.setBounds(342, 150, 10, 22);
        jPanel1.add(lblHoraFin);

        javax.swing.JLabel lblHora = new javax.swing.JLabel("Hora");
        lblHora.setForeground(new java.awt.Color(255, 255, 255));
        lblHora.setFont(new java.awt.Font("Dialog", 0, 10));
        lblHora.setBounds(315, 100, 30, 15);
        jPanel1.add(lblHora);

        javax.swing.JLabel lblMin = new javax.swing.JLabel("Min");
        lblMin.setForeground(new java.awt.Color(255, 255, 255));
        lblMin.setFont(new java.awt.Font("Dialog", 0, 10));
        lblMin.setBounds(365, 100, 25, 15);
        jPanel1.add(lblMin);

        cargarClientes();
        cargarAgencias();
        activar(false);
    }
    private void cargarClientes() {
        try {
            ArrayList<Cliente> clientes = facade.listarClientes();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione un cliente...");

            for (Cliente cliente : clientes) {
                model.addElement(cliente.getIdCliente() + " - " + cliente.getNombre());
            }
            jComboBox1.setModel(model);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al cargar clientes: " + e.getMessage(), 
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarAgencias() {
        try {
            ArrayList<Agencia> agencias = facade.listarAgencias();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Seleccione una agencia...");

            for (Agencia agencia : agencias) {
                model.addElement(agencia.getAgenciaId() + " - " + agencia.getNombre());
            }
            jComboBox2.setModel(model);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al cargar agencias: " + e.getMessage(), 
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void activar(boolean estado) {
        jComboBox1.setEnabled(estado);
        jComboBox2.setEnabled(estado);
        dateInicio.setEnabled(estado);
        dateFin.setEnabled(estado);
        timeInicioHora.setEnabled(estado);
        timeInicioMinuto.setEnabled(estado);
        timeFinHora.setEnabled(estado);
        timeFinMinuto.setEnabled(estado);
        btnRegistrar.setEnabled(estado);
    }

    private void limpiar() {
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        dateInicio.setDate(null);
        dateFin.setDate(null);
        timeInicioHora.setValue(9);
        timeInicioMinuto.setValue(0);
        timeFinHora.setValue(17);
        timeFinMinuto.setValue(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    @Override
    public void dispose() {
        super.dispose(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnSalir = new BotonPersonalizado("Inicio",botonMenu,presionadoMenu,encimaMenu);
        btnNuevo = new BotonPersonalizado("Inicio",botonMenu,presionadoMenu,encimaMenu);
        btnRegistrar = new BotonPersonalizado("Inicio",botonMenu,presionadoMenu,encimaMenu);
        jPanel1 = new PanelPersonalizado();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lblDniCliente = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblAgencia = new javax.swing.JLabel();

        setTitle("Registro de Reservas");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/24-em-cross.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/24-em-plus.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.gif"))); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.setEnabled(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(8, 100, 60));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha Fin:");

        lblDniCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblDniCliente.setText("Cliente:");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fecha de inicio:");

        lblAgencia.setForeground(new java.awt.Color(255, 255, 255));
        lblAgencia.setText("Agencia:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblDniCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAgencia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2, 0, 192, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        activar(true);
        limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
            try {
            String clienteSeleccionado = (String) jComboBox1.getSelectedItem();
            if (clienteSeleccionado == null || clienteSeleccionado.equals("Seleccione un cliente...")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente");
                return;
            }
            int clienteId = Integer.parseInt(clienteSeleccionado.split(" - ")[0]);

            String agenciaSeleccionada = (String) jComboBox2.getSelectedItem();
            if (agenciaSeleccionada == null || agenciaSeleccionada.equals("Seleccione una agencia...")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar una agencia");
                return;
            }
            int agenciaId = Integer.parseInt(agenciaSeleccionada.split(" - ")[0]);

            if (dateInicio.getDate() == null || dateFin.getDate() == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar las fechas de inicio y fin");
                return;
            }

            GregorianCalendar fechaInicio = new GregorianCalendar();
            fechaInicio.setTime(dateInicio.getDate());
            fechaInicio.set(Calendar.HOUR_OF_DAY, (Integer) timeInicioHora.getValue());
            fechaInicio.set(Calendar.MINUTE, (Integer) timeInicioMinuto.getValue());
            fechaInicio.set(Calendar.SECOND, 0);

            GregorianCalendar fechaFin = new GregorianCalendar();
            fechaFin.setTime(dateFin.getDate());
            fechaFin.set(Calendar.HOUR_OF_DAY, (Integer) timeFinHora.getValue());
            fechaFin.set(Calendar.MINUTE, (Integer) timeFinMinuto.getValue());
            fechaFin.set(Calendar.SECOND, 0);

            boolean exito = facade.registrarReserva(clienteId, agenciaId, fechaInicio, fechaFin);

            if (exito) {
                activar(false);
                limpiar();
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al registrar la reserva: " + e.getMessage(), 
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAgencia;
    private javax.swing.JLabel lblDniCliente;
    // End of variables declaration//GEN-END:variables
    // Añadir después de las variables declaration
    private JDateChooser dateInicio;
    private JDateChooser dateFin;
    private FacadeAlquiler facade;
    private JSpinner timeInicioHora;
    private JSpinner timeInicioMinuto;
    private JSpinner timeFinHora;
    private JSpinner timeFinMinuto;
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {
        String seleccion = (String) jComboBox2.getSelectedItem();
        if (seleccion != null && !seleccion.equals("Seleccione una agencia...")) {
            System.out.println("Agencia seleccionada: " + seleccion);
        }
    }
    private Color presionadoMenu = new Color(175,175,175);
    private Color encimaMenu = new Color(200,200,200);
    private Color botonMenu= new Color(255,255,255);
}
