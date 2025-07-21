package logica;

import datos.*;
import entidades.*;
import java.util.ArrayList;
import logica.BLCliente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Asus
 */
public class BLClienteTest {
    @Test
    public void testInsertarClienteValido() {
        Cliente cliente = ClienteFactory.crearClienteSinSponsor(1000, "87654323", "Juan Pérez", "Av. Central 123", "987654321");
        System.out.println("pasa por aqui");
        int resultado = BLCliente.insertarCliente(cliente);
        System.out.println("termina aqui");
        assertEquals(0, resultado);
    }
    
    @Test
    public void testInsertarClienteDniDuplicado() {
        Cliente cliente = ClienteFactory.crearClienteSinSponsor(1001, "87654321", "Pedro Sánchez", "Jr. Lima 456", "912345678");
        int resultado = BLCliente.insertarCliente(cliente);
        assertEquals(3, resultado);
    }
    
    @Test
    public void testInsertarClienteDatosInvalidos() {
        Cliente cliente = ClienteFactory.crearClienteSinSponsor(1002, "123", "", "", "");
        int resultado = BLCliente.insertarCliente(cliente);
        assertEquals(2, resultado);
    }
    
    @Test
    public void testBuscarClientePorId_Existente() {
        int idCliente = 5; // Este cliente debe existir en la BD
        Cliente resultado = BLCliente.buscarClientePorId(idCliente);
        assertNotNull(resultado);
        assertEquals(idCliente, resultado.getIdCliente());
    }
    
    @Test
    public void testBuscarClientePorId_NoExistente() {
        int idCliente = 9999; // Este ID no debe existir en la BD
        Cliente resultado = BLCliente.buscarClientePorId(idCliente);
        assertNull(resultado);
    }
    
    @Test
    public void testBuscarClientePorDni_Existente() {
        String dni = "87654321"; // debe existir en la BD
        Cliente resultado = BLCliente.buscarClientePorDni(dni);
        assertNotNull(resultado);
        assertEquals(dni, resultado.getDni());
    }
    
    @Test
    public void testBuscarClientePorDni_NoExistente() {
        String dni = "99999999"; // debe no existir en la BD
        Cliente resultado = BLCliente.buscarClientePorDni(dni);
        assertNull(resultado);
    }
    
    @Test
    public void testEditarClienteExistente() {
        int idCliente = 5; // Asegúrate que este cliente exista
        boolean resultado = BLCliente.editarCliente(idCliente, "Juan Actualizado", "Calle Actualizada 456", "999999999");
        assertTrue(resultado);
    }
    
    @Test
    public void testEditarClienteInexistente() {
        int idCliente = 9999; // No debe existir
        boolean resultado = BLCliente.editarCliente(idCliente, "No Existe", "Sin Dirección", "000000000");
        assertFalse(resultado);
    }
    
    @Test
    public void testEliminarClienteSinReservas() {
        int idCliente = 12; // Asegúrate que no tenga reservas
        boolean resultado = BLCliente.eliminarCliente(idCliente);
        assertTrue(resultado);
    }
    
    @Test
    public void testEliminarClienteConReservas() {
        int idCliente = 7; // Debe tener reservas registradas
        boolean resultado = BLCliente.eliminarCliente(idCliente);
        assertFalse(resultado);
    }
    
    @Test
    public void testListarClientes() {
        ArrayList<Cliente> lista = BLCliente.listarClientes();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

}