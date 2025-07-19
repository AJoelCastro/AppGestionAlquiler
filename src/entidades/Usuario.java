/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * Entidad que representa un usuario del sistema
 * @author ArcosArce
 */

public class Usuario {
    private int usuarioId;
    private String identificacion;
    private String nombre;
    private String contrasena;
    private TipoRol rol;
    private boolean activo;
    private int agenciaId; // Solo para empleados de agencia
    
    // Enum para los roles
    public enum TipoRol {
        ADMINISTRADOR("ADMINISTRADOR"),
        EMPLEADO_AGENCIA("EMPLEADO_AGENCIA");
        
        private final String valor;
        
        TipoRol(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
        
        public static TipoRol fromString(String valor) {
            for (TipoRol rol : TipoRol.values()) {
                if (rol.valor.equals(valor)) {
                    return rol;
                }
            }
            throw new IllegalArgumentException("Rol no válido: " + valor);
        }
    }
    
    // Constructores
    public Usuario() {}
    
    public Usuario(String identificacion, String nombre, String contrasena, TipoRol rol) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }
    
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public TipoRol getRol() {
        return rol;
    }
    
    public void setRol(TipoRol rol) {
        this.rol = rol;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public int getAgenciaId() {
        return agenciaId;
    }
    
    public void setAgenciaId(int agenciaId) {
        this.agenciaId = agenciaId;
    }
    
    // Métodos de utilidad
    public boolean esAdministrador() {
        return this.rol == TipoRol.ADMINISTRADOR;
    }
    
    public boolean esEmpleadoAgencia() {
        return this.rol == TipoRol.EMPLEADO_AGENCIA;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol=" + rol +
                ", activo=" + activo +
                ", agenciaId=" + agenciaId +
                '}';
    }
}