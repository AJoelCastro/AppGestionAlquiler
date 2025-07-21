package datos;

import entidades.ReservaAutomovil;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DALReservaAutomovil {

    public static int insertarReservaAutomovil(ReservaAutomovil ra) {
        Connection cn = null;
        CallableStatement cs = null;
        int resultado = -1;

        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_insertar_reserva_automovil(?, ?, ?, ?)}");
            cs.setInt(1, ra.getReservaId());
            cs.setString(2, ra.getPlaca());
            cs.setDouble(3, ra.getPrecioAlquiler());
            cs.setDouble(4, ra.getLitrosInicial());
            resultado = cs.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error en DALReservaAutomovil", 0);
        } finally {
            try {
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error al cerrar conexión", 0);
            }
        }

        return resultado;
    }

    public static ArrayList<ReservaAutomovil> listarReservaAutomovil() {
        ArrayList<ReservaAutomovil> lista = new ArrayList<>();
        Connection cn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_reserva_automovil()}");
            rs = cs.executeQuery();

            while (rs.next()) {
                ReservaAutomovil ra = new ReservaAutomovil(
                    rs.getInt("reserva_id"),
                    rs.getString("placa"),
                    rs.getDouble("precio_alquiler"),
                    rs.getDouble("litros_inicial")
                );
                lista.add(ra);
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error en listado", 0);
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error cerrando", 0);
            }
        }

        return lista;
    }
    
    public static double obtenerTotalAlquiler(int reservaId) {
        Connection cn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        double total = 0;

        try {
            cn = Conexion.realizarconexion();
            cs = cn.prepareCall("{call sp_listar_reserva_automoviles_por_id(?)}");
            cs.setInt(1, reservaId);

            boolean hayResultados = cs.execute();
            if (hayResultados) {
                cs.getResultSet().close(); // Ignorar primer resultado
                if (cs.getMoreResults()) {
                    rs = cs.getResultSet();
                    if (rs.next()) {
                        total = rs.getDouble("total_alquiler");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error obteniendo total", 0);
        } finally {
            try {
                if (rs != null) rs.close();
                if (cs != null) cs.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error cerrando", 0);
            }
        }

        return total;
    }

}
