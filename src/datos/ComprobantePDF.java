/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import entidades.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.GregorianCalendar;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ComprobantePDF {

    public void generarComprobantePDF(Cliente c, Automovil a,GregorianCalendar fi, GregorianCalendar ff,ReservaAutomovil ra) {
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("comprobante_alquiler.pdf"));
            document.open();

            PdfContentByte cb = writer.getDirectContent();
            Graphics2D g = cb.createGraphicsShapes(PageSize.A4.getWidth(), PageSize.A4.getHeight());

            // ---------------- ENCABEZADO ----------------
            g.setColor(new Color(60, 120, 180));
            g.fillRect(0, 0, 600, 80);

            g.setColor(Color.WHITE);
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 24));
            g.drawString("AUTORENT S.A.C.", 40, 50);

            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
            g.drawString("RUC: 20123456789", 400, 30);
            g.drawString("Av. Las Industrias 123, Lima", 400, 45);
            g.drawString("Tel: (01) 123-4567", 400, 60);

            // ---------------- TÍTULO ----------------
            g.setColor(Color.BLACK);
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 20));
            g.drawString("Comprobante de Alquiler de Vehículo", 150, 120);

            // ---------------- DATOS DEL CLIENTE ----------------
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
            g.drawString("Datos del Cliente:", 40, 160);
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
            g.drawString("Nombre:" + c.getNombre(), 40, 180);
            g.drawString("DNI:" + c.getDni(), 40, 180);
            g.drawString("Direccion:" + c.getDireccion(), 40, 180);
            g.drawString("Teléfono:" + c.getTelefono(), 40, 180);

            // ---------------- DATOS DEL VEHÍCULO ----------------
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
            g.drawString("Datos del Alquiler:", 40, 240);
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
            g.drawString("Vehículo:" + a.getMarca() + a.getModelo() + " - " + a.getPlaca(), 40, 260);
            g.drawString("Fecha inicio: " + fi, 40, 275);
            g.drawString("Fecha fin: " + ff, 40, 290);

            // ---------------- TABLA DE COSTOS ----------------
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
            g.drawString("Detalle de Costos:", 40, 340);
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));

            g.drawRect(40, 350, 500, 90); // tabla

            g.drawString("Concepto", 50, 365);
            g.drawLine(40, 430, 540, 430);
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
            g.drawString("Total a pagar:", 50, 445);
            g.drawLine(40, 430, 540, 430);
            g.drawString(String.valueOf(ra.getPrecioAlquiler()), 420, 445);

            // ---------------- OBSERVACIONES ----------------
            g.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
            g.setColor(Color.DARK_GRAY);
            g.drawString("Este comprobante no tiene valor tributario.", 40, 490);
            g.drawString("Gracias por confiar en AUTORENT S.A.C.", 40, 505);

            // ---------------- CIERRE ----------------
            g.dispose();
            document.close();

            JOptionPane.showMessageDialog(null, "Comprobante generado correctamente como 'comprobante_alquiler.pdf'");

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar comprobante: " + e.getMessage());
        }
    }
}
