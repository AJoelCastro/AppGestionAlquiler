/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Cliente;

public class ClienteSinSponsorCreator extends ClienteCreator {
    private int idCliente;
    private String dni, nombre, direccion, telefono;

    public ClienteSinSponsorCreator(int idCliente, String dni, String nombre, String direccion, String telefono) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public Cliente crearCliente() {
        return new Cliente(idCliente, dni, nombre, direccion, telefono, null);
    }
}
