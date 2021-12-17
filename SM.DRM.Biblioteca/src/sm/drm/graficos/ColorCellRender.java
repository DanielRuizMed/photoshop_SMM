/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.graficos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Clase celda de color, que crea una panel de colores con una lista de 
 * colores pasados.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class ColorCellRender implements ListCellRenderer<Color> {

    /**
     * Método que devuelve un componente panel con una lista de colores que recibe.
     * @param jlist lista con colores a mostrar
     * @param e color de fondo del panel
     * @param i parametro necesario para sobreescribir del padre pero no se usa
     * @param bln parametro necesario para sobreescribir del padre pero no se usa
     * @param bln1 parametro necesario para sobreescribir del padre pero no se usa
     * @return (tipo: Component) panel con colores.
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Color> jlist, Color e, int i, boolean bln, boolean bln1) {
        //JButton b = new JButton();
        //b.setBackground(e);
        //b.setPreferredSize(new Dimension(25,25));
        
        PanelColor panel = new PanelColor(e);
        return panel;
    }
    
}

