/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.*;
import java.util.GregorianCalendar;

/**
 *
 * @author Administrador
 */
public class FacadeAlquiler {

    private BLAgencia agencia;
    private BLAutomovil auto;
    private BLCliente cliente;
    private BLReserva reserva;
    private BLGaraje garaje;

    public FacadeAlquiler() {
        this.agencia = new BLAgencia();
        this.auto = new BLAutomovil();
        this.cliente = new BLCliente();
        this.reserva = new BLReserva();
        this.garaje = new BLGaraje();
    }

    public void registrarAgencia(String nombre, String direccion){
        agencia.insertarAgencia(nombre, direccion);
    }
    
    public Agencia buscarAgenciaPorId(int idAgencia){
        return agencia.buscarAgenciaPorId(idAgencia);
    }
    
    
    
    public void registrarAutomovil(String placa, String modelo, String color, String marca, int garajeId){
        auto.insertarAutomovil(placa, modelo, color, marca, garajeId);
    }
    
    public void registrarGaraje(String nombre, String ubicacion){
        garaje.insertarGaraje(nombre, ubicacion);
    }
    
    public void registrarCliente(Cliente c) {
        cliente.insertarCliente(c);
    }
    
    public void registrarReserva(int reservaId, int IdCliente, int IdAgencia, GregorianCalendar fechaI, GregorianCalendar fechaF, float precioT, Boolean estado, String nombreC, String nombreAg) {
        reserva.insertarReserva(reservaId, IdCliente, IdAgencia, fechaI, fechaF, precioT, estado, nombreC, nombreAg);
    }
}
