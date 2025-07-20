/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.Cliente;
import entidades.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
/**
 *
 * @author Asus
 */
public class DALCliente {
    private static Connection cn;
    private static CallableStatement cs;
    private static ResultSet rs;

    public static String insertarCliente(Cliente c) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_insertar_cliente(?, ?, ?, ?, ?)}");
            cs.setString(1, c.getDni());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getDireccion());
            cs.setString(4, c.getTelefono());

            if (c.getSponsor() == null || c.getSponsor().isEmpty()) {
                cs.setNull(5, Types.VARCHAR);
            } else {
                cs.setString(5, c.getSponsor());
            }

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
    
    public static Cliente buscarClientePorId(int idCliente) {
        Cliente cliente = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_buscar_cliente_por_id(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1,idCliente);
            rs = cs.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(idCliente);
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("mombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setSponsor(rs.getString("sponsor_id")); 

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
        return cliente;
    }

       public static String actualizarCliente(Cliente cliente) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_actualizar_cliente(?, ?, ?, ?, ?)}";
            cs.setString(1, cliente.getDni());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getDireccion());
            cs.setString(4, cliente.getTelefono());

            if (cliente.getSponsor() == null || cliente.getSponsor().isEmpty()) {
                cs.setNull(5, Types.VARCHAR);
            } else {
                cs.setString(5, cliente.getSponsor());
            }

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
       
       public static String eliminarCliente(int idCliente) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_eliminar_cliente(?)}";
            cs = cn.prepareCall(sql);
            cs.setInt(1,idCliente);
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
    
    public static ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_clientes()}");
            rs = cs.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("cliente_id"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("sponsor") 
                );
                lista.add(c);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", 0);
            }
        }
        return lista;
    }
    
        public static ArrayList<Reserva> listarReservasPorCliente(int idCliente) {
    ArrayList<Reserva> reservas = new ArrayList<>();
    try {
        cn = Conexion.realizarconexion();
        String sql = "{call sp_listar_reservas_por_cliente(?)}";
        cs = cn.prepareCall(sql);
        cs.setInt(1, idCliente);
        rs = cs.executeQuery();

        while (rs.next()) {
            Reserva r = new Reserva();
            r.setReservaId(rs.getInt("reserva_id"));
            r.setClienteId(idCliente);
            r.setAgenciaId(rs.getInt("agencia_id"));

            // Convertir fechas SQL a GregorianCalendar
            java.sql.Timestamp sqlInicio = rs.getTimestamp("fecha_inicio");
            if (sqlInicio != null) {
                GregorianCalendar fechaInicio = new GregorianCalendar();
                fechaInicio.setTimeInMillis(sqlInicio.getTime());
                r.setFechaInicio(fechaInicio);
            }

            java.sql.Timestamp sqlFin = rs.getTimestamp("fecha_fin");
            if (sqlFin != null) {
                GregorianCalendar fechaFin = new GregorianCalendar();
                fechaFin.setTimeInMillis(sqlFin.getTime());
                r.setFechaFin(fechaFin);
            }

            r.setPrecioTotal(rs.getDouble("precio_total"));
            r.setEntregado(rs.getBoolean("entregado"));
            r.setNombreAgencia(rs.getString("agencia"));
            // si el SP devuelve lista de autos
            // r.setAutosReservados(rs.getString("automoviles")); 

            reservas.add(r);
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
    return reservas;}
}
