/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Cliente;

/**
 *
 * @author Arturo
 */
public class ClienteFactory {
    public static Cliente crearClienteConSponsor(String dni, String nombre, String direccion, String telefono, String sponsor) {
        return new Cliente(dni, nombre, direccion, telefono, sponsor);
    }

    public static Cliente crearClienteSinSponsor(String dni, String nombre, String direccion, String telefono) {
        return new Cliente(dni, nombre, direccion, telefono, null);
    }

    public static Cliente crearClienteVacio() {
        return new Cliente(); // valores por defecto
    }
}
