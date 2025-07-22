/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import entidades.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.*;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import logica.FacadeAlquiler;

public class ComprobantePDF {

    // Método estático que coincide con el llamado en ifrmEntregaAutomovil
    public static boolean generarComprobante(Reserva reserva, Cliente cliente, ArrayList<ReservaAutomovil> autosReserva, FacadeAlquiler facade) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("comprobante_alquiler_" + reserva.getReservaId() + ".pdf"));
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            // ENCABEZADO
            Paragraph title = new Paragraph("AUTORENT S.A.C.", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10f);
            document.add(title);

            Paragraph subtitle = new Paragraph("Comprobante de Alquiler de Vehículo", headerFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20f);
            document.add(subtitle);

            // DATOS DE LA EMPRESA
            Paragraph empresa = new Paragraph();
            empresa.add(new Chunk("RUC: 20123456789\n", smallFont));
            empresa.add(new Chunk("Av. Las Industrias 123, Lima\n", smallFont));
            empresa.add(new Chunk("Tel: (01) 123-4567\n", smallFont));
            empresa.setAlignment(Element.ALIGN_RIGHT);
            empresa.setSpacingAfter(20f);
            document.add(empresa);

            // INFORMACIÓN DE LA RESERVA
            Paragraph infoReserva = new Paragraph();
            infoReserva.add(new Chunk("Número de Reserva: " + reserva.getReservaId() + "\n", headerFont));
            infoReserva.add(new Chunk("Fecha de emisión: " + new java.util.Date().toString() + "\n", normalFont));
            infoReserva.setSpacingAfter(15f);
            document.add(infoReserva);

            // DATOS DEL CLIENTE
            Paragraph clienteHeader = new Paragraph("Datos del Cliente:", headerFont);
            clienteHeader.setSpacingAfter(5f);
            document.add(clienteHeader);

            Paragraph datosCliente = new Paragraph();
            datosCliente.add(new Chunk("Nombre: " + cliente.getNombre() + "\n", normalFont));
            datosCliente.add(new Chunk("DNI: " + cliente.getDni() + "\n", normalFont));
            datosCliente.add(new Chunk("Dirección: " + cliente.getDireccion() + "\n", normalFont));
            datosCliente.add(new Chunk("Teléfono: " + cliente.getTelefono() + "\n", normalFont));
            datosCliente.setSpacingAfter(20f);
            document.add(datosCliente);

            // TABLA DE VEHÍCULOS
            Paragraph vehiculosHeader = new Paragraph("Vehículos Alquilados:", headerFont);
            vehiculosHeader.setSpacingAfter(10f);
            document.add(vehiculosHeader);

            // Crear tabla
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingAfter(20f);
            
            // Headers de la tabla
            table.addCell(new PdfPCell(new Phrase("Placa", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Marca/Modelo", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Color", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Litros Inicial", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Precio", headerFont)));

            double totalPrecio = 0.0;

            for (ReservaAutomovil ra : autosReserva) {
                table.addCell(new PdfPCell(new Phrase(ra.getPlaca(), normalFont)));
                
                // Obtener detalles del automóvil usando el facade
                Automovil automovil = facade.buscarAutomovilPorPlaca(ra.getPlaca());
                if (automovil != null) {
                    String marcaModelo = automovil.getMarca() + " " + automovil.getModelo();
                    table.addCell(new PdfPCell(new Phrase(marcaModelo, normalFont)));
                    table.addCell(new PdfPCell(new Phrase(automovil.getColor(), normalFont)));
                } else {
                    table.addCell(new PdfPCell(new Phrase("N/A", normalFont)));
                    table.addCell(new PdfPCell(new Phrase("N/A", normalFont)));
                }
                
                table.addCell(new PdfPCell(new Phrase(String.format("%.2f L", ra.getLitrosInicial()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.format("$%.2f", ra.getPrecioAlquiler()), normalFont)));
                
                totalPrecio += ra.getPrecioAlquiler();
            }

            document.add(table);

            // TOTAL
            Paragraph total = new Paragraph("TOTAL A PAGAR: $" + String.format("%.2f", totalPrecio), headerFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingAfter(30f);
            document.add(total);

            // OBSERVACIONES
            Paragraph observaciones = new Paragraph();
            observaciones.add(new Chunk("OBSERVACIONES:\n", headerFont));
            observaciones.add(new Chunk("• Este comprobante no tiene valor tributario.\n", smallFont));
            observaciones.add(new Chunk("• El cliente debe devolver el vehículo con el mismo nivel de combustible.\n", smallFont));
            observaciones.add(new Chunk("• Cualquier daño será evaluado y cobrado por separado.\n", smallFont));
            observaciones.add(new Chunk("• Gracias por confiar en AUTORENT S.A.C.\n", smallFont));
            document.add(observaciones);

            document.close();
            return true;

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método de instancia original (mantenido por compatibilidad)
    public void generarComprobantePDF(Cliente c, Automovil a, GregorianCalendar fi, GregorianCalendar ff, ReservaAutomovil ra) {
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("comprobante_alquiler.pdf"));
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // ENCABEZADO
            Paragraph title = new Paragraph("AUTORENT S.A.C.", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            Paragraph subtitle = new Paragraph("Comprobante de Alquiler de Vehículo", headerFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20f);
            document.add(subtitle);

            // DATOS DEL CLIENTE
            Paragraph clienteInfo = new Paragraph();
            clienteInfo.add(new Chunk("Datos del Cliente:\n", headerFont));
            clienteInfo.add(new Chunk("Nombre: " + c.getNombre() + "\n", normalFont));
            clienteInfo.add(new Chunk("DNI: " + c.getDni() + "\n", normalFont));
            clienteInfo.add(new Chunk("Dirección: " + c.getDireccion() + "\n", normalFont));
            clienteInfo.add(new Chunk("Teléfono: " + c.getTelefono() + "\n", normalFont));
            clienteInfo.setSpacingAfter(20f);
            document.add(clienteInfo);

            // DATOS DEL VEHÍCULO
            Paragraph vehiculoInfo = new Paragraph();
            vehiculoInfo.add(new Chunk("Datos del Alquiler:\n", headerFont));
            vehiculoInfo.add(new Chunk("Vehículo: " + a.getMarca() + " " + a.getModelo() + " - " + a.getPlaca() + "\n", normalFont));
            vehiculoInfo.add(new Chunk("Fecha inicio: " + fi.getTime().toString() + "\n", normalFont));
            vehiculoInfo.add(new Chunk("Fecha fin: " + ff.getTime().toString() + "\n", normalFont));
            vehiculoInfo.add(new Chunk("Total a pagar: $" + String.format("%.2f", ra.getPrecioAlquiler()) + "\n", normalFont));
            vehiculoInfo.setSpacingAfter(20f);
            document.add(vehiculoInfo);

            // OBSERVACIONES
            Paragraph observaciones = new Paragraph();
            observaciones.add(new Chunk("Este comprobante no tiene valor tributario.\n", normalFont));
            observaciones.add(new Chunk("Gracias por confiar en AUTORENT S.A.C.", normalFont));
            document.add(observaciones);

            document.close();
            JOptionPane.showMessageDialog(null, "Comprobante generado correctamente como 'comprobante_alquiler.pdf'");

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar comprobante: " + e.getMessage());
        }
    }
}