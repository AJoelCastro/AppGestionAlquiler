package logica;

import entidades.Automovil;
import static org.junit.Assert.*;
import org.junit.Test;
import logica.BLGaraje;
import entidades.Garaje;
import java.util.ArrayList;
import java.util.List;

public class BLGarajeTest {
    
    @Test
    public void testInsertarGaraje_Valido() {
        int resultado = BLGaraje.insertarGaraje("Garaje Central", "Calle 123");
        assertEquals(0, resultado); // 0 = éxito
    }
    
    @Test
    public void testInsertarGaraje_Invalido() {
        int resultado = BLGaraje.insertarGaraje("", "");
        assertEquals(2, resultado); // 2 = campos vacíos
    }
    
    @Test
    public void testBuscarGarajePorId_Existe() {
        Garaje garaje = BLGaraje.buscarGarajePorId(1); // Ajusta si el ID real es otro
        assertNotNull(garaje); // debería encontrarlo
    }

    @Test
    public void testBuscarGarajePorId_NoExiste() {
        Garaje garaje = BLGaraje.buscarGarajePorId(9999); // ID improbable
        assertNull(garaje); // no debería encontrar nada
    }
    
    @Test
    public void testEditarGaraje_Existe() {
        Garaje garaje = new Garaje();
        garaje.setIdGaraje(1); // Este ID debe existir
        garaje.setNombre("Garaje Actualizado");
        garaje.setUbicacion("Ubicación nueva 456");

        boolean resultado = BLGaraje.editarGaraje(garaje.getIdGaraje(), garaje.getNombre(), garaje.getUbicacion());
        assertTrue(resultado); // Se espera que se actualice correctamente
    }
    
    @Test
    public void testEliminarGaraje_Existe() {
        // El garaje con ID 7 debe existir y no estar relacionado con otras tablas
        boolean resultado = BLGaraje.eliminarGaraje(13);
        assertTrue(resultado); // Se espera eliminación exitosa
    }
    
    @Test
    public void testListarGarajes() {
        ArrayList<Garaje> lista = BLGaraje.listarGarajes();
        assertNotNull(lista);
        assertTrue(lista.size() >= 0); // puede estar vacía pero no debe ser null
    }
    
    @Test
    public void testListarAutomovilesPorGaraje_Existe() {
        int idGaraje = 1; // debe existir un garaje con este ID y tener autos o no
        ArrayList<Automovil> lista = BLGaraje.listarAutomovilesPorGaraje(idGaraje);
        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }
}