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

    public static ArrayList<Agencia> listarAgencias() {
        return DALAgencia.listarAgencias();
    }
}
