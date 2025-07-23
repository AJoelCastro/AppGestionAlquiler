/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Reserva;
import java.util.GregorianCalendar;

public class ReservaCompletaCreator extends ReservaCreator {
    private int reservaId;
    private int clienteId;
    private int agenciaId;
    private GregorianCalendar fechaInicio;
    private GregorianCalendar fechaFin;
    private double precioTotal;
    private boolean entregado;

    public ReservaCompletaCreator(int reservaId, int clienteId, int agenciaId,
                                   GregorianCalendar fechaInicio, GregorianCalendar fechaFin,
                                   double precioTotal, boolean entregado) {
        this.reservaId = reservaId;
        this.clienteId = clienteId;
        this.agenciaId = agenciaId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.entregado = entregado;
    }

    @Override
    public Reserva crearReserva() {
        return new Reserva(reservaId, clienteId, agenciaId, fechaInicio, fechaFin, precioTotal, entregado);
    }
}
