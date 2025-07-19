/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 *
 * @author sanar
 */
public class BotonPersonalizado extends JButton{
    private Color color;
    private Color presionado;
    private Color encima;
    

    public BotonPersonalizado(String text, Color color, Color presionado, Color encima) {
        super(text);
        this.color = color;
        this.presionado = presionado;
        this.encima = encima;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }
    public BotonPersonalizado(String text, Color color) {
        super(text);
        this.color = color;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        this.presionado = color;
        this.encima = color;   
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(presionado);  // Cambia a verde cuando se presiona el botón
        } else if (getModel().isRollover()) {
            g2.setColor(encima);  // Cambia a rojo cuando el mouse pasa sobre el botón
        } else {
            g2.setColor(color);  // Color original cuando no se interactúa
        }

        // Dibuja el botón con bordes redondeados
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();
        
        super.paintComponent(g);
    }
}
