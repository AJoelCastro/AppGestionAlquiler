/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package presentacion;

import datos.Conexion;
import entidades.Automovil;
import entidades.Cliente;
import entidades.Reserva;
import entidades.ReservaAutomovil;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.FacadeAlquiler;
import entidades.Reserva;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author ArcosArce
 */
public class ifrmDevolucionAutomovil1 extends javax.swing.JInternalFrame {

    /**
     * Creates new form ifrmEntregaAutomovil
     */
    public ifrmDevolucionAutomovil1() {
        initComponents();
        facade = new FacadeAlquiler();
        inicializarComponentes(); 
        cargarReservasEntregadas();
    }

    private void inicializarComponentes() {
        String[] columnas = {"Placa", "Modelo", "Marca", "Color", "Precio Alquiler", "Litros Inicial", "Litros Final"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        jTable1.setModel(modeloTabla);

        jComboBox1.addActionListener(e -> cargarDatosReserva());

        jLabel3.setText("N/N");
        jLabel5.setText("Fecha de entrega: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        jLabel7.setText("0.00");
        jLabel8.setText("Fecha Fin de reserva: N/N");

        jButton1.setText("Realizar Devolución");
        jButton1.setEnabled(false);

        jButton2.setText("Pagar Multa y Devolver");
        jButton2.setEnabled(false);
    }

    private void cargarReservasEntregadas() {
        try {
            ArrayList<Reserva> todasReservas = facade.listarReservas();
            reservasEntregadas = new ArrayList<>();
            
            for (Reserva reserva : todasReservas) {
                if (reserva.isEntregado()) {
                    ArrayList<ReservaAutomovil> autos = obtenerAutomovilesDeReserva(reserva.getReservaId());
                    if (!autos.isEmpty()) {
                        reservasEntregadas.add(reserva);
                    }
                }
            }

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            modelo.addElement("Seleccione una reserva...");
            
            for (Reserva reserva : reservasEntregadas) {
                String item = String.format("ID: %d - Cliente: %s - Agencia: %s", 
                    reserva.getReservaId(), 
                    reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente " + reserva.getClienteId(),
                    reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia " + reserva.getAgenciaId());
                modelo.addElement(item);
            }
            
            jComboBox1.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosReserva() {
        int indice = jComboBox1.getSelectedIndex();

        if (indice <= 0) {
            limpiarFormulario();
            return;
        }

        try {
            reservaSeleccionada = reservasEntregadas.get(indice - 1);
            clienteSeleccionado = facade.buscarClientePorId(reservaSeleccionada.getClienteId());

            if (clienteSeleccionado != null) {
                jLabel3.setText(clienteSeleccionado.getNombre());
            } else {
                jLabel3.setText("Cliente " + reservaSeleccionada.getClienteId());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            jLabel8.setText("Fecha Fin de reserva: " + sdf.format(reservaSeleccionada.getFechaFin().getTime()));

            cargarAutomovilesReserva();

            calcularMulta();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de la reserva: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
        }
    }

    private void cargarAutomovilesReserva() {
        modeloTabla.setRowCount(0);
        automovilesReserva = obtenerAutomovilesDeReserva(reservaSeleccionada.getReservaId());
        
        for (ReservaAutomovil ra : automovilesReserva) {
            Automovil auto = facade.buscarAutomovilPorPlaca(ra.getPlaca());
            
            Object[] fila = {
                ra.getPlaca(),
                auto != null ? auto.getModelo() : "N/D",
                auto != null ? auto.getMarca() : "N/D", 
                auto != null ? auto.getColor() : "N/D",
                String.format("%.2f", ra.getPrecioAlquiler()),
                String.format("%.2f", ra.getLitrosInicial()),
                "" 
            };
            modeloTabla.addRow(fila);
        }
    }

    private ArrayList<ReservaAutomovil> obtenerAutomovilesDeReserva(int reservaId) {
        ArrayList<ReservaAutomovil> resultado = new ArrayList<>();
        ArrayList<ReservaAutomovil> todasReservasAuto = facade.listarReservaAutomovil();
        
        for (ReservaAutomovil ra : todasReservasAuto) {
            if (ra.getReservaId() == reservaId) {
                resultado.add(ra);
            }
        }
        
        return resultado;
    }

    private void calcularMulta() {
        Date fechaActual = new Date();
        Date fechaFinReserva = reservaSeleccionada.getFechaFin().getTime();

        if (fechaActual.after(fechaFinReserva)) {
            long diferenciaMilisegundos = fechaActual.getTime() - fechaFinReserva.getTime();
            int diasRetraso = (int) Math.ceil(diferenciaMilisegundos / (1000.0 * 60 * 60 * 24));

            multaCalculada = diasRetraso * MULTA_POR_DIA;
            jLabel7.setText(String.format("%.2f", multaCalculada));

            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
        } else {
            multaCalculada = 0.0;
            jLabel7.setText("0.00");

            jButton1.setEnabled(true);
            jButton2.setEnabled(false);
        }
    }

    private void limpiarFormulario() {
        jLabel3.setText("N/N");
        jLabel8.setText("Fecha Fin de reserva: N/N");
        jLabel7.setText("0.00");
        modeloTabla.setRowCount(0);
        reservaSeleccionada = null;
        clienteSeleccionado = null;
        automovilesReserva = null;
        multaCalculada = 0.0;

        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
    }

    private boolean validarLitrosFinales() {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Object litrosFinalesObj = modeloTabla.getValueAt(i, 6);

            if (litrosFinalesObj == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese los litros finales para el vehículo con placa: " + 
                    modeloTabla.getValueAt(i, 0), 
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            String litrosFinalesStr = litrosFinalesObj.toString().trim();

            if (litrosFinalesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor, ingrese los litros finales para el vehículo con placa: " + 
                    modeloTabla.getValueAt(i, 0), 
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try {
                litrosFinalesStr = litrosFinalesStr.replace(",", ".")
                                                 .replaceAll("[^0-9.]", "");

                if (litrosFinalesStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Por favor, ingrese un número válido para los litros finales del vehículo: " + 
                        modeloTabla.getValueAt(i, 0), 
                        "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                System.out.println("Valor a convertir: '" + litrosFinalesStr + "' - Length: " + litrosFinalesStr.length());
                double litrosFinales = Double.parseDouble(litrosFinalesStr);

                if (litrosFinales < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Los litros finales no pueden ser negativos para el vehículo: " + 
                        modeloTabla.getValueAt(i, 0), 
                        "Datos inválidos", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al procesar los litros finales del vehículo: " + modeloTabla.getValueAt(i, 0) + 
                    "\nValor recibido: '" + litrosFinalesObj + "'" +
                    "\nValor procesado: '" + litrosFinalesStr + "'" +
                    "\nPor favor, ingrese solo números.", 
                    "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void guardarLitrosFinales() {
        try {
            String nombreArchivo = "litros_finales_reserva_" + reservaSeleccionada.getReservaId() + 
                                  "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";

            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write("REGISTRO DE LITROS FINALES - DEVOLUCIÓN DE AUTOMÓVILES\n");
            writer.write("=========================================================\n");
            writer.write("Reserva ID: " + reservaSeleccionada.getReservaId() + "\n");
            writer.write("Cliente: " + (clienteSeleccionado != null ? clienteSeleccionado.getNombre() : "N/D") + "\n");
            writer.write("Fecha de devolución: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "\n");
            writer.write("Multa aplicada: $" + String.format("%.2f", multaCalculada) + "\n\n");
            writer.write("AUTOMÓVILES DEVUELTOS:\n");
            writer.write("Placa\t\tLitros Inicial\tLitros Final\tDiferencia\n");
            for (int j = 0; j < 60; j++) {
                writer.write("-");
            }
            writer.write("\n");

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String placa = (String) modeloTabla.getValueAt(i, 0);
                double litrosInicial = Double.parseDouble(modeloTabla.getValueAt(i, 5).toString());

                Object litrosFinalesObj = modeloTabla.getValueAt(i, 6);
                double litrosFinales;

                if (litrosFinalesObj instanceof Double) {
                    litrosFinales = (Double) litrosFinalesObj;
                } else if (litrosFinalesObj instanceof Integer) {
                    litrosFinales = ((Integer) litrosFinalesObj).doubleValue();
                } else {
                    String litrosFinalesStr = litrosFinalesObj.toString().trim()
                                                            .replace(",", ".");
                    litrosFinalesStr = litrosFinalesStr.replaceAll("[^0-9.-]", "");
                    litrosFinales = Double.parseDouble(litrosFinalesStr);
                }

                double diferencia = litrosInicial - litrosFinales;

                writer.write(String.format("%s\t\t%.2f\t\t%.2f\t\t%.2f\n", 
                    placa, litrosInicial, litrosFinales, diferencia));
            }

            writer.close();

            JOptionPane.showMessageDialog(this, 
                "Registro de litros guardado en: " + nombreArchivo, 
                "Archivo guardado", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el archivo de litros: " + e.getMessage() + 
                "\nVerifique que tenga permisos de escritura en el directorio.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, 
                "Error al procesar los números de litros: " + e.getMessage(), 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, 
                "Error inesperado al guardar el archivo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarDevolucion(boolean pagarMulta) {
        if (reservaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "No hay una reserva seleccionada", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarLitrosFinales()) {
            return;
        }

        if (multaCalculada > 0 && !pagarMulta) {
            JOptionPane.showMessageDialog(this, 
                "Esta reserva tiene multa por retraso. Debe usar el botón 'Pagar Multa y Devolver'", 
                "Multa pendiente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensaje = "¿Confirma la devolución de los automóviles?\n\n";
        mensaje += "Reserva ID: " + reservaSeleccionada.getReservaId() + "\n";
        mensaje += "Cliente: " + jLabel3.getText() + "\n";
        if (multaCalculada > 0) {
            mensaje += "Multa: $" + String.format("%.2f", multaCalculada) + "\n";
        }
        mensaje += "\nEsta acción eliminará la reserva y liberará los automóviles.";

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, 
            "Confirmar Devolución", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean exito = true;
            StringBuilder errores = new StringBuilder();

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String placa = (String) modeloTabla.getValueAt(i, 0);
                boolean liberado = facade.liberarAutomovil(placa);
                
                if (!liberado) {
                    exito = false;
                    errores.append("Error al liberar automóvil: ").append(placa).append("\n");
                }
            }

            if (!exito) {
                JOptionPane.showMessageDialog(this, 
                    "Errores al liberar automóviles:\n" + errores.toString(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            guardarLitrosFinales();
            
            boolean archivada = archivarReservaAntesDeDevolion(reservaSeleccionada);

            boolean reservaEliminada = facade.eliminarReserva(reservaSeleccionada.getReservaId());
            
            if (reservaEliminada) {
                String mensajeExito = "Devolución completada exitosamente!\n\n";
                mensajeExito += "- Automóviles liberados\n";
                mensajeExito += "- Registro de combustible guardado\n";
                mensajeExito += "- Reserva eliminada\n";
                if (multaCalculada > 0) {
                    mensajeExito += "- Multa cobrada: $" + String.format("%.2f", multaCalculada);
                }

                JOptionPane.showMessageDialog(this, mensajeExito, 
                    "Devolución Exitosa", JOptionPane.INFORMATION_MESSAGE);

                cargarReservasEntregadas();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Los automóviles fueron liberados pero no se pudo eliminar la reserva. " +
                    "Contacte al administrador del sistema.", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error durante el proceso de devolución: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean archivarReservaAntesDeDevolion(Reserva reserva) {
        if (reserva == null || reserva.getReservaId() <= 0) {
            JOptionPane.showMessageDialog(this, "Reserva no válida para archivar", "Error de validación", 0);
            return false;
        }

        if (!reserva.isEntregado()) {
            JOptionPane.showMessageDialog(this, "Solo se pueden archivar reservas entregadas", "Error de negocio", 0);
            return false;
        }

        Connection cn = null;
        CallableStatement cs = null;
        PreparedStatement psCheck = null;
        ResultSet rs = null;

        try {
            cn = Conexion.realizarconexion();

            String checkQuery = "SELECT COUNT(*) FROM ReservaInactiva WHERE reserva_id = ?";
            psCheck = cn.prepareStatement(checkQuery);
            psCheck.setInt(1, reserva.getReservaId());
            rs = psCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("DEBUG - La reserva " + reserva.getReservaId() + " ya está archivada");
                JOptionPane.showMessageDialog(this, "La reserva ya ha sido archivada anteriormente", "Información", JOptionPane.INFORMATION_MESSAGE);
                return true; 
            }

            if (reserva.getFechaInicio() == null || reserva.getFechaFin() == null) {
                JOptionPane.showMessageDialog(this, "Error: Fechas de reserva no válidas", "Error de datos", 0);
                return false;
            }

            cs = cn.prepareCall("{call sp_insertar_reserva_inactiva(?,?,?,?,?,?)}");
            cs.setInt(1, reserva.getReservaId());
            cs.setInt(2, reserva.getClienteId());
            cs.setInt(3, reserva.getAgenciaId());
            cs.setTimestamp(4, new java.sql.Timestamp(reserva.getFechaInicio().getTimeInMillis()));
            cs.setTimestamp(5, new java.sql.Timestamp(reserva.getFechaFin().getTimeInMillis()));
            cs.setDouble(6, reserva.getPrecioTotal());

            System.out.println("DEBUG - Archivando reserva:");
            System.out.println("ID: " + reserva.getReservaId());
            System.out.println("Cliente: " + reserva.getClienteId());
            System.out.println("Agencia: " + reserva.getAgenciaId());
            System.out.println("Fecha inicio: " + new java.sql.Timestamp(reserva.getFechaInicio().getTimeInMillis()));
            System.out.println("Fecha fin: " + new java.sql.Timestamp(reserva.getFechaFin().getTimeInMillis()));
            System.out.println("Precio: " + reserva.getPrecioTotal());

            int resultado = cs.executeUpdate();
            System.out.println("Resultado executeUpdate: " + resultado);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Reserva archivada exitosamente", "Archivo completado", 1);
                return true;
            } else {
                psCheck = cn.prepareStatement(checkQuery);
                psCheck.setInt(1, reserva.getReservaId());
                rs = psCheck.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("DEBUG - La reserva se archivó correctamente (INSERT IGNORE no reportó el insert)");
                    JOptionPane.showMessageDialog(this, "Reserva archivada exitosamente", "Archivo completado", 1);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo archivar la reserva. Posibles causas:\n" +
                        "- La reserva ya existe en el archivo\n" +
                        "- Error en los datos proporcionados\n" +
                        "- Problema con la base de datos", 
                        "Error de archivo", 0);
                    return false;
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error: Driver de base de datos no encontrado\n" + e.getMessage(), 
                "Error de configuración", 0);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error SQL al archivar reserva:\n" + 
                "Código: " + e.getErrorCode() + "\n" +
                "Estado SQL: " + e.getSQLState() + "\n" +
                "Mensaje: " + e.getMessage(), 
                "Error en Base de Datos", 0);
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheck != null) psCheck.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
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
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
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

        jLabel6.setText("Precio multa:");

        jLabel7.setText("00.00");

        jPanel2.setBackground(new java.awt.Color(8, 100, 60));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Fecha de entrega:");

        jLabel8.setBackground(new java.awt.Color(8, 100, 60));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fecha Fin de reserva:");

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
        jLabel4.setText("Automoviles a devolver:");

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
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(205, 205, 205)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, 211, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(30, 30, 30))
        );

        jButton1.setText("Realizar devolucion");
        jButton1.setActionCommand("Realizar devolucion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pagar multa");
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
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
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
        realizarDevolucion(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        realizarDevolucion(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private FacadeAlquiler facade;
    private ArrayList<Reserva> reservasEntregadas;
    private DefaultTableModel modeloTabla;
    private Reserva reservaSeleccionada;
    private Cliente clienteSeleccionado;
    private ArrayList<ReservaAutomovil> automovilesReserva;
    private double multaCalculada = 0.0;
    private final double MULTA_POR_DIA = 50.0;
    private Color presionadoMenu = new Color(150,150,150);
    private Color encimaMenu = new Color(175,175,175);
    private Color botonMenu= new Color(200,200,200);
}
