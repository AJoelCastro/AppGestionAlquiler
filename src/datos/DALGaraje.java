/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.Garaje;
import entidades.Automovil;
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
    
        public static Garaje buscarGarajePorId(int idGaraje) {
        Garaje garaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_buscar_garaje_por_id(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1,idGaraje);
            rs = cs.executeQuery();

            if (rs.next()) {
                garaje = new Garaje();
                garaje.setIdGaraje(idGaraje);
                garaje.setNombre(rs.getString("nombre"));
                garaje.setUbicacion(rs.getString("ubicacion"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return garaje;
    }
        
       public static String actualizarGaraje(Garaje garaje) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_actualizar_garaje(?, ?)}";
            cs.setInt(1, garaje.getIdGaraje());
            cs.setString(2, garaje.getNombre());
            cs.setString(3, garaje.getUbicacion());
            cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            mensaje = ex.getMessage();
        } finally {
            try {
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                mensaje = ex.getMessage();
            }
        }
        return mensaje;
    }
       
    public static String eliminarGaraje(int idGaraje) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_eliminar_garaje(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1,idGaraje);
            cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            mensaje = ex.getMessage();
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (cn != null) {
                    cn.close();
                }
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
                lista.add(new Garaje(rs.getInt("garaje_id"),rs.getString("nombre"), rs.getString("ubicacion")));
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
    
     public static ArrayList<Automovil> listarAutomovilesPorGaraje(int garaje) {
        ArrayList<Automovil> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_automovil_por_garaje(?)}");
            cs.setInt(1, garaje);
            rs = cs.executeQuery();
            while (rs.next()) {
                lista.add(new Automovil(
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("color"),
                        rs.getString("marca"),
                        rs.getString("garaje")
                ));
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
            }
        }
        return lista;
    }
}
