/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Automovil;

public class AutomovilConGarajeIdCreator extends AutomovilCreator {
    private String placa, modelo, color, marca;
    private int garajeId;

    public AutomovilConGarajeIdCreator(String placa, String modelo, String color, String marca, int garajeId) {
        this.placa = placa;
        this.modelo = modelo;
        this.color = color;
        this.marca = marca;
        this.garajeId = garajeId;
    }

    @Override
    public Automovil crearAutomovil() {
        return new Automovil(placa, modelo, color, marca, garajeId);
    }
}
