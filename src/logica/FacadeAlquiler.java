/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.*;
import java.util.ArrayList;
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

    public void registrarAgencia(String nombre, String direccion) {
        agencia.insertarAgencia(nombre, direccion);
    }

    public Agencia buscarAgenciaPorId(int idAgencia) {
        return agencia.buscarAgenciaPorId(idAgencia);
    }

    public boolean editarAgencia(int idAgencia, String nuevoNom, String nuevaDir) {
        return agencia.editarAgencia(idAgencia, nuevoNom, nuevaDir);
    }

    public boolean eliminarAgencia(int idAgencia) {
        return agencia.eliminarAgencia(idAgencia);
    }

    public void registrarAutomovil(String placa, String modelo, String color, String marca, int garajeId) {
        auto.insertarAutomovil(placa, modelo, color, marca, garajeId);
    }
    
    public ArrayList<Agencia> listarAgencias() {
        return agencia.listarAgencias();
    }
    
    public Automovil buscarAutomovilPorPlaca(String placa) {
        return auto.buscarAutomovilPorPlaca(placa);
    }

    public boolean editarAutomovil(String placa, String nuevoModelo, String nuevoColor, String nuevaMarca, int nuevoGarajeId) {
        return auto.editarAutomovil(placa, nuevoModelo, nuevoColor, nuevaMarca, nuevoGarajeId);
    }

    public boolean eliminarAutomovil(String placa) {
        return auto.eliminarAutomovil(placa);
    }
    
    public String verificarDisponibilidadAutomovil(String placa) { 
        return auto.verificarDisponibilidadAutomovil(placa);
    }
    public void registrarGaraje(String nombre, String ubicacion) {
        garaje.insertarGaraje(nombre, ubicacion);
    }

    public Garaje buscarGarajePorId(int idGaraje) {
        return garaje.buscarGarajePorId(idGaraje);
    }

    public boolean editarGaraje(int idGaraje, String nuevoNombre, String nuevaUbicacion) {
        return garaje.editarGaraje(idGaraje, nuevoNombre, nuevaUbicacion);
    }

    public boolean eliminarGaraje(int idGaraje) {
        return garaje.eliminarGaraje(idGaraje);
    }
    
    public ArrayList<Automovil> listarAutomovilesPorGaraje(int idGaraje) {
        return garaje.listarAutomovilesPorGaraje(idGaraje);
    }

    public void registrarCliente(Cliente c) {
        cliente.insertarCliente(c);
    }

    public Cliente buscarClientePorId(int idCliente) {
        return cliente.buscarClientePorId(idCliente);
    }

    public boolean editarCliente(int idCliente, String nuevoNombre, String nuevaDireccion, String nuevoTelefono) {
        return cliente.editarCliente(idCliente, nuevoNombre, nuevaDireccion, nuevoTelefono);
    }

    public boolean eliminarCliente(int idCliente) {
        return cliente.eliminarCliente(idCliente);
    }

    public void registrarReserva(int reservaId, int IdCliente, int IdAgencia, GregorianCalendar fechaI, GregorianCalendar fechaF, float precioT, Boolean estado) {
        reserva.insertarReserva(reservaId, IdCliente, IdAgencia, fechaI, fechaF, precioT, estado);
    }

    public Reserva buscarReservaPorId(int idReserva) {
        return reserva.buscarReservaPorId(idReserva);
    }
    
    public Reserva buscarReservaPorIdCliente(int idCliente) {
        return reserva.buscarReservaPorIdCliente(idCliente);
    }

    public boolean editarReserva(int reservaId, double nuevoPrecioT, boolean cambiarEstado) {
        return reserva.editarReserva(reservaId,nuevoPrecioT,cambiarEstado);
    }

    public boolean eliminarReserva(int idReserva) {
        return reserva.eliminarReserva(idReserva);
    }
    
    
}
