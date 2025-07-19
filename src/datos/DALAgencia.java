/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.*;
import java.util.*;
import java.sql.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Asus
 */
public class DALAgencia {
   private static Connection cn = null;
    private static CallableStatement cs = null;
    private static Statement st = null;
    private static ResultSet rs = null;

    public static String insertarAgencia(Agencia agencia) {
        String mensaje = null;
        try {
            cn = Conexion.realizarconexion();
            String sql = "{call sp_insertar_agencia(?, ?)}";
            cs = cn.prepareCall(sql);
            cs.setString(1, agencia.getNombre());
            cs.setString(2, agencia.getDireccion());
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
    
public static Agencia buscarAgenciaPorId(int idAgencia) {
    Agencia agencia = null;
    try {
        cn = Conexion.realizarconexion();
        String sql = "{call sp_buscar_agencia_por_id(?)}";
        cs = cn.prepareCall(sql);
        cs.setInt(1, idAgencia);
        rs = cs.executeQuery();

        if (rs.next()) {
            agencia = new Agencia();
            agencia.setAgenciaId(idAgencia);
            agencia.setNombre(rs.getString("nombre"));
            agencia.setDireccion(rs.getString("direccion"));
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
    return agencia;
}

    public static ArrayList<Agencia> listarAgencias() {
        ArrayList<Agencia> lista = new ArrayList<>();
        try {
            cn = Conexion.realizarconexion();
            st = cn.createStatement();
            String sql = "CALL sp_listar_agencias()";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Agencia ag = new Agencia();
                ag.setAgenciaId(rs.getInt("agencia_id"));
                ag.setNombre(rs.getString("nombre"));
                ag.setDireccion(rs.getString("direccion"));
                lista.add(ag);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            showMessageDialog(null, ex.getMessage(), "Error", 0);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                showMessageDialog(null, ex.getMessage(), "Error", 0);
            }
        }
        return lista;
    }
}
