/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import entidades.Cliente;

/**
 *
 * @author Arturo
 */
public class ClienteFactory {
    public static Cliente crearClienteConSponsor(int idCliente, String dni, String nombre, String direccion, String telefono, String sponsor) {
        return new Cliente(idCliente,dni, nombre, direccion, telefono, sponsor);
    }

    public static Cliente crearClienteSinSponsor(int idCliente,String dni, String nombre, String direccion, String telefono) {
        return new Cliente(idCliente,dni, nombre, direccion, telefono, null);
    }

    public static Cliente crearClienteVacio() {
        return new Cliente(); // valores por defecto
    }
}
