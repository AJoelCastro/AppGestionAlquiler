/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.DALAutomovil;
import datos.DALReservaAutomovil;
import entidades.Automovil;
import entidades.ReservaAutomovil;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class BLAutomovil {

    public static int insertarAutomovil(String placa, String modelo, String color, String marca, int garajeId) {
        String mensaje = null;
        if (placa.trim().length() > 0 && modelo.trim().length() > 0 && marca.trim().length() > 0) {
            Automovil auto = new Automovil(placa, modelo, color, marca, garajeId, "disponible");
            mensaje = DALAutomovil.insertarAutomovil(auto);
            if (mensaje == null) {
                JOptionPane.showMessageDialog(null, "Automóvil registrado correctamente", "Éxito", 1);
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

    public static Automovil buscarAutomovilPorPlaca(String placaAuto) {
        ArrayList<Automovil> listaAutos = DALAutomovil.listarAutomoviles();
        for (Automovil autos : listaAutos) {
            if (autos.getPlaca().equals(placaAuto)) {
                return DALAutomovil.buscarAutomovilPorPlaca(placaAuto);
            }
        }
        return null;
    }

    public static boolean editarAutomovil(String placa, String nuevoModelo, String nuevoColor, String nuevaMarca, int nuevoGarajeId) {
        Automovil auto = buscarAutomovilPorPlaca(placa);
        if (auto != null) {
            auto.setModelo(nuevoModelo);
            auto.setColor(nuevoColor);
            auto.setMarca(nuevaMarca);
            auto.setGarajeId(nuevoGarajeId);
            DALAutomovil.actualizarAutomovil(auto);
            return true;
        }
        return false;
    }

    public static boolean eliminarAutomovil(String placa) {
        Automovil auto = buscarAutomovilPorPlaca(placa);
        if (auto != null) {
            int cantReserv = 0;
            ArrayList<ReservaAutomovil> listaRA = DALReservaAutomovil.listarReservaAutomovil();
            for (ReservaAutomovil reservaA : listaRA) {
                if(reservaA.getPlaca().equals(placa));
                    cantReserv++;
            }
            if(cantReserv==0) {
                DALAutomovil.eliminarAutomovil(placa);
                return true; }
        }
        return false;
    }
    
    public static String verificarDisponibilidadAutomovil(String placa) {
        Automovil auto = buscarAutomovilPorPlaca(placa);

        if (auto != null) {
            ArrayList<ReservaAutomovil> listaRA = DALReservaAutomovil.listarReservaAutomovil();
            for (ReservaAutomovil reservaA : listaRA) {
                if (reservaA.getPlaca().equals(placa)) {
                    return "En reserva";
                }
            }
            return "Disponible";
        }

        return "No encontrado";
    }

    public static ArrayList<Automovil> listarAutomoviles() {
        return DALAutomovil.listarAutomoviles();
    }
}
