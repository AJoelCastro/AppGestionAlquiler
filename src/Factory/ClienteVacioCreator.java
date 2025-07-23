/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Cliente;

public class ClienteVacioCreator extends ClienteCreator {

    @Override
    public Cliente crearCliente() {
        return new Cliente(); // valores por defecto
    }
}
