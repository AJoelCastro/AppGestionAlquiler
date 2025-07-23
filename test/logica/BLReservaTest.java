/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package logica;

import entidades.Reserva;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

public class BLReservaTest {

    @Test
    public void testInsertarReserva() {
        System.out.println("insertarReserva");
        int idCliente = 31;  // Asegúrate que existe
        int idAgencia = 31;  // Asegúrate que existe

        GregorianCalendar fechaInicio = new GregorianCalendar(2025, Calendar.AUGUST, 10);
        GregorianCalendar fechaFin = new GregorianCalendar(2025, Calendar.AUGUST, 15);

        boolean result = BLReserva.insertarReserva(idCliente, idAgencia, fechaInicio, fechaFin);
        assertTrue(result);
    }

    @Test
    public void testBuscarReservaPorId() {
        System.out.println("buscarReservaPorId");
        int idReserva = 1; // Debe existir
        Reserva result = BLReserva.buscarReservaPorId(idReserva);
        assertNotNull(result);
    }

    @Test
    public void testBuscarReservasPorCliente() {
        System.out.println("buscarReservasPorCliente");
        int idCliente = 1; // Debe tener reservas
        ArrayList<Reserva> result = BLReserva.buscarReservasPorCliente(idCliente);
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    public void testActualizarPrecioReserva() {
        System.out.println("actualizarPrecioReserva");
        int reservaId = 2; // Debe existir
        boolean result = BLReserva.actualizarPrecioReserva(reservaId);
        assertTrue(result);
    }

    @Test
    public void testCambiarEstadoReserva() {
        System.out.println("cambiarEstadoReserva");
        int reservaId = 2; // Debe existir
        boolean entregado = true;
        boolean result = BLReserva.cambiarEstadoReserva(reservaId, entregado);
        assertTrue(result);
    }

    @Test
    public void testEliminarReserva() {
        System.out.println("eliminarReserva");
        int idReserva = 4; // Asegúrate que existe o pruébalo después de insertar
        boolean result = BLReserva.eliminarReserva(idReserva);
        assertTrue(result);
    }

    @Test
    public void testListarReservas() {
        System.out.println("listarReservas");
        ArrayList<Reserva> result = BLReserva.listarReservas();
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    public void testEditarReserva() {
        System.out.println("editarReserva");
        int reservaId = 9;   // Asegúrate que exista
        int agenciaId = 9;   // Asegúrate que exista

        GregorianCalendar fechaInicio = new GregorianCalendar(2025, Calendar.AUGUST, 10);
        GregorianCalendar fechaFin = new GregorianCalendar(2025, Calendar.AUGUST, 15);

        boolean result = BLReserva.editarReserva(reservaId, agenciaId, fechaInicio, fechaFin);
        assertTrue(result);
    }

    @Test
    public void testBuscarReservasPorFecha() {
        System.out.println("buscarReservasPorFecha");

        GregorianCalendar fechaInicio = new GregorianCalendar(2025, Calendar.AUGUST, 1);
        GregorianCalendar fechaFin = new GregorianCalendar(2025, Calendar.AUGUST, 31);

        ArrayList<Reserva> result = BLReserva.buscarReservasPorFecha(fechaInicio, fechaFin);
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    public void testBuscarReservasPorAgencia() {
        System.out.println("buscarReservasPorAgencia");
        int agenciaId = 1; // Debe existir
        ArrayList<Reserva> result = BLReserva.buscarReservasPorAgencia(agenciaId);
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }

    @Test
    public void testListarReservasActivas() {
        System.out.println("listarReservasActivas");
        ArrayList<Reserva> result = BLReserva.listarReservasActivas();
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }
}
