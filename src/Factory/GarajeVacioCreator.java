/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import entidades.Garaje;

public class GarajeVacioCreator extends GarajeCreator {

    @Override
    public Garaje crearGaraje() {
        return new Garaje(); // valores por defecto
    }
}
