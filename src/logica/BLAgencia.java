/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.*;
import entidades.*;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Asus
 */
public class BLAgencia {
    public static int insertarAgencia(String nombre, String direccion) {
        String mensaje;
        if (nombre.trim().length() > 0 && direccion.trim().length() > 0) {
            Agencia ag = new Agencia();
            ag.setNombre(nombre.trim());
            ag.setDireccion(direccion.trim());

            mensaje = DALAgencia.insertarAgencia(ag);
            if (mensaje == null) {
                showMessageDialog(null, "Agencia registrada", "Resultado", 1);
                return 0;
            } else {
                showMessageDialog(null, mensaje, "Error", 0);
                return 1;
            }
        } else {
            showMessageDialog(null, "Campos vacíos", "Error", 0);
            return 2;
        }
    }
    
    public static Agencia buscarAgenciaPorId(int idAgencia){
       ArrayList<Agencia> listaA = DALAgencia.listarAgencias();
       for(Agencia agencia: listaA){
           if(agencia.getAgenciaId()==idAgencia)
               return DALAgencia.buscarAgenciaPorId(idAgencia);
       }
       return null;
    }
    
    public static boolean editarAgencia(int idAgencia, String nuevoNombre, String nuevaDireccion) {
        Agencia agencia = buscarAgenciaPorId(idAgencia);
        if (agencia != null) {
            agencia.setNombre(nuevoNombre);
            agencia.setDireccion(nuevaDireccion);
            agencia.setAgenciaId(idAgencia);

            String resultado = DALAgencia.actualizarAgencia(agencia);

            if (resultado == null) {
                return true;
            } else {
                System.err.println("Error al actualizar agencia: " + resultado);
                return false;
            }
        }
        return false; 
    }
    public static boolean eliminarAgencia(int idAgencia) {
        Agencia agencia = buscarAgenciaPorId(idAgencia);
        if (agencia != null) {
            try {
                String resultado = DALAgencia.eliminarAgencia(idAgencia);
                if (resultado == null) {
                    return true;
                } else {
                    System.err.println("Error al eliminar agencia ID " + idAgencia + ": " + resultado);
                    return false;
                }
            } catch (Exception e) {
                System.err.println("Excepción al eliminar agencia ID " + idAgencia + ": " + e.getMessage());
                return false;
            }
        }
        return false; // Agencia no encontrada
    }
    
    
    public static ArrayList<Agencia> listarAgencias() {
        return DALAgencia.listarAgencias();
    }
}
