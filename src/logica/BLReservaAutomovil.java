/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.DALReservaAutomovil;
import entidades.ReservaAutomovil;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BLReservaAutomovil {

    public static int insertarReservaAutomovil(int reservaId, String placa, double precioAlquiler, double litrosInicial) {
        ReservaAutomovil ra = new ReservaAutomovil(reservaId, placa, precioAlquiler, litrosInicial);
        int resultado = DALReservaAutomovil.insertarReservaAutomovil(ra);
        if (resultado > 0) {
            JOptionPane.showMessageDialog(null, "Reserva de automóvil registrada correctamente", "Éxito", 1);
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la reserva del automóvil", "Error", 0);
        }
        return resultado;
    }
    
    public static ArrayList<ReservaAutomovil> listarReservaAutomovil() {
        return DALReservaAutomovil.listarReservaAutomovil();
    }
    
    public static double obtenerTotalAlquiler(int reservaId) {
        return DALReservaAutomovil.obtenerTotalAlquiler(reservaId);
    }
}
