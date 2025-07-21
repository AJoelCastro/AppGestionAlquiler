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
    public static boolean asignarAutomovilesAReserva(int reservaId, ArrayList<ReservaAutomovil> autosAsignados) {
        if (reservaId <= 0 || autosAsignados == null || autosAsignados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Datos no válidos para la asignación", "Error", 0);
            return false;
        }

        boolean todosInsertados = true;
        double precioTotalCalculado = 0.0;

        for (ReservaAutomovil ra : autosAsignados) {
            int resultado = DALReservaAutomovil.insertarReservaAutomovil(ra);
            if (resultado <= 0) {
                todosInsertados = false;
                JOptionPane.showMessageDialog(null, 
                    "Error al asignar el automóvil con placa: " + ra.getPlaca(), "Error", 0);
            } else {
                precioTotalCalculado += ra.getPrecioAlquiler();
            }
        }

        if (todosInsertados) {
            boolean precioActualizado = BLReserva.actualizarPrecioReserva(reservaId);

            if (precioActualizado) {
                JOptionPane.showMessageDialog(null, 
                    "Automóviles asignados correctamente\nPrecio total: $" + 
                    String.format("%.2f", precioTotalCalculado), "Éxito", 1);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Automóviles asignados pero error al actualizar precio total", "Advertencia", 2);
                return false;
            }
        }

        return false;
    }
}
