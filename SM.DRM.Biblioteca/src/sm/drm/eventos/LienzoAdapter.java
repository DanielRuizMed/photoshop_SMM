/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.eventos;

/**
 * Clase abstracta para gestión de eventos del lienzo.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public abstract class LienzoAdapter implements LienzoListener{
    
    public String nombre;
    
    /**
     * Para indicar que una nueva figura ha sido añadida.
     *
     * @param evt evento lanzador.
     */
    @Override
    public void shapeAdded(LienzoEvent evt) {
    }

    /**
     * Para indicar que un atributo de una figura ha sido modificada.
     *
     * @param evt evento lanzador.
     */
    @Override
    public void propertyChange(LienzoEvent evt) {
    }

    /**
     * Para indicar que una figura ha sido seleccionada.
     *
     */
    @Override
    public void shapeClicked() {
    }
    
    /**
     * Para modificar el nombre de una figura.
     *
     */
    public void setNombre() {
    }
    
    /**
     * Devolver el nombre de la figura.
     *
     */
    public abstract String getNombre();
}
