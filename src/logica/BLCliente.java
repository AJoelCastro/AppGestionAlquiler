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
        if (c.getDni().trim().length() != 8
                || c.getNombre().trim().isEmpty()
                || c.getDireccion().trim().isEmpty()
                || c.getTelefono().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Datos no válidos", "Error", 0);
            return 2; 
        }

        if (buscarClientePorDni(c.getDni()) != null) {
            JOptionPane.showMessageDialog(null, "El Dni ingresado ya esta registrado", "Error", 0);
            return 3; 
        }

        String mensaje = DALCliente.insertarCliente(c);
        if (mensaje == null) {
            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");
            return 0; 
        } else {
            JOptionPane.showMessageDialog(null, mensaje, "Error en la BD", 0);
            return 1; 
        }
    }

    public static Cliente buscarClientePorId(int idCliente) {
        ArrayList<Cliente> listaC = DALCliente.listarClientes();
        for (Cliente c : listaC) {
            if (c.getIdCliente() == idCliente) {  
                return DALCliente.buscarClientePorId(idCliente);
            }
        }
        return null;
    }

    public static Cliente buscarClientePorDni(String dni) {
        ArrayList<Cliente> listaC = DALCliente.listarClientes();
        for (Cliente c : listaC) {
            if (c.getDni().equals(dni)){
                return DALCliente.buscarClientePorDni(dni);
            }
        }
        return null;
    }

    public static boolean editarCliente(int idCliente, String nuevoNombre, String nuevaDireccion, String nuevoTelefono) {
        // Validaciones básicas
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty() ||
            nuevaDireccion == null || nuevaDireccion.trim().isEmpty() ||
            nuevoTelefono == null || nuevoTelefono.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", 0);
            return false;
        }

        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente != null) {
            // Actualizar solo los campos que se pueden modificar
            cliente.setNombre(nuevoNombre.trim());
            cliente.setDireccion(nuevaDireccion.trim());
            cliente.setTelefono(nuevoTelefono.trim());
            // Mantener el sponsor existente

            String mensaje = DALCliente.actualizarCliente(cliente);

            if (mensaje == null) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar: " + mensaje, "Error", 0);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", 0);
            return false;
        }
    }

    public static boolean eliminarCliente(int idCliente) {
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente != null) {
            int cantReserv = 0;
            ArrayList<Reserva> listaR = DALReserva.listarReservas();
            for (Reserva reserva : listaR) {
                if(reserva.getClienteId()== idCliente) {
                    cantReserv++;
                }
            }
            if (cantReserv == 0) {
                DALCliente.eliminarCliente(idCliente);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Cliente> listarClientes() {
        return DALCliente.listarClientes();
    }
    
    public static ArrayList<Reserva> obtenerReservasPorCliente(int idCliente) {
        return DALCliente.listarReservasPorCliente(idCliente);
    }

}
