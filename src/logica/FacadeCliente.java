/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import entidades.Cliente;
import entidades.ClienteFactory;
import java.util.ArrayList;

public class FacadeCliente {

    public static int registrarCliente(String dni, String nombre, String direccion, String telefono, String sponsor) {
        Cliente cliente;

        if (sponsor == null || sponsor.trim().isEmpty()) {
            cliente = ClienteFactory.crearClienteSinSponsor(dni, nombre, direccion, telefono);
        } else {
            cliente = ClienteFactory.crearClienteConSponsor(dni, nombre, direccion, telefono, sponsor);
        }

        return BLCliente.insertarCliente(cliente);
    }

    public static ArrayList<Cliente> obtenerClientes() {
        return BLCliente.listarClientes();
    }
}
