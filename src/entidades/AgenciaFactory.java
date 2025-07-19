/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Arturo
 */
public class AgenciaFactory {
    
    public static Agencia crearAgenciaCompleta(int agenciaId, String nombre, String direccion) {
        return new Agencia(agenciaId, nombre, direccion);
    }
    
    public static Agencia crearAgenciaSinId(String nombre, String direccion) {
        return new Agencia(0, nombre, direccion);
    }
    
    public static Agencia crearAgenciaVacia() {
        return new Agencia(); // valores por defecto
    }
}