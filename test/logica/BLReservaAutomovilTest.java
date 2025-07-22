/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package logica;

import entidades.ReservaAutomovil;
import java.util.ArrayList;
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
public class BLReservaAutomovilTest {
    
    public BLReservaAutomovilTest() {
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
     * Test of insertarReservaAutomovil method, of class BLReservaAutomovil.
     */
     @Test
    public void testInsertarReservaAutomovil() {
        // Arrange
        int reservaId = 14; // debe existir en BD
        String placa = "XYZ455"; // debe existir en Automovil
        double precioAlquiler = 100.0;
        double litrosInicial = 20.0;

        // Act
        int resultado = BLReservaAutomovil.insertarReservaAutomovil(reservaId, placa, precioAlquiler, litrosInicial);

        // Assert
        assertEquals(1, resultado); // 1 si se insertó con éxito
    }


    /**
     * Test of listarReservaAutomovil method, of class BLReservaAutomovil.
     */
    @Test
    public void testListarReservaAutomovil() {
        // Act
        ArrayList<ReservaAutomovil> lista = BLReservaAutomovil.listarReservaAutomovil();

        // Assert
        assertNotNull(lista);
        assertTrue(lista.size() > 0); // Asumiendo que hay al menos una
    }

    /**
     * Test of obtenerTotalAlquiler method, of class BLReservaAutomovil.
     */
    @Test
    public void testObtenerTotalAlquiler() {
        // Arrange
        int reservaId = 11; // debe existir

        // Act
        double total = BLReservaAutomovil.obtenerTotalAlquiler(reservaId);

        // Assert
        assertTrue(total >= 0); // puede ser 0 si aún no hay autos
    }

    /**
     * Test of asignarAutomovilesAReserva method, of class BLReservaAutomovil.
     */
    @Test
    public void testAsignarAutomovilesAReserva() {
        // Arrange
        int reservaId = 13; // Asegúrate de que exista esta reserva en la base de datos

        ReservaAutomovil auto1 = new ReservaAutomovil(reservaId, "ABC123", 100.0, 10.0);
        ReservaAutomovil auto2 = new ReservaAutomovil(reservaId, "XYZ456", 150.0, 15.0);

        ArrayList<ReservaAutomovil> lista = new ArrayList<>();
        lista.add(auto1);
        lista.add(auto2);

        // Act
        boolean resultado = BLReservaAutomovil.asignarAutomovilesAReserva(reservaId, lista);

        // Assert
        assertTrue(resultado); // Debería ser true si todo fue exitoso
    }
    
}
