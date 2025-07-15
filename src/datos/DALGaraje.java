/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.Garaje;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Asus
 */
public class DALGaraje {
    private static Connection cn;
    private static CallableStatement cs;
    private static ResultSet rs;

    public static String insertarGaraje(Garaje g) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_insertar_garaje(?, ?)}");
            cs.setString(1, g.getNombre());
            cs.setString(2, g.getUbicacion());
            cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            mensaje = ex.getMessage();
        } finally {
            try {
                cs.close();
                cn.close();
            } catch (SQLException ex) {
                mensaje = ex.getMessage();
            }
        }
        return mensaje;
    }

    public static ArrayList<Garaje> listarGarajes() {
        ArrayList<Garaje> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_garajes()}");
            rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new Garaje(rs.getString("nombre"), rs.getString("ubicacion")));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        } finally {
            try {
                rs.close();
                cs.close();
                cn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
            }
        }
        return lista;
    }
}
