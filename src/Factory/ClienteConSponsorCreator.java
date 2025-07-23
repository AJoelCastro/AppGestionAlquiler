/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Cliente;

public class ClienteConSponsorCreator extends ClienteCreator {
    private int idCliente;
    private String dni, nombre, direccion, telefono, sponsor;

    public ClienteConSponsorCreator(int idCliente, String dni, String nombre, String direccion, String telefono, String sponsor) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.sponsor = sponsor;
    }

    @Override
    public Cliente crearCliente() {
        return new Cliente(idCliente, dni, nombre, direccion, telefono, sponsor);
    }
}

