/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import java.sql.*;
/**
 *
 * @author Asus
 */
public class Conexion {
    public static Connection realizarconexion()throws ClassNotFoundException, SQLException{
        String url,user,password;
        Class.forName("com.mysql.cj.jdbc.Driver");
        url="jdbc:mysql://localhost:3306/alquiler_automoviles";
        user="root";
        password="7389209350";
        
        System.out.println("Intentando conectar a: " + url);
        System.out.println("Usuario: " + user);
        System.out.println("Contraseña: " + password);
        return DriverManager.getConnection(url, user, password);
    }
}
