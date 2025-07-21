package logica;

import entidades.Automovil;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
public class BLAutomovilTest {

    public BLAutomovilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insertarAutomovil method, of class BLAutomovil.
     */
    @Test
    public void testInsertarAutomovil() {
        String placa = "WWW777"; // Placa nueva y única
        String modelo = "Hyundai";
        String color = "Negro";
        String marca = "Honda";
        int garajeId = 1;

        int resultado = BLAutomovil.insertarAutomovil(placa, modelo, color, marca, garajeId);
        assertEquals("Error al insertar automóvil", 0, resultado);
    }

    /**
     * Test of buscarAutomovilPorPlaca method, of class BLAutomovil.
     */
    @Test
    public void testBuscarAutomovilPorPlaca() {
        String placa = "III999"; // Asegúrate de que este auto YA exista en tu BD
        String modeloEsperado = "Golf";

        Automovil auto = BLAutomovil.buscarAutomovilPorPlaca(placa);

        assertNotNull("No se encontró el automóvil con la placa indicada", auto);
        assertEquals("Modelo incorrecto", modeloEsperado, auto.getModelo());
    }

    /**
     * Test of editarAutomovil method, of class BLAutomovil.
     */
    @Test
    public void testEditarAutomovil() {
        System.out.println("editarAutomovil");
        String placa = "XXX123";
        String nuevoModelo = "Justines";
        String nuevoColor = "Turquesa";
        String nuevaMarca = "Papu";
        boolean expResult = true;
        boolean result = BLAutomovil.editarAutomovil(placa, nuevoModelo, nuevoColor, nuevaMarca);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of eliminarAutomovil method, of class BLAutomovil.
     */
    @Test
    public void testEliminarAutomovil() {
        System.out.println("eliminarAutomovil");

        String placa = "YYY777";

        // Primero lo insertamos, por si no está
        BLAutomovil.insertarAutomovil(placa, "Temporal", "Rojo", "Prueba", 1);

        // Asegurarse que NO tiene reservas antes de eliminar
        // (O limpiar reservas en la BD si estás en entorno de pruebas)
        boolean resultado = BLAutomovil.eliminarAutomovil(placa);
        assertTrue("No se pudo eliminar el auto con placa " + placa, resultado);
    }

    /**
     * Test of verificarDisponibilidadAutomovil method, of class BLAutomovil.
     */
    @Test
    public void testVerificarDisponibilidadAutomovil() {
        System.out.println("verificarDisponibilidadAutomovil");
        String placa = "JJJ000";
        String expResult = "En Reserva";
        String result = BLAutomovil.verificarDisponibilidadAutomovil(placa);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of listarAutomoviles method, of class BLAutomovil.
     */
    @Test
    public void testListarAutomoviles() {
        System.out.println("listarAutomoviles");
        ArrayList<Automovil> lista = BLAutomovil.listarAutomoviles();
        assertNotNull(lista);
        assertTrue(lista.size()>=0);
    }
}