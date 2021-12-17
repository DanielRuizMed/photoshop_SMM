/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.eventos;

import java.awt.Color;
import java.util.EventObject;
import sm.drm.graficos.DFormas;

/**
 * Clase que contienen algunas propiedades a controlar en los eventos del lienzo.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class LienzoEvent extends EventObject{

    private DFormas forma;
    private Color color;

    /**
     * Contructor para establecer un evento e inicializar variables.
     *
     * @param source objeto al que asignar el evento
     * @param forma figura.
     * @param color color de la figura.
     */
    public LienzoEvent(Object source, DFormas forma, Color color) {
        super(source);
        this.forma = forma;
        this.color = color;
    }

    /**
     * Devolverá una forma.
     *
     * @return (tipo: DFormas) figura.
     */
    public DFormas getForma() {
        return forma;
    }

    /**
     * Devuelve el color de nuestra figura.
     *
     * @return (tipo: Color) color de la figura.
     */
    public Color getColor() {
        return color;
    }
}
