/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.DALReserva;
import datos.DALReservaAutomovil;
import entidades.Reserva;
import entidades.ReservaAutomovil;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author sanar
 */

public class BLReserva {
    
    public static boolean insertarReserva(int IdCliente, int IdAgencia, GregorianCalendar fechaI, GregorianCalendar fechaF) {
        if (IdCliente > 0 && IdAgencia > 0
                && fechaI != null && fechaF != null
                && fechaI.before(fechaF)) {

            Reserva reserva = new Reserva();
            reserva.setClienteId(IdCliente);
            reserva.setAgenciaId(IdAgencia);
            reserva.setFechaInicio(fechaI);
            reserva.setFechaFin(fechaF);

            int resultado = DALReserva.insertarReserva(reserva);

            if (resultado > 0) {
                showMessageDialog(null, "Reserva registrada", "Resultado", 1);
                return true;
            } else {
                showMessageDialog(null, "Error al registrar reserva", "Error", 0);
                return false;
            }

        } else {
            showMessageDialog(null, "Datos no válidos o fechas incorrectas", "Error", 0);
            return false;
        }
    }
    
    public static Reserva buscarReservaPorId(int idReserva) {
        return DALReserva.buscarReservaPorId(idReserva);
    }
    
    public static ArrayList<Reserva> buscarReservasPorCliente(int idCliente) {
        return DALReserva.buscarReservasPorCliente(idCliente);
    }
    
    public static boolean actualizarPrecioReserva(int reservaId) {
        ArrayList<ReservaAutomovil> reservasAuto = DALReservaAutomovil.listarReservaAutomovil();
        double precioTotal = 0.0;
        
        for (ReservaAutomovil ra : reservasAuto) {
            if (ra.getReservaId() == reservaId) {
                precioTotal += ra.getPrecioAlquiler();
            }
        }
        
        if (precioTotal > 0) {
            String mensaje = DALReserva.actualizarPrecioReserva(reservaId, precioTotal);
            if (mensaje == null) {
                showMessageDialog(null, "Precio actualizado correctamente", "Éxito", 1);
                return true;
            } else {
                showMessageDialog(null, "Error al actualizar precio: " + mensaje, "Error", 0);
                return false;
            }
        } else {
            showMessageDialog(null, "No se encontraron automóviles para esta reserva", "Advertencia", 2);
            return false;
        }
    }
    
    public static boolean cambiarEstadoReserva(int reservaId, boolean entregado) {
        Reserva reserva = buscarReservaPorId(reservaId);
        if (reserva != null) {
            String mensaje = DALReserva.actualizarEstadoReserva(reservaId, entregado);
            if (mensaje == null) {
                showMessageDialog(null, "Estado actualizado correctamente", "Éxito", 1);
                return true;
            } else {
                showMessageDialog(null, "Error al actualizar estado: " + mensaje, "Error", 0);
                return false;
            }
        }
        return false;
    }
    
    public static boolean eliminarReserva(int idReserva) {
        Reserva reserva = buscarReservaPorId(idReserva);
        if (reserva != null) {
            if (reserva.isEntregado()) {
                String mensaje = DALReserva.eliminarReserva(idReserva);
                if (mensaje == null) {
                    showMessageDialog(null, "Reserva eliminada correctamente", "Éxito", 1);
                    return true;
                } else {
                    showMessageDialog(null, "Error al eliminar: " + mensaje, "Error", 0);
                    return false;
                }
            } else {
                showMessageDialog(null, "Solo se pueden eliminar reservas entregadas", "Advertencia", 2);
                return false;
            }
        }
        return false;
    }

    public static ArrayList<Reserva> listarReservas() {
        return DALReserva.listarReservas();
    }
    
    public static boolean editarReserva(int reservaId, int agenciaId, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        // Validaciones
        if (reservaId <= 0 || agenciaId <= 0 || fechaInicio == null || fechaFin == null) {
            showMessageDialog(null, "Datos no válidos", "Error", 0);
            return false;
        }

        if (!fechaInicio.before(fechaFin)) {
            showMessageDialog(null, "La fecha de inicio debe ser anterior a la fecha de fin", "Error", 0);
            return false;
        }

        Reserva reservaExistente = buscarReservaPorId(reservaId);
        if (reservaExistente == null) {
            showMessageDialog(null, "La reserva no existe", "Error", 0);
            return false;
        }

        if (reservaExistente.isEntregado()) {
            showMessageDialog(null, "No se pueden modificar reservas ya entregadas", "Advertencia", 2);
            return false;
        }

        String mensaje = DALReserva.editarReserva(reservaId, agenciaId, fechaInicio, fechaFin);

        if (mensaje == null) {
            showMessageDialog(null, "Reserva modificada correctamente", "Éxito", 1);
            return true;
        } else {
            showMessageDialog(null, "Error al modificar reserva: " + mensaje, "Error", 0);
            return false;
        }
    }
    // Agregar estos métodos a la clase BLReserva

    public static ArrayList<Reserva> buscarReservasPorFecha(GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        if (fechaInicio != null && fechaFin != null && !fechaInicio.after(fechaFin)) {
            return DALReserva.buscarReservasPorFecha(fechaInicio, fechaFin);
        } else {
            showMessageDialog(null, "Rango de fechas inválido", "Error", 0);
            return new ArrayList<>();
        }
    }

    public static ArrayList<Reserva> buscarReservasPorAgencia(int agenciaId) {
        if (agenciaId > 0) {
            return DALReserva.buscarReservasPorAgencia(agenciaId);
        } else {
            showMessageDialog(null, "ID de agencia inválido", "Error", 0);
            return new ArrayList<>();
        }
    }
    // Agregar este método a la clase BLReserva

    public static ArrayList<Reserva> listarReservasActivas() {
        return DALReserva.listarReservasActivas();
    }
}