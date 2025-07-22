package logica;

import org.junit.Test;
import static org.junit.Assert.*;
import logica.BLAgencia;
import entidades.Agencia;
import java.util.ArrayList;
import java.util.List;

public class BLAgenciaTest {

    @Test
    public void testInsertarAgencia_Valido() {
        int resultado = BLAgencia.insertarAgencia("Agencia Prueba", "Av. Principal 123");
        // Esperamos que el código sea 0 (registro exitoso)
        assertEquals(0, resultado);
    }

    @Test
    public void testInsertarAgencia_CamposVacios() {
        int resultado = BLAgencia.insertarAgencia("", "");
        // Esperamos que el código sea 2 (error por campos vacíos)
        assertEquals(2, resultado);
    }
    
    @Test
    public void testBuscarAgenciaPorId_Existe() {
        
        Agencia agencia = BLAgencia.buscarAgenciaPorId(1);
        assertNotNull(agencia); // debería encontrarla
    }

    @Test
    public void testBuscarAgenciaPorId_NoExiste() {
        Agencia agencia = BLAgencia.buscarAgenciaPorId(9999); // un ID improbable
        assertNull(agencia); // no debería encontrar nada
    }
    
    @Test
    public void testEditarAgencia_Existe() {
        int idAgencia = 1; // 
        String nuevoNombre = "Agencia 12";
        String nuevaDireccion = "DIR CALLE S";

        boolean resultado = BLAgencia.editarAgencia(idAgencia, nuevoNombre, nuevaDireccion);
        assertTrue(resultado);
    }
    
    @Test
    public void testEliminarAgencia_Existe() {
        int idAgencia = 12; 
        boolean resultado = BLAgencia.eliminarAgencia(idAgencia);
        assertTrue(resultado);
    }
//    
    @Test
    public void testListarAgencias() {
        List<Agencia> agencias = BLAgencia.listarAgencias();
        assertNotNull(agencias); // Verifica que la lista no sea null
        assertTrue(agencias.size() > 0); // Verifica que haya al menos una agencia
    }
}