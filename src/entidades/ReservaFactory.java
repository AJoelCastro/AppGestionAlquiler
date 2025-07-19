/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.GregorianCalendar;

/**
 *
 * @author Arturo
 */
public class ReservaFactory {
    
    public static Reserva crearReservaCompleta(int reservaId, int clienteId, int agenciaId, 
            GregorianCalendar fechaInicio, GregorianCalendar fechaFin, 
            double precioTotal, boolean entregado, 
            String nombreCliente, String nombreAgencia) {
        return new Reserva(reservaId, clienteId, agenciaId, fechaInicio, fechaFin, 
                precioTotal, entregado, nombreCliente, nombreAgencia);
    }
    
    public static Reserva crearReservaSinId(int clienteId, int agenciaId, 
            GregorianCalendar fechaInicio, GregorianCalendar fechaFin, 
            double precioTotal, boolean entregado) {
        return new Reserva(clienteId, agenciaId, fechaInicio, fechaFin, 
                precioTotal, entregado);
    }
}