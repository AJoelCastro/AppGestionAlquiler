/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.Automovil;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Asus
 */
public class DALAutomovil {
    private static Connection cn;
    private static CallableStatement cs;
    private static ResultSet rs;

    public static String insertarAutomovil(Automovil auto) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_insertar_automovil(?, ?, ?, ?, ?)}");
            cs.setString(1, auto.getPlaca());
            cs.setString(2, auto.getModelo());
            cs.setString(3, auto.getColor());
            cs.setString(4, auto.getMarca());
            cs.setInt(5, auto.getGarajeId());
            cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            mensaje = e.getMessage();
        } finally {
            try {
                cs.close();
                cn.close();
            } catch (SQLException e) {
                mensaje = e.getMessage();
            }
        }
        return mensaje;
    }

    public static ArrayList<Automovil> listarAutomoviles() {
        ArrayList<Automovil> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_automovil()}");
            rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new Automovil(rs.getString("placa"), rs.getString("modelo"), rs.getString("color"), rs.getString("marca"), rs.getString("garaje")
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } finally {
            try {
                rs.close();
                cs.close();
                cn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
            }
        }
        return lista;
    }
}
