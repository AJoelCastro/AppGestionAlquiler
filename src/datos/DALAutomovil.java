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
                auto.setGarajeId(rs.getInt("garaje_id"));
                auto.setNombreGaraje(rs.getString("direccion")); //???

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
            String sql = "{call sp_actualizar_automovil(?, ?, ?, ?, ?)}";
            cs = cn.prepareCall(sql);
            cs.setString(1, auto.getPlaca());
            cs.setString(2, auto.getModelo());
            cs.setString(3, auto.getColor());
            cs.setString(4, auto.getMarca());
            cs.setInt(5, auto.getGarajeId());
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
                estado = rs.getString("estado_disponibilidad");
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

    public static ArrayList<Automovil> listarAutomovilesPorGaraje(String garaje) {
        ArrayList<Automovil> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_automovil_por_garaje(?)}");
            cs.setString(1, garaje);
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
