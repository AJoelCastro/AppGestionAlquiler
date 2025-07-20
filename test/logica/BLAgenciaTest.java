/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package logica;

import entidades.Agencia;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class BLAgenciaTest {

    private static int idAgenciaExistente;

    @BeforeClass
    public static void setUpClass() {
        System.out.println(">>> [TEST] Obteniendo una agencia existente...");
        ArrayList<Agencia> agencias = BLAgencia.listarAgencias();
        assertFalse("Debe haber al menos una agencia en la base de datos", agencias.isEmpty());
        idAgenciaExistente = agencias.get(0).getAgenciaId();  // Tomamos el ID de la primera agencia
    }

    @Test
    public void testBuscarAgenciaPorId() {
        System.out.println("buscarAgenciaPorId");
        Agencia result = BLAgencia.buscarAgenciaPorId(idAgenciaExistente);
        assertNotNull("La agencia debería existir", result);
        assertEquals("El ID no coincide", idAgenciaExistente, result.getAgenciaId());
    }

    @Test
    public void testListarAgencias() {
        System.out.println("listarAgencias");
        ArrayList<Agencia> agencias = BLAgencia.listarAgencias();
        assertNotNull("La lista no debe ser nula", agencias);
        assertTrue("Debe existir al menos una agencia", agencias.size() > 0);
    }

    @Test
    public void testEditarAgencia() {
        System.out.println("editarAgencia");
        String nuevoNombre = "Agencia Modificada Test";
        String nuevaDireccion = "Av. Modificada Test";
        boolean result = BLAgencia.editarAgencia(idAgenciaExistente, nuevoNombre, nuevaDireccion);
        assertTrue("La edición debería ser exitosa", result);

        // Verificamos el cambio
        Agencia editada = BLAgencia.buscarAgenciaPorId(idAgenciaExistente);
        assertEquals("El nombre no se actualizó correctamente", nuevoNombre, editada.getNombre());
        assertEquals("La dirección no se actualizó correctamente", nuevaDireccion, editada.getDireccion());
    }

    @Test
    public void testInsertarAgencia() {
        System.out.println("insertarAgencia");
        String nombre = "Agencia Nueva Test";
        String direccion = "Av. Nueva Test";

        int result = BLAgencia.insertarAgencia(nombre, direccion);
        assertTrue("La inserción debería devolver un número positivo", result > 0);

        // Verificamos que la nueva agencia está en la lista
        Agencia nueva = BLAgencia.buscarAgenciaPorId(result);
        assertNotNull("La nueva agencia debería existir en la BD", nueva);

        // Limpiamos borrando la agencia
        BLAgencia.eliminarAgencia(result);
    }

    @Test
    public void testEliminarAgencia() {
        System.out.println("eliminarAgencia");
        // Primero insertamos una agencia para eliminar
        int idTemp = BLAgencia.insertarAgencia("Agencia Temp", "Av. Temp");
        assertTrue("La agencia temporal debe insertarse correctamente", idTemp > 0);

        // Ahora la eliminamos
        boolean result = BLAgencia.eliminarAgencia(idTemp);
        assertTrue("La agencia temporal debe eliminarse", result);
    }
}