/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Garaje;

public class GarajeCompletoCreator extends GarajeCreator {

    private int idGaraje;
    private String nombre;
    private String ubicacion;

    public GarajeCompletoCreator(int idGaraje, String nombre, String ubicacion) {
        this.idGaraje = idGaraje;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    @Override
    public Garaje crearGaraje() {
        return new Garaje(idGaraje, nombre, ubicacion);
    }
}
