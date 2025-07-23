/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Agencia;

public class AgenciaSinIdCreator extends AgenciaCreator {

    private String nombre;
    private String direccion;

    public AgenciaSinIdCreator(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    @Override
    public Agencia crearAgencia() {
        return new Agencia(0, nombre, direccion);
    }
}
