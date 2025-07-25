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
            cs = cn.prepareCall("{call sp_insertar_automovil(?,?, ?, ?, ?, ?)}");
            cs.setString(1, auto.getPlaca());
            cs.setString(2, auto.getModelo());
            cs.setString(3, auto.getColor());
            cs.setString(4, auto.getMarca());
            cs.setString(5, auto.getEstado()); // nuevo
            cs.setInt(6, auto.getGarajeId());
            cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            mensaje = e.getMessage();
        } finally {
            try {
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                mensaje = e.getMessage();
            }
        }
        return mensaje;
    }

    public static Automovil buscarAutomovilPorPlaca(String placaAuto) {
        Automovil auto = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_buscar_automovil_por_placa(?)}";
            cs = cn.prepareCall(sql);
            cs.setString(1, placaAuto);
            rs = cs.executeQuery();

            if (rs.next()) {
                auto = new Automovil();
                auto.setPlaca(placaAuto);
                auto.setModelo(rs.getString("modelo"));
                auto.setColor(rs.getString("color"));
                auto.setMarca(rs.getString("marca"));
                auto.setEstado(rs.getString("estado"));
                auto.setGarajeId(rs.getInt("garaje_id"));
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
        return auto;
    }

    public static String actualizarAutomovil(Automovil auto) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();

            // Cambiar el SP call para que coincida con los parámetros correctos
            String sql = "{call sp_actualizar_automovil(?, ?, ?, ?)}";
            cs = cn.prepareCall(sql);
            cs.setString(1, auto.getPlaca());     // p_placa
            cs.setString(2, auto.getModelo());    // p_modelo  
            cs.setString(3, auto.getColor());     // p_color
            cs.setString(4, auto.getMarca());     // p_marca

            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas == 0) {
                mensaje = "No se encontró el automóvil o no se realizaron cambios";
            }

        } catch (ClassNotFoundException | SQLException ex) {
            mensaje = ex.getMessage();
            ex.printStackTrace();
        } finally {
            try {
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                if (mensaje == null) mensaje = ex.getMessage();
            }
        }
        return mensaje;
    }


    public static String eliminarAutomovil(String placaAuto) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_eliminar_automovil(?)}";
            cs = cn.prepareCall(sql);
            cs.setString(1, placaAuto);
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

    public static String verificarDisponibilidadAutomovil(String placa) {
        String estado = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_verificar_disponibilidad_automovil(?)}";
            cs = cn.prepareCall(sql);
            cs.setString(1, placa);
            rs = cs.executeQuery();

            if (rs.next()) {
                estado = rs.getString("estado");
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
        return estado;
    }

    public static ArrayList<Automovil> listarAutomoviles() {
        ArrayList<Automovil> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_automovil()}");
            rs = cs.executeQuery();
            while (rs.next()) {
                Automovil auto = new Automovil();
                auto.setPlaca(rs.getString("placa"));
                auto.setModelo(rs.getString("modelo"));
                auto.setColor(rs.getString("color"));
                auto.setMarca(rs.getString("marca"));
                auto.setEstado(rs.getString("estado"));
                auto.setNombreGaraje(rs.getString("garaje"));
                auto.setGarajeId(0);

                lista.add(auto);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
            }
        }
        return lista;
    }

}
