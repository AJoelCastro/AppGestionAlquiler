/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus
 */
public class DALReserva {

    private static Connection cn;
    private static CallableStatement cs;
    private static ResultSet rs;

    public static int insertarReserva(Reserva r) {
        int resultado = -1;

        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_insertar_reserva(?,?,?,?,?,?)}");
            cs.setInt(1, r.getClienteId());
            cs.setInt(2, r.getAgenciaId());
            cs.setDate(3, new java.sql.Date(r.getFechaInicio().getTimeInMillis()));
            cs.setDate(4, new java.sql.Date(r.getFechaFin().getTimeInMillis()));
            cs.setDouble(5, r.getPrecioTotal());
            cs.setBoolean(6, r.isEntregado());

            resultado = cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error en DALReserva", 0);
        } finally {
            try {
                if (cs != null) {
                    cs.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error al cerrar conexión", 0);
            }
        }

        return resultado;
    }

    public static Reserva buscarReservaPorId(int idReserva) {
        Reserva reserva = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_buscar_reserva_por_id(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, idReserva);
            rs = cs.executeQuery();

            if (rs.next()) {
                reserva = new Reserva();
                reserva.setReservaId(idReserva);
                reserva.setClienteId(rs.getInt("cliente_id"));
                reserva.setAgenciaId(rs.getInt("agencia_id"));
                Date sqlFechaInicio = rs.getDate("fecha_inicio");
                if (sqlFechaInicio != null) {
                    GregorianCalendar fechaInicio = new GregorianCalendar();
                    fechaInicio.setTime(sqlFechaInicio);
                    reserva.setFechaInicio(fechaInicio);
                }

                Date sqlFechaFin = rs.getDate("fecha_fin");
                if (sqlFechaFin != null) {
                    GregorianCalendar fechaFin = new GregorianCalendar();
                    fechaFin.setTime(sqlFechaFin);
                    reserva.setFechaFin(fechaFin);
                }
                reserva.setPrecioTotal(rs.getDouble("precio_total"));
                reserva.setEntregado(rs.getBoolean("entregado"));

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
        return reserva;
    }

    public static String actualizarReserva(Reserva r) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_actualizar_reserva(?,?,?,?,?,?)}";
            cs.setInt(1, r.getClienteId());
            cs.setInt(2, r.getAgenciaId());
            cs.setDate(3, new java.sql.Date(r.getFechaInicio().getTimeInMillis()));
            cs.setDate(4, new java.sql.Date(r.getFechaFin().getTimeInMillis()));
            cs.setDouble(5, r.getPrecioTotal());
            cs.setBoolean(6, r.isEntregado());

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

    public static String eliminarReserva(int idReserva) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_eliminar_reserva(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, idReserva);
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

    public static ArrayList<Reserva> listarReservas() {
        ArrayList<Reserva> lista = new ArrayList<>();
        Connection cn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_reservas()}");
            rs = cs.executeQuery();

            while (rs.next()) {
                GregorianCalendar fi = new GregorianCalendar();
                GregorianCalendar ff = new GregorianCalendar();

                fi.setTime(rs.getDate("fecha_inicio"));
                ff.setTime(rs.getDate("fecha_fin"));

                Reserva r = new Reserva(
                        rs.getInt("reserva_id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("agencia_id"),
                        fi,
                        ff,
                        rs.getDouble("precio_total"),
                        rs.getBoolean("entregado")
                );
                lista.add(r);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error en listado", 0);
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
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error cerrando", 0);
            }
        }
        return lista;
    }
}
