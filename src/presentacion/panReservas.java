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
public class panReservas extends javax.swing.JPanel {

    /**
     * Creates new form panPrestamos
     */
    public panReservas() {
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
    
    private void cargarReservasActivasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Reserva> reservas = facade.listarReservasActivas();
            for (Reserva reserva : reservas) {
                String fechaInicio = String.format("%02d/%02d/%d %02d:%02d", 
                    reserva.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                    reserva.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                    reserva.getFechaInicio().get(java.util.Calendar.YEAR),
                    reserva.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                    reserva.getFechaInicio().get(java.util.Calendar.MINUTE));

                String fechaFin = String.format("%02d/%02d/%d %02d:%02d", 
                    reserva.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                    reserva.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                    reserva.getFechaFin().get(java.util.Calendar.YEAR),
                    reserva.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                    reserva.getFechaFin().get(java.util.Calendar.MINUTE));

                String estado = reserva.getEstadoReserva();

                Object[] fila = {
                    reserva.getReservaId(),
                    reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente ID: " + reserva.getClienteId(),
                    reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia ID: " + reserva.getAgenciaId(),
                    fechaInicio,
                    fechaFin,
                    String.format("S/. %.2f", reserva.getPrecioTotal()),
                    estado
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las reservas activas: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarReservasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        modelo.setRowCount(0);

        try {
            ArrayList<Reserva> reservas = facade.listarReservas();
            for (Reserva reserva : reservas) {
                String fechaInicio = String.format("%02d/%02d/%d %02d:%02d", 
                    reserva.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                    reserva.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                    reserva.getFechaInicio().get(java.util.Calendar.YEAR),
                    reserva.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                    reserva.getFechaInicio().get(java.util.Calendar.MINUTE));

                String fechaFin = String.format("%02d/%02d/%d %02d:%02d", 
                    reserva.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                    reserva.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                    reserva.getFechaFin().get(java.util.Calendar.YEAR),
                    reserva.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                    reserva.getFechaFin().get(java.util.Calendar.MINUTE));

                String estado = reserva.getEstadoReserva();

                Object[] fila = {
                    reserva.getReservaId(),
                    reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente ID: " + reserva.getClienteId(),
                    reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia ID: " + reserva.getAgenciaId(),
                    fechaInicio,
                    fechaFin,
                    String.format("S/. %.2f", reserva.getPrecioTotal()),
                    estado
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las reservas: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aplicarColoresTabla() {
        tblReservas.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, 
                boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    String estado = (String) table.getValueAt(row, 6); // Columna del estado

                    switch (estado) {
                        case "ENTREGADA":
                            c.setBackground(new java.awt.Color(200, 255, 200)); // Verde claro
                            break;
                        case "ACTIVA":
                            c.setBackground(new java.awt.Color(255, 255, 200)); // Amarillo claro
                            break;
                        case "VENCIDA":
                            c.setBackground(new java.awt.Color(255, 200, 200)); // Rojo claro
                            break;
                        case "FUTURA":
                            c.setBackground(new java.awt.Color(200, 200, 255)); // Azul claro
                            break;
                        default:
                            c.setBackground(java.awt.Color.WHITE);
                            break;
                    }
                }

                return c;
            }
        });
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Reserva");
        modelo.addColumn("Cliente");
        modelo.addColumn("Agencia");
        modelo.addColumn("Fecha/Hora Inicio");
        modelo.addColumn("Fecha/Hora Fin");
        modelo.addColumn("Precio Total");
        modelo.addColumn("Estado");
        tblReservas.setModel(modelo);

        tblReservas.setDefaultEditor(Object.class, null);

        tblReservas.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblReservas.getColumnModel().getColumn(4).setPreferredWidth(120);

        // NUEVO: Aplicar colores según el estado
        aplicarColoresTabla();
    }

    private void eliminarReserva() {
        int filaSeleccionada = tblReservas.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una reserva de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        int idReserva = (int) modelo.getValueAt(filaSeleccionada, 0);
        String cliente = (String) modelo.getValueAt(filaSeleccionada, 1);
        String agencia = (String) modelo.getValueAt(filaSeleccionada, 2);
        String fechaInicio = (String) modelo.getValueAt(filaSeleccionada, 3);
        String fechaFin = (String) modelo.getValueAt(filaSeleccionada, 4);
        String precioTotal = (String) modelo.getValueAt(filaSeleccionada, 5);
        String estado = (String) modelo.getValueAt(filaSeleccionada, 6);

        // CAMBIO: Verificar si la reserva está entregada usando el nuevo estado
        if (!estado.equals("ENTREGADA")) {
            JOptionPane.showMessageDialog(this, 
                "Solo se pueden eliminar reservas que estén en estado 'ENTREGADA'", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensaje = String.format(
            "¿Está seguro de que desea eliminar la siguiente reserva?\n\n" +
            "ID: %d\n" +
            "Cliente: %s\n" +
            "Agencia: %s\n" +
            "Fecha Inicio: %s\n" +
            "Fecha Fin: %s\n" +
            "Precio Total: %s\n" +
            "Estado: %s\n\n" +
            "Esta acción no se puede deshacer.",
            idReserva, cliente, agencia, fechaInicio, fechaFin, precioTotal, estado
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
                boolean exito = facade.eliminarReserva(idReserva);

                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                        "Reserva eliminada correctamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarReservasEnTabla();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo eliminar la reserva. Verifique que cumpla con los requisitos para eliminación.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar la reserva: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarReserva() {
        int filaSeleccionada = tblReservas.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una reserva de la tabla", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        int idReserva = (int) modelo.getValueAt(filaSeleccionada, 0);

        Reserva reservaActual = facade.buscarReservaPorId(idReserva);

        if (reservaActual == null) {
            JOptionPane.showMessageDialog(this, "No se pudo obtener la información de la reserva", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (reservaActual.isEntregado()) {
            JOptionPane.showMessageDialog(this, "No se pueden modificar reservas ya entregadas", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ArrayList<Agencia> agencias = facade.listarAgencias();
            String[] opcionesAgencias = new String[agencias.size()];
            for (int i = 0; i < agencias.size(); i++) {
                opcionesAgencias[i] = agencias.get(i).getAgenciaId() + " - " + agencias.get(i).getNombre();
            }

            String agenciaSeleccionada = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione la nueva agencia:",
                "Actualizar Agencia",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesAgencias,
                null
            );

            if (agenciaSeleccionada == null) return;

            int nuevaAgenciaId = Integer.parseInt(agenciaSeleccionada.split(" - ")[0]);

            String fechaInicioStr = JOptionPane.showInputDialog(this, 
                "Ingrese la nueva fecha y hora de inicio (dd/MM/yyyy HH:mm):",
                String.format("%02d/%02d/%04d %02d:%02d", 
                    reservaActual.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                    reservaActual.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                    reservaActual.getFechaInicio().get(java.util.Calendar.YEAR),
                    reservaActual.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                    reservaActual.getFechaInicio().get(java.util.Calendar.MINUTE))
            );

            if (fechaInicioStr == null) return;

            String fechaFinStr = JOptionPane.showInputDialog(this, 
                "Ingrese la nueva fecha y hora de fin (dd/MM/yyyy HH:mm):",
                String.format("%02d/%02d/%04d %02d:%02d", 
                    reservaActual.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                    reservaActual.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                    reservaActual.getFechaFin().get(java.util.Calendar.YEAR),
                    reservaActual.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                    reservaActual.getFechaFin().get(java.util.Calendar.MINUTE))
            );

            if (fechaFinStr == null) return;

            if (!fechaInicioStr.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}") || 
                !fechaFinStr.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Formato de fecha y hora inválido. Use dd/MM/yyyy HH:mm", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            java.util.GregorianCalendar nuevaFechaInicio = parseFechaHora(fechaInicioStr);
            java.util.GregorianCalendar nuevaFechaFin = parseFechaHora(fechaFinStr);

            if (nuevaFechaInicio == null || nuevaFechaFin == null) {
                JOptionPane.showMessageDialog(this, "Error al procesar las fechas ingresadas", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!nuevaFechaInicio.before(nuevaFechaFin)) {
                JOptionPane.showMessageDialog(this, "La fecha y hora de inicio debe ser anterior a la fecha y hora de fin", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean exito = facade.editarReserva(idReserva, nuevaAgenciaId, nuevaFechaInicio, nuevaFechaFin);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Reserva actualizada correctamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarReservasEnTabla();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos ingresados", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la reserva: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private java.util.GregorianCalendar parseFechaHora(String fechaHoraStr) {
        try {
            String[] partes = fechaHoraStr.split(" ");
            if (partes.length != 2) return null;

            String[] partesFecha = partes[0].split("/");
            String[] partesHora = partes[1].split(":");

            if (partesFecha.length != 3 || partesHora.length != 2) return null;

            int dia = Integer.parseInt(partesFecha[0]);
            int mes = Integer.parseInt(partesFecha[1]) - 1;
            int año = Integer.parseInt(partesFecha[2]);
            int hora = Integer.parseInt(partesHora[0]);
            int minuto = Integer.parseInt(partesHora[1]);

            if (dia < 1 || dia > 31 || mes < 0 || mes > 11 || año < 1900 || 
                hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
                return null;
            }

            java.util.GregorianCalendar calendario = new java.util.GregorianCalendar(año, mes, dia, hora, minuto, 0);
            return calendario;

        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private void buscarReserva() {
        String textoBusqueda = txtBusqueda.getText().trim();

        if (textoBusqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un término de búsqueda", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) tblReservas.getModel();
        modelo.setRowCount(0);

        try {
            boolean encontrado = false;

            if (jRadioButton1.isSelected()) { // Búsqueda por ID Reserva
                try {
                    int idReserva = Integer.parseInt(textoBusqueda);
                    Reserva reserva = facade.buscarReservaPorId(idReserva);

                    if (reserva != null) {
                        String fechaInicio = String.format("%02d/%02d/%d %02d:%02d", 
                            reserva.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                            reserva.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                            reserva.getFechaInicio().get(java.util.Calendar.YEAR),
                            reserva.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                            reserva.getFechaInicio().get(java.util.Calendar.MINUTE));

                        String fechaFin = String.format("%02d/%02d/%d %02d:%02d", 
                            reserva.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                            reserva.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                            reserva.getFechaFin().get(java.util.Calendar.YEAR),
                            reserva.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                            reserva.getFechaFin().get(java.util.Calendar.MINUTE));

                        String estado = reserva.isEntregado() ? "Entregado" : "Pendiente";

                        Object[] fila = {
                            reserva.getReservaId(),
                            reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente ID: " + reserva.getClienteId(),
                            reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia ID: " + reserva.getAgenciaId(),
                            fechaInicio,
                            fechaFin,
                            String.format("S/. %.2f", reserva.getPrecioTotal()),
                            estado
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El ID de reserva debe ser un número válido", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } else if (jRadioButton2.isSelected()) { // Búsqueda por Cliente
                try {
                    int idCliente = Integer.parseInt(textoBusqueda);
                    ArrayList<Reserva> reservas = facade.buscarReservasPorCliente(idCliente);

                    for (Reserva reserva : reservas) {
                        String fechaInicio = String.format("%02d/%02d/%d %02d:%02d", 
                            reserva.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                            reserva.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                            reserva.getFechaInicio().get(java.util.Calendar.YEAR),
                            reserva.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                            reserva.getFechaInicio().get(java.util.Calendar.MINUTE));

                        String fechaFin = String.format("%02d/%02d/%d %02d:%02d", 
                            reserva.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                            reserva.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                            reserva.getFechaFin().get(java.util.Calendar.YEAR),
                            reserva.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                            reserva.getFechaFin().get(java.util.Calendar.MINUTE));

                        String estado = reserva.isEntregado() ? "Entregado" : "Pendiente";

                        Object[] fila = {
                            reserva.getReservaId(),
                            reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente ID: " + reserva.getClienteId(),
                            reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia ID: " + reserva.getAgenciaId(),
                            fechaInicio,
                            fechaFin,
                            String.format("S/. %.2f", reserva.getPrecioTotal()),
                            estado
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El ID del cliente debe ser un número válido", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } else if (jRadioButton3.isSelected()) { // Búsqueda por Rango de Fechas
                // Formato esperado: "dd/mm/yyyy hh:mm - dd/mm/yyyy hh:mm"
                String[] fechas = textoBusqueda.split("-");
                if (fechas.length != 2) {
                    JOptionPane.showMessageDialog(this, 
                        "Formato incorrecto. Use: dd/mm/yyyy hh:mm - dd/mm/yyyy hh:mm", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.util.GregorianCalendar fechaInicio = parseFechaHora(fechas[0].trim());
                java.util.GregorianCalendar fechaFin = parseFechaHora(fechas[1].trim());

                if (fechaInicio == null || fechaFin == null) {
                    JOptionPane.showMessageDialog(this, 
                        "Formato de fecha incorrecto. Use: dd/mm/yyyy hh:mm", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (fechaInicio.after(fechaFin)) {
                    JOptionPane.showMessageDialog(this, 
                        "La fecha de inicio debe ser anterior a la fecha de fin", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ArrayList<Reserva> reservas = facade.buscarReservasPorFecha(fechaInicio, fechaFin);

                for (Reserva reserva : reservas) {
                    String fechaInicioStr = String.format("%02d/%02d/%d %02d:%02d", 
                        reserva.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                        reserva.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                        reserva.getFechaInicio().get(java.util.Calendar.YEAR),
                        reserva.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                        reserva.getFechaInicio().get(java.util.Calendar.MINUTE));

                    String fechaFinStr = String.format("%02d/%02d/%d %02d:%02d", 
                        reserva.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                        reserva.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                        reserva.getFechaFin().get(java.util.Calendar.YEAR),
                        reserva.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                        reserva.getFechaFin().get(java.util.Calendar.MINUTE));

                    String estado = reserva.isEntregado() ? "Entregado" : "Pendiente";

                    Object[] fila = {
                        reserva.getReservaId(),
                        reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente ID: " + reserva.getClienteId(),
                        reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia ID: " + reserva.getAgenciaId(),
                        fechaInicioStr,
                        fechaFinStr,
                        String.format("S/. %.2f", reserva.getPrecioTotal()),
                        estado
                    };
                    modelo.addRow(fila);
                    encontrado = true;
                }

            } else if (jRadioButton4.isSelected()) { // Búsqueda por Agencia
                try {
                    int idAgencia = Integer.parseInt(textoBusqueda);
                    ArrayList<Reserva> reservas = facade.buscarReservasPorAgencia(idAgencia);

                    for (Reserva reserva : reservas) {
                        String fechaInicio = String.format("%02d/%02d/%d %02d:%02d", 
                            reserva.getFechaInicio().get(java.util.Calendar.DAY_OF_MONTH),
                            reserva.getFechaInicio().get(java.util.Calendar.MONTH) + 1,
                            reserva.getFechaInicio().get(java.util.Calendar.YEAR),
                            reserva.getFechaInicio().get(java.util.Calendar.HOUR_OF_DAY),
                            reserva.getFechaInicio().get(java.util.Calendar.MINUTE));

                        String fechaFin = String.format("%02d/%02d/%d %02d:%02d", 
                            reserva.getFechaFin().get(java.util.Calendar.DAY_OF_MONTH),
                            reserva.getFechaFin().get(java.util.Calendar.MONTH) + 1,
                            reserva.getFechaFin().get(java.util.Calendar.YEAR),
                            reserva.getFechaFin().get(java.util.Calendar.HOUR_OF_DAY),
                            reserva.getFechaFin().get(java.util.Calendar.MINUTE));

                        String estado = reserva.isEntregado() ? "Entregado" : "Pendiente";

                        Object[] fila = {
                            reserva.getReservaId(),
                            reserva.getNombreCliente() != null ? reserva.getNombreCliente() : "Cliente ID: " + reserva.getClienteId(),
                            reserva.getNombreAgencia() != null ? reserva.getNombreAgencia() : "Agencia ID: " + reserva.getAgenciaId(),
                            fechaInicio,
                            fechaFin,
                            String.format("S/. %.2f", reserva.getPrecioTotal()),
                            estado
                        };
                        modelo.addRow(fila);
                        encontrado = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El ID de la agencia debe ser un número válido", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un tipo de búsqueda", 
                                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!encontrado) {
                String tipoBusqueda = "";
                if (jRadioButton1.isSelected()) tipoBusqueda = "ID de reserva";
                else if (jRadioButton2.isSelected()) tipoBusqueda = "ID de cliente";
                else if (jRadioButton3.isSelected()) tipoBusqueda = "rango de fechas";
                else if (jRadioButton4.isSelected()) tipoBusqueda = "ID de agencia";

                JOptionPane.showMessageDialog(this, 
                    "No se encontraron reservas que coincidan con " + tipoBusqueda + ": " + textoBusqueda, 
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                cargarReservasEnTabla(); // Mostrar todas las reservas nuevamente
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar reservas: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
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
        btnListar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnActualizar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnEliminar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        btnRegistrar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jLabel1 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnBuscar = new BotonPersonalizado("", botonBlanco,presionadoBuscar,encimaBuscar);
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        dspFondo.setBackground(new java.awt.Color(255, 255, 255));

        btnListar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lista.png"))); // NOI18N
        btnListar.setText("  Listar Reservas");
        btnListar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/editar.png"))); // NOI18N
        btnActualizar.setText("  Actualizar Reservas");
        btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("  Eliminar Reservas");
        btnEliminar.setToolTipText("");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/añadir.png"))); // NOI18N
        btnRegistrar.setText("  Registrar Reservas");
        btnRegistrar.setToolTipText("");
        btnRegistrar.setActionCommand("Agregar Préstamo");
        btnRegistrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setText("Control de Reservas");

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    buscarReserva();
                }
            }
        });
        txtBusqueda.setBorder(null);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOpaque(true);

        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

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

        jLabel2.setText("Buscar por:");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("ID Reserva");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("ID Cliente");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Rango Fecha inicio");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("ID Agencia");
        jRadioButton4.setToolTipText("");

        jLabel3.setText("Formato: dd/mm/yyyy hh:mm - dd/mm/yyyy hh:mm");

        dspFondo.setLayer(btnListar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnActualizar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnEliminar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnRegistrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(txtBusqueda, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(btnBuscar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jRadioButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jRadioButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jRadioButton3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jRadioButton4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dspFondo.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dspFondoLayout = new javax.swing.GroupLayout(dspFondo);
        dspFondo.setLayout(dspFondoLayout);
        dspFondoLayout.setHorizontalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnListar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(75, 75, 75)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(dspFondoLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(dspFondoLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(222, 222, 222))
                                .addGroup(dspFondoLayout.createSequentialGroup()
                                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jRadioButton4)
                                        .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jSeparator1)
                                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dspFondoLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(80, 80, 80)
                            .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jRadioButton3)
                                .addGroup(dspFondoLayout.createSequentialGroup()
                                    .addComponent(jRadioButton1)
                                    .addGap(111, 111, 111)
                                    .addComponent(jRadioButton2))
                                .addGroup(dspFondoLayout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addComponent(jLabel3)))
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        dspFondoLayout.setVerticalGroup(
            dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dspFondoLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton4))
                .addGap(10, 10, 10)
                .addComponent(jRadioButton3)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addGap(20, 20, 20)
                .addGroup(dspFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dspFondoLayout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarReserva();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        JInternalFrame ifrm = new ifrmRegistrarReserva();
        centrarInternalFrame(ifrm);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarReserva();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        String[] opciones = {"Todas las Reservas", "Solo Reservas Activas"};
        int seleccion = JOptionPane.showOptionDialog(
            this,
            "¿Qué reservas desea listar?",
            "Opciones de Listado",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion == 0) {
            cargarReservasEnTabla();
        } else if (seleccion == 1) {
            cargarReservasActivasEnTabla();
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarReserva();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JDesktopPane dspFondo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblReservas;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
    private Color botonBlanco = new Color(255,255,255);
    private Color presionadoBuscar = new Color(200,200,200);
    private Color encimaBuscar = new Color(225,225,225);
    private FacadeAlquiler facade;
}
