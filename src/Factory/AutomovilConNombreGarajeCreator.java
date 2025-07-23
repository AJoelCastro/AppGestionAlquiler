/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Automovil;

public class AutomovilConNombreGarajeCreator extends AutomovilCreator {

    private String placa, modelo, color, marca, nombreGaraje;

    public AutomovilConNombreGarajeCreator(String placa, String modelo, String color, String marca, String nombreGaraje) {
        this.placa = placa;
        this.modelo = modelo;
        this.color = color;
        this.marca = marca;
        this.nombreGaraje = nombreGaraje;
    }

    @Override
    public Automovil crearAutomovil() {
        return new Automovil(placa, modelo, color, marca, nombreGaraje);
    }
}
