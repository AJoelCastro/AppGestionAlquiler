/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import datos.DALReserva;
import entidades.Reserva;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class BLReserva {
   public static int insertarReserva(Reserva r) {
        return DALReserva.insertarReserva(r);
    }

    public static ArrayList<Reserva> listarReservas() {
        return DALReserva.listarReservas();
    }}
