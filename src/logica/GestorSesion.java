/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author ArcosArce
 */

import entidades.Usuario;

/**
 * Singleton para gestionar la sesión del usuario en toda la aplicación
 */
public class GestorSesion {
    
    private static GestorSesion instancia;
    private Usuario usuarioActual;
    private long tiempoInicioSesion;
    
    private GestorSesion() {
        // Constructor privado para patrón Singleton
    }
    
    /**
     * Obtiene la instancia única del gestor de sesión
     * @return Instancia del GestorSesion
     */
    public static synchronized GestorSesion getInstance() {
        if (instancia == null) {
            instancia = new GestorSesion();
        }
        return instancia;
    }
    
    /**
     * Inicia una nueva sesión para el usuario
     * @param usuario Usuario que inicia sesión
     */
    public void iniciarSesion(Usuario usuario) {
        this.usuarioActual = usuario;
        this.tiempoInicioSesion = System.currentTimeMillis();
    }
    
    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        this.usuarioActual = null;
        this.tiempoInicioSesion = 0;
    }
    
    /**
     * Verifica si hay una sesión activa
     * @return true si hay un usuario logueado
     */
    public boolean haySesionActiva() {
        return usuarioActual != null;
    }
    
    /**
     * Obtiene el usuario de la sesión actual
     * @return Usuario actual o null si no hay sesión
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Verifica si el usuario actual tiene permisos de administrador
     * @return true si es administrador
     */
    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.esAdministrador();
    }
    
    /**
     * Verifica si el usuario actual es empleado de agencia
     * @return true si es empleado de agencia
     */
    public boolean esEmpleadoAgencia() {
        return usuarioActual != null && usuarioActual.esEmpleadoAgencia();
    }
    
    /**
     * Obtiene la agencia del usuario actual (solo para empleados)
     * @return ID de la agencia del empleado, 0 si no aplica
     */
    public int getAgenciaUsuario() {
        return usuarioActual != null ? usuarioActual.getAgenciaId() : 0;
    }
    
    /**
     * Obtiene el nombre del usuario actual
     * @return Nombre del usuario o "Desconocido" si no hay sesión
     */
    public String getNombreUsuario() {
        return usuarioActual != null ? usuarioActual.getNombre() : "Desconocido";
    }
    
    /**
     * Obtiene la identificación del usuario actual
     * @return Identificación del usuario o null si no hay sesión
     */
    public String getIdentificacionUsuario() {
        return usuarioActual != null ? usuarioActual.getIdentificacion() : null;
    }
    
    /**
     * Obtiene el rol del usuario actual como texto
     * @return Descripción del rol
     */
    public String getRolUsuario() {
        if (usuarioActual == null) return "Sin sesión";
        
        switch (usuarioActual.getRol()) {
            case ADMINISTRADOR:
                return "Administrador";
            case EMPLEADO_AGENCIA:
                return "Empleado de Agencia";
            default:
                return "Usuario";
        }
    }
    
    /**
     * Calcula el tiempo transcurrido desde el inicio de la sesión
     * @return Tiempo en milisegundos
     */
    public long getTiempoSesion() {
        if (tiempoInicioSesion == 0) return 0;
        return System.currentTimeMillis() - tiempoInicioSesion;
    }
    
    /**
     * Verifica permisos para una operación específica
     * @param operacion Tipo de operación a verificar
     * @return true si tiene permisos
     */
    public boolean tienePermiso(TipoOperacion operacion) {
        if (usuarioActual == null) return false;
        
        switch (operacion) {
            case GESTION_USUARIOS:
            case REPORTES_GLOBALES:
            case CONFIGURACION_SISTEMA:
                return esAdministrador();
                
            case GESTION_RESERVAS:
            case GESTION_CLIENTES:
            case GESTION_AUTOMOVILES:
            case REPORTES_AGENCIA:
                return esAdministrador() || esEmpleadoAgencia();
                
            case CONSULTAS_BASICAS:
                return true; // Todos los usuarios logueados
                
            default:
                return false;
        }
    }
    
    /**
     * Enum para tipos de operaciones del sistema
     */
    public enum TipoOperacion {
        GESTION_USUARIOS,
        GESTION_RESERVAS,
        GESTION_CLIENTES,
        GESTION_AUTOMOVILES,
        REPORTES_GLOBALES,
        REPORTES_AGENCIA,
        CONFIGURACION_SISTEMA,
        CONSULTAS_BASICAS
    }
}