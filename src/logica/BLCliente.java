/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;
import datos.DALCliente;
import entidades.Cliente;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Asus
 */
public class BLCliente {
    public static int insertarCliente(Cliente c) {
        if (c.getDni().trim().length() == 8 &&
            !c.getNombre().trim().isEmpty() &&
            !c.getDireccion().trim().isEmpty() &&
            !c.getTelefono().trim().isEmpty()) {
            
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
    public static ArrayList<Cliente> listarClientes() {
        return DALCliente.listarClientes();
    }
}
