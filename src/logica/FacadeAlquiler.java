/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.Conexion;
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
    private BLReservaAutomovil reserauto;

    public FacadeAlquiler() {
        this.agencia = new BLAgencia();
        this.auto = new BLAutomovil();
        this.cliente = new BLCliente();
        this.reserva = new BLReserva();
        this.garaje = new BLGaraje();
        this.reserauto = new BLReservaAutomovil();
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

    public boolean editarAutomovil(String placa, String nuevoModelo, String nuevoColor, String nuevaMarca) {
        return auto.editarAutomovil(placa, nuevoModelo, nuevoColor, nuevaMarca);
    }

    public boolean eliminarAutomovil(String placa) {
        return auto.eliminarAutomovil(placa);
    }
    
    public String verificarDisponibilidadAutomovil(String placa) { 
        return auto.verificarDisponibilidadAutomovil(placa);
    }
    
    public ArrayList<Automovil> listarAutomoviles() {
        return auto.listarAutomoviles();
    }
    
    public boolean liberarAutomovil(String placa) {
        return auto.liberarAutomovil(placa);
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
    
    public ArrayList<Garaje> listarGarajes() {
        return garaje.listarGarajes();
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
    
    public ArrayList<Cliente> listarClientes() {
        return cliente.listarClientes();
    }

    public boolean registrarReserva(int IdCliente, int IdAgencia, GregorianCalendar fechaI, GregorianCalendar fechaF) {
        return reserva.insertarReserva(IdCliente, IdAgencia, fechaI, fechaF);
    }

    public Reserva buscarReservaPorId(int idReserva) {
        return reserva.buscarReservaPorId(idReserva);
    }
    
    public ArrayList<Reserva> buscarReservasPorCliente(int idCliente) {
        return reserva.buscarReservasPorCliente(idCliente);
    }

    public boolean actualizarPrecioReserva(int reservaId) {
        return reserva.actualizarPrecioReserva(reservaId);
    }
    public boolean cambiarEstadoReserva(int reservaId, boolean entregado) {
        return reserva.cambiarEstadoReserva(reservaId, entregado);
    }

    public boolean eliminarReserva(int idReserva) {
        return reserva.eliminarReserva(idReserva);
    }
    public ArrayList<Reserva> listarReservas() {
        return reserva.listarReservas();
    }
    public boolean editarReserva(int reservaId, int agenciaId, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        return reserva.editarReserva(reservaId, agenciaId, fechaInicio, fechaFin);
    }
    // Agregar estos métodos a la clase FacadeAlquiler

    public ArrayList<Reserva> buscarReservasPorFecha(GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        return reserva.buscarReservasPorFecha(fechaInicio, fechaFin);
    }

    public ArrayList<Reserva> buscarReservasPorAgencia(int agenciaId) {
        return reserva.buscarReservasPorAgencia(agenciaId);
    }

    public ArrayList<Reserva> listarReservasActivas() {
        return reserva.listarReservasActivas();
    }
    public boolean asignarAutomovilesAReserva(int reservaId, ArrayList<ReservaAutomovil> autosAsignados) {
        return reserauto.asignarAutomovilesAReserva(reservaId, autosAsignados);
    }

    public ArrayList<ReservaAutomovil> listarReservaAutomovil() {
        return reserauto.listarReservaAutomovil();
    }

    public double obtenerTotalAlquilerPorReserva(int reservaId) {
        return reserauto.obtenerTotalAlquiler(reservaId);
    }
    // ...existing code...
    public double obtenerIngresosPorMesAgencia(String nombreAgencia, java.util.Date fecha) {
        double total = 0.0;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(fecha);
        int anio = cal.get(java.util.Calendar.YEAR);
        int mes = cal.get(java.util.Calendar.MONTH) + 1; // Calendar.MONTH es 0-based

        try (java.sql.Connection conn = Conexion.realizarconexion()) {
            // Obtener el id de la agencia por nombre
            String sqlAgencia = "SELECT agencia_id FROM Agencia WHERE nombre = ?";
            try (java.sql.PreparedStatement psAgencia = conn.prepareStatement(sqlAgencia)) {
                psAgencia.setString(1, nombreAgencia);
                try (java.sql.ResultSet rsAgencia = psAgencia.executeQuery()) {
                    if (rsAgencia.next()) {
                        int agenciaId = rsAgencia.getInt("agencia_id");

                        // Consulta directa a ReservaInactiva
                        String sql = """
                            SELECT SUM(precio_total) AS total_ingresos
                            FROM ReservaInactiva
                            WHERE agencia_id = ? 
                              AND YEAR(fecha_inicio) = ? 
                              AND MONTH(fecha_inicio) = ?
                        """;

                        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
                            ps.setInt(1, agenciaId);
                            ps.setInt(2, anio);
                            ps.setInt(3, mes);

                            try (java.sql.ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    total = rs.getDouble("total_ingresos");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total;
    }

}
