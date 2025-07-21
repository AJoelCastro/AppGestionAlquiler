/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.GregorianCalendar;

/**
 *
 * @author sanar
 */


public class Reserva {
    private int reservaId;
    private int clienteId;
    private int agenciaId;
    private GregorianCalendar fechaInicio;
    private GregorianCalendar fechaFin;
    private double precioTotal;
    private boolean entregado;

    private String nombreCliente;
    private String nombreAgencia;

    public Reserva() {
        this.precioTotal = 0.0;
        this.entregado = false;
        this.fechaInicio = new GregorianCalendar();
        this.fechaFin = new GregorianCalendar();
    }
    
    public Reserva(int clienteId, int agenciaId, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        this.clienteId = clienteId;
        this.agenciaId = agenciaId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = 0.0;
        this.entregado = false;
    }
    
    public Reserva(int reservaId, int clienteId, int agenciaId, GregorianCalendar fechaInicio, GregorianCalendar fechaFin, double precioTotal, boolean entregado) {
        this.reservaId = reservaId;
        this.clienteId = clienteId;
        this.agenciaId = agenciaId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.entregado = entregado;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getAgenciaId() {
        return agenciaId;
    }

    public void setAgenciaId(int agenciaId) {
        this.agenciaId = agenciaId;
    }

    public GregorianCalendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(GregorianCalendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public GregorianCalendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(GregorianCalendar fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreAgencia() {
        return nombreAgencia;
    }

    public void setNombreAgencia(String nombreAgencia) {
        this.nombreAgencia = nombreAgencia;
    }
    public String getEstadoReserva() {
        java.util.Date fechaActual = new java.util.Date();
        java.util.Date fechaInicio = this.fechaInicio.getTime();
        java.util.Date fechaFin = this.fechaFin.getTime();

        if (this.entregado) {
            return "ENTREGADA";
        } else if (fechaActual.before(fechaInicio)) {
            return "FUTURA";
        } else if (fechaActual.after(fechaFin)) {
            return "VENCIDA";
        } else if ((fechaActual.equals(fechaInicio) || fechaActual.after(fechaInicio)) && 
                   (fechaActual.equals(fechaFin) || fechaActual.before(fechaFin))) {
            return "ACTIVA";
        } else {
            return "OTRO";
        }
    }
}