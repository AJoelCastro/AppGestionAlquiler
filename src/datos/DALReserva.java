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
 * @author sanar
 */

public class DALReserva {

    private static Connection cn;
    private static CallableStatement cs;
    private static ResultSet rs;

    public static int insertarReserva(Reserva r) {
        int resultado = -1;

        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_insertar_reserva(?,?,?,?)}");
            cs.setInt(1, r.getClienteId());
            cs.setInt(2, r.getAgenciaId());
            cs.setTimestamp(3, new java.sql.Timestamp(r.getFechaInicio().getTimeInMillis()));
            cs.setTimestamp(4, new java.sql.Timestamp(r.getFechaFin().getTimeInMillis()));

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
                reserva.setReservaId(rs.getInt("reserva_id"));
                reserva.setClienteId(rs.getInt("cliente_id"));
                reserva.setAgenciaId(rs.getInt("agencia_id"));
                
                Timestamp sqlFechaInicio = rs.getTimestamp("fecha_inicio");
                if (sqlFechaInicio != null) {
                    GregorianCalendar fechaInicio = new GregorianCalendar();
                    fechaInicio.setTime(sqlFechaInicio);
                    reserva.setFechaInicio(fechaInicio);
                }

                Timestamp sqlFechaFin = rs.getTimestamp("fecha_fin");
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

    public static ArrayList<Reserva> buscarReservasPorCliente(int idCliente) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_listar_reservas_por_cliente(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, idCliente);
            rs = cs.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setReservaId(rs.getInt("reserva_id"));
                reserva.setClienteId(idCliente);
                reserva.setAgenciaId(rs.getInt("agencia_id"));
                
                Timestamp sqlFechaInicio = rs.getTimestamp("fecha_inicio");
                if (sqlFechaInicio != null) {
                    GregorianCalendar fechaInicio = new GregorianCalendar();
                    fechaInicio.setTime(sqlFechaInicio);
                    reserva.setFechaInicio(fechaInicio);
                }

                Timestamp sqlFechaFin = rs.getTimestamp("fecha_fin");
                if (sqlFechaFin != null) {
                    GregorianCalendar fechaFin = new GregorianCalendar();
                    fechaFin.setTime(sqlFechaFin);
                    reserva.setFechaFin(fechaFin);
                }
                reserva.setPrecioTotal(rs.getDouble("precio_total"));
                reserva.setEntregado(rs.getBoolean("entregado"));
                reserva.setNombreAgencia(rs.getString("agencia"));
                
                reservas.add(reserva);
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
        return reservas;
    }

    public static String actualizarPrecioReserva(int reservaId, double nuevoPrecio) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_actualizar_precio_reserva(?,?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, reservaId);
            cs.setDouble(2, nuevoPrecio);
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

    public static String actualizarEstadoReserva(int reservaId, boolean entregado) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_actualizar_estado_reserva(?,?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, reservaId);
            cs.setBoolean(2, entregado);
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

                fi.setTime(rs.getTimestamp("fecha_inicio"));
                ff.setTime(rs.getTimestamp("fecha_fin"));

                Reserva r = new Reserva(
                        rs.getInt("reserva_id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("agencia_id"),
                        fi,
                        ff,
                        rs.getDouble("precio_total"),
                        rs.getBoolean("entregado")
                );
                r.setNombreCliente(rs.getString("cliente_nombre"));
                r.setNombreAgencia(rs.getString("agencia_nombre"));
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
    
    public static String editarReserva(int reservaId, int agenciaId, GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_editar_reserva(?,?,?,?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, reservaId);
            cs.setInt(2, agenciaId);
            cs.setTimestamp(3, new java.sql.Timestamp(fechaInicio.getTimeInMillis()));
            cs.setTimestamp(4, new java.sql.Timestamp(fechaFin.getTimeInMillis()));
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
    // Agregar estos métodos a la clase DALReserva

    public static ArrayList<Reserva> buscarReservasPorFecha(GregorianCalendar fechaInicio, GregorianCalendar fechaFin) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_buscar_reservas_por_fecha(?,?)}";
            cs = cn.prepareCall(sql);
            cs.setTimestamp(1, new java.sql.Timestamp(fechaInicio.getTimeInMillis()));
            cs.setTimestamp(2, new java.sql.Timestamp(fechaFin.getTimeInMillis()));
            rs = cs.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setReservaId(rs.getInt("reserva_id"));
                reserva.setClienteId(rs.getInt("cliente_id"));
                reserva.setAgenciaId(rs.getInt("agencia_id"));

                Timestamp sqlFechaInicio = rs.getTimestamp("fecha_inicio");
                if (sqlFechaInicio != null) {
                    GregorianCalendar fi = new GregorianCalendar();
                    fi.setTime(sqlFechaInicio);
                    reserva.setFechaInicio(fi);
                }

                Timestamp sqlFechaFin = rs.getTimestamp("fecha_fin");
                if (sqlFechaFin != null) {
                    GregorianCalendar ff = new GregorianCalendar();
                    ff.setTime(sqlFechaFin);
                    reserva.setFechaFin(ff);
                }

                reserva.setPrecioTotal(rs.getDouble("precio_total"));
                reserva.setEntregado(rs.getBoolean("entregado"));
                reserva.setNombreCliente(rs.getString("cliente_nombre"));
                reserva.setNombreAgencia(rs.getString("agencia_nombre"));

                reservas.add(reserva);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return reservas;
    }

    public static ArrayList<Reserva> buscarReservasPorAgencia(int agenciaId) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_buscar_reservas_por_agencia(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1, agenciaId);
            rs = cs.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setReservaId(rs.getInt("reserva_id"));
                reserva.setClienteId(rs.getInt("cliente_id"));
                reserva.setAgenciaId(rs.getInt("agencia_id"));

                Timestamp sqlFechaInicio = rs.getTimestamp("fecha_inicio");
                if (sqlFechaInicio != null) {
                    GregorianCalendar fi = new GregorianCalendar();
                    fi.setTime(sqlFechaInicio);
                    reserva.setFechaInicio(fi);
                }

                Timestamp sqlFechaFin = rs.getTimestamp("fecha_fin");
                if (sqlFechaFin != null) {
                    GregorianCalendar ff = new GregorianCalendar();
                    ff.setTime(sqlFechaFin);
                    reserva.setFechaFin(ff);
                }

                reserva.setPrecioTotal(rs.getDouble("precio_total"));
                reserva.setEntregado(rs.getBoolean("entregado"));
                reserva.setNombreCliente(rs.getString("cliente_nombre"));
                reserva.setNombreAgencia(rs.getString("agencia_nombre"));

                reservas.add(reserva);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return reservas;
    }

    public static ArrayList<Reserva> listarReservasActivas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_listar_reservas_activas()}";
            cs = cn.prepareCall(sql);
            rs = cs.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setReservaId(rs.getInt("reserva_id"));
                reserva.setClienteId(rs.getInt("cliente_id"));
                reserva.setAgenciaId(rs.getInt("agencia_id"));

                Timestamp sqlFechaInicio = rs.getTimestamp("fecha_inicio");
                if (sqlFechaInicio != null) {
                    GregorianCalendar fi = new GregorianCalendar();
                    fi.setTime(sqlFechaInicio);
                    reserva.setFechaInicio(fi);
                }

                Timestamp sqlFechaFin = rs.getTimestamp("fecha_fin");
                if (sqlFechaFin != null) {
                    GregorianCalendar ff = new GregorianCalendar();
                    ff.setTime(sqlFechaFin);
                    reserva.setFechaFin(ff);
                }

                reserva.setPrecioTotal(rs.getDouble("precio_total"));
                reserva.setEntregado(rs.getBoolean("entregado"));
                reserva.setNombreCliente(rs.getString("cliente_nombre"));
                reserva.setNombreAgencia(rs.getString("agencia_nombre"));

                reservas.add(reserva);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return reservas;
    }
}
