/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Asus
 */
public class Garaje {
    private int idGaraje;
    private String nombre;
    private String ubicacion;

    public Garaje() {
        this(0,"ND", "ND");
    }

    public Garaje(int idGaraje,String nombre, String ubicacion) {
        this.idGaraje = idGaraje;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public int getIdGaraje() {
        return idGaraje;
    }

    public void setIdGaraje(int idGaraje) {
        this.idGaraje = idGaraje;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    
}
