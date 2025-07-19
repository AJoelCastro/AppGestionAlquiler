/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author ArcosArce
 */

import entidades.Usuario;
import entidades.Usuario.TipoRol;
import java.sql.*;

/**
 * DAO para la gestión de usuarios en la base de datos
 */
public class DALUsuario {
    
    /**
     * Autentica un usuario en el sistema
     * @param identificacion Identificación del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario autenticado o null si no existe
     */
    public Usuario autenticarUsuario(String identificacion, String contrasena) {
        Usuario usuario = null;

        // CAMBIAR: Usar consulta directa en lugar del procedimiento almacenado
        String sql = "SELECT usuario_id, identificacion, nombre, rol, agencia_id, activo " +
                    "FROM usuarios WHERE identificacion = ? AND contrasena = ? AND activo = TRUE";

        try (Connection conn = Conexion.realizarconexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Intentando conectar a BD...");
            stmt.setString(1, identificacion);
            stmt.setString(2, contrasena);

            System.out.println("Ejecutando consulta para: " + identificacion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Usuario encontrado: " + rs.getString("nombre"));
                    usuario = new Usuario();
                    usuario.setUsuarioId(rs.getInt("usuario_id"));
                    usuario.setIdentificacion(rs.getString("identificacion"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setContrasena(contrasena);
                    usuario.setRol(TipoRol.fromString(rs.getString("rol")));
                    usuario.setAgenciaId(rs.getInt("agencia_id"));
                    usuario.setActivo(rs.getBoolean("activo"));
                } else {
                    System.out.println("No se encontró usuario con esas credenciales");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error detallado: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }
    
    /**
     * Busca un usuario por su identificación
     * @param identificacion Identificación del usuario
     * @return Usuario encontrado o null
     */
    public Usuario buscarPorIdentificacion(String identificacion) {
        Usuario usuario = null;
        String sql = "SELECT usuario_id, identificacion, nombre, rol, agencia_id, activo " +
                    "FROM usuarios WHERE identificacion = ? AND activo = TRUE";
        
        try (Connection conn = Conexion.realizarconexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setUsuarioId(rs.getInt("usuario_id"));
                    usuario.setIdentificacion(rs.getString("identificacion"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRol(TipoRol.fromString(rs.getString("rol")));
                    usuario.setAgenciaId(rs.getInt("agencia_id"));
                    usuario.setActivo(rs.getBoolean("activo"));
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        
        return usuario;
    }
    
    /**
     * Verifica si existe un usuario con la identificación dada
     * @param identificacion Identificación a verificar
     * @return true si existe, false si no
     */
    public boolean existeUsuario(String identificacion) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE identificacion = ?";
        
        try (Connection conn = Conexion.realizarconexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al verificar existencia del usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Inserta un nuevo usuario en la base de datos
     * @param usuario Usuario a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (identificacion, nombre, contrasena, rol, agencia_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.realizarconexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getIdentificacion());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getRol().getValor());
            if (usuario.getAgenciaId() > 0) {
                stmt.setInt(5, usuario.getAgenciaId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }
}
