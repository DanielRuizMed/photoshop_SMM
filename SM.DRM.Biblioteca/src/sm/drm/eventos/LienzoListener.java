/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.eventos;

import java.util.EventListener;

/**
 * Interfaz para gestión de eventos.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public interface LienzoListener extends EventListener {

    /**
     * Para indicar que una nueva figura ha sido añadida.
     *
     * @param evt evento lanzador.
     */
    public void shapeAdded(LienzoEvent evt);
    
    /**
     * Para indicar que una figura ha sido seleccionada.
     *
     */
    public void shapeClicked();

    /**
     * Para indicar que un atributo de una figura ha sido modificada.
     *
     * @param evt evento lanzador.
     */
    public void propertyChange(LienzoEvent evt);
    
    /**
     * Para modificar el nombre de una figura.
     *
     */
    public void setNombre(String nuevo);
}
    
