/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Arturo
 */
public class AutomovilFactory {
    
    public static Automovil crearAutomovilConGarajeId(String placa, String modelo, String color, String marca, int garajeId) {
        return new Automovil(placa, modelo, color, marca, garajeId);
    }
    
    public static Automovil crearAutomovilConNombreGaraje(String placa, String modelo, String color, String marca, String nombreGaraje) {
        return new Automovil(placa, modelo, color, marca, nombreGaraje);
    }
    
    public static Automovil crearAutomovilCompleto(String placa, String modelo, String color, String marca, String estado, int garajeId) {
    return new Automovil(placa, modelo, color, marca, estado, garajeId);
    }
    
    public static Automovil crearAutomovilVacio() {
        return new Automovil(); // valores por defecto
    }
}