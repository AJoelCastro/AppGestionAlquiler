/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.DALCliente;
import datos.DALReserva;
import entidades.Cliente;
import entidades.Reserva;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class BLCliente {

    public static int insertarCliente(Cliente c) {
        if (c.getDni().trim().length() == 8
                && !c.getNombre().trim().isEmpty()
                && !c.getDireccion().trim().isEmpty()
                && !c.getTelefono().trim().isEmpty()) {

            String mensaje = DALCliente.insertarCliente(c);
            if (mensaje == null) {
                JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
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

    public static Cliente buscarClientePorId(int idCliente) {
        ArrayList<Cliente> listaC = DALCliente.listarClientes();
        for (Cliente c : listaC) {
            if (c.getIdCliente() == idCliente);
            return DALCliente.buscarClientePorId(idCliente);
        }
        return null;
    }

    public static boolean editarCliente(int idCliente, String nuevoNombre, String nuevaDireccion, String nuevoTelefono) {
        Cliente Cliente = buscarClientePorId(idCliente);
        if (Cliente != null) {
            Cliente.setNombre(nuevoNombre);
            Cliente.setDireccion(nuevaDireccion);
            Cliente.setTelefono(nuevoTelefono);
            DALCliente.actualizarCliente(Cliente);
            return true;
        }
        return false;
    }

    public static boolean eliminarCliente(int idCliente) {
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente != null) {
            int cantReserv = 0;
            ArrayList<Reserva> listaR = DALReserva.listarReservas();
            for (Reserva reserva : listaR) {
                if(reserva.getClienteId()== idCliente);
                    cantReserv++;
            }
            if(cantReserv==0) {
                DALCliente.eliminarCliente(idCliente);
                return true; }
        }
        return false;
    }

    public static ArrayList<Cliente> listarClientes() {
        return DALCliente.listarClientes();
    }
    
//    public static ArrayList<Reserva> obtenerReservasPorCliente(int idCliente) {
//        return DALCliente.listarReservasPorCliente(idCliente);
//    }
}
