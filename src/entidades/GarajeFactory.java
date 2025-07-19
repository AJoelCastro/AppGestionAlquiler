/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Arturo
 */
public class GarajeFactory {
    
    public static Garaje crearGarajeCompleto(int idGaraje,String nombre, String ubicacion) {
        return new Garaje(idGaraje,nombre, ubicacion);
    }
    
    public static Garaje crearGarajeVacio() {
        return new Garaje(); // valores por defecto
    }
}