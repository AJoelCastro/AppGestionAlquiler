/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.DALReserva;
import entidades.Reserva;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Asus
 */
public class BLReserva {
    public static int insertarReserva(int reservaId, int IdCliente, int IdAgencia, GregorianCalendar fechaI, GregorianCalendar fechaF, double precioT, boolean estado) {
        if (IdCliente > 0 && IdCliente > 0 && IdAgencia > 0
                && fechaI != null && fechaF != null
                && precioT >= 0) {

            Reserva reserva = new Reserva(reservaId, IdCliente, IdAgencia, fechaI, fechaF, precioT, estado);
            int mensaje = DALReserva.insertarReserva(reserva);

            if (mensaje == -1) {
                showMessageDialog(null, "Reserva registrada", "Resultado", 1);
                return 0;
            } else {
                showMessageDialog(null, mensaje, "Error", 0);
                return 1;
            }

        } else {
            showMessageDialog(null, "Datos no válidos", "Error", 0);
            return 3;
        }
    }
    
    public static Reserva buscarReservaPorId(int idReserva){
       ArrayList<Reserva> listaR = DALReserva.listarReservas();
       for(Reserva reserva: listaR){
           if(reserva.getReservaId()==idReserva);
               return DALReserva.buscarReservaPorId(idReserva);
       }
       return null;
    }
    
    public static Reserva buscarReservaPorIdCliente(int idCliente){
       ArrayList<Reserva> listaR = DALReserva.listarReservas();
       for(Reserva reserva: listaR){
           if(reserva.getClienteId()==idCliente);
               return DALReserva.buscarReservaPorIdCliente(idCliente);
       }
       return null;
    }
    
    public static boolean editarReserva(int reservaId, double nuevoPrecioT, boolean cambiarEstado) {
        Reserva reserva = buscarReservaPorId(reservaId);
            if (reserva != null) {
                reserva.setPrecioTotal(nuevoPrecioT);
                reserva.setEntregado(cambiarEstado);
                DALReserva.actualizarReserva(reserva);
                return true; 
            }
    return false; 
    }
    
    public static boolean eliminarReserva(int idReserva) {
        Reserva reserva = buscarReservaPorId(idReserva);
            if (reserva != null) {
                if(reserva.isEntregado()){
                    DALReserva.eliminarReserva(idReserva);
                    return true; }
            }
    return false; 
}

    public static ArrayList<Reserva> listarReservas() {
        return DALReserva.listarReservas();
    }
    
    public static ArrayList<Reserva> listarReservasActivas() {
        return DALReserva.listarReservasActivas();
}
}
