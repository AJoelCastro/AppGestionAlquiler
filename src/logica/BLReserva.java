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
        if (IdCliente > 0 && IdAgencia > 0
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
    
    

    public static ArrayList<Reserva> listarReservas() {
        return DALReserva.listarReservas();
    }}
