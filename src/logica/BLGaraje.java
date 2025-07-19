/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;
import datos.DALGaraje;
import entidades.Garaje;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Asus
 */
public class BLGaraje {
    public static int insertarGaraje(String nombre, String ubicacion) {
        if (nombre.trim().length() > 0 && ubicacion.trim().length() > 0) {
            Garaje g = new Garaje();
            g.setNombre(nombre.trim());
            g.setUbicacion(ubicacion.trim());
            String mensaje = DALGaraje.insertarGaraje(g);
            if (mensaje == null) {
                JOptionPane.showMessageDialog(null, "Garaje registrado correctamente");
                return 0;
            } else {
                JOptionPane.showMessageDialog(null, mensaje, "Error", 0);
                return 1;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Datos no válidos", "Error", 0);
            return 2;
        }
    }
    
    public static Garaje buscarGarajePorId(int idGaraje){
       ArrayList<Garaje> listaG = DALGaraje.listarGarajes();
       for(Garaje garaje: listaG){
           if(garaje.getIdGaraje()==idGaraje);
               return DALGaraje.buscarGarajePorId(idGaraje);
       }
       return null;
    }
    
    public static boolean editarGaraje(int idGaraje, String nuevoNombre, String nuevaUbicacion) {
        Garaje garaje = buscarGarajePorId(idGaraje);
            if(garaje != null) {
                garaje.setNombre(nuevoNombre);
                garaje.setUbicacion(nuevaUbicacion);
                DALGaraje.actualizarGaraje(garaje);
                return true; 
            }
    return false; 
    }
    
    public static boolean eliminarGaraje(int idGaraje) {
        Garaje garaje = buscarGarajePorId(idGaraje);
            if (garaje != null) {
                DALGaraje.eliminarGaraje(idGaraje);
                return true; 
            }
    return false; 
}

    public static ArrayList<Garaje> listarGarajes() {
        return DALGaraje.listarGarajes();
    }
}
