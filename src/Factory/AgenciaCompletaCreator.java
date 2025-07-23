/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Agencia;

public class AgenciaCompletaCreator extends AgenciaCreator {

    private int id;
    private String nombre;
    private String direccion;

    public AgenciaCompletaCreator(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    @Override
    public Agencia crearAgencia() {
        return new Agencia(id, nombre, direccion);
    }
}
