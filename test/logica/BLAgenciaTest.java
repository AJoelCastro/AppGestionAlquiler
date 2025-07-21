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


    @Test
    public void testInsertarAgencia() {
        System.out.println("insertarAgencia");
        String nombre = "Agencia Central";
        String direccion = "Av. Lima 123";

        int resultado = BLAgencia.insertarAgencia(nombre, direccion);

        // Verifica que el resultado sea 0 (suponiendo que eso significa éxito)
        assertEquals(0, resultado);
    }

    @Test
    public void testBuscarAgenciaPorId() {
        System.out.println("buscarAgenciaPorId");
        // Insertar
        BLAgencia.insertarAgencia("Agencia Temporal", "Calle 45");

        // Obtener último ID insertado (suponiendo que se inserta al final)
        ArrayList<Agencia> agencias = BLAgencia.listarAgencias();
        Agencia ultima = agencias.get(agencias.size() - 1);

        Agencia agencia = BLAgencia.buscarAgenciaPorId(ultima.getAgenciaId());
        assertNotNull(agencia);
        assertEquals("Agencia Temporal", agencia.getNombre());

    }

    @Test
    public void testEditarAgencia() {
        System.out.println("editarAgencia");

        // Editamos una agencia existente
        boolean resultado = BLAgencia.editarAgencia(1, "Agencia Editada", "Nueva Dirección 99");

        assertTrue(resultado); // Suponiendo que devuelve true si fue editado
    }

    @Test
    public void testEliminarAgencia() {
        System.out.println("eliminarAgencia");

        // Insertar agencia para eliminar
        BLAgencia.insertarAgencia("Eliminar Test", "Calle falsa");

        // Obtener ID
        ArrayList<Agencia> agencias = BLAgencia.listarAgencias();
        Agencia ultima = agencias.get(agencias.size() - 1);
        int id = ultima.getAgenciaId();

        boolean resultado = BLAgencia.eliminarAgencia(id);

        assertTrue(resultado);
    }

    @Test
    public void testListarAgencias() {
        System.out.println("listarAgencias");

        ArrayList<Agencia> lista = BLAgencia.listarAgencias();

        assertNotNull(lista);          // Asegura que la lista no es nula
        assertTrue(lista.size() >= 0); // Puede estar vacía, pero debe existir
    }
}

