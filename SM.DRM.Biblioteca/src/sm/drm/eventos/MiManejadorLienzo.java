/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.eventos;

/**
 * Clsee saber que figuras han sido añadidas al lienzo y comunicarlo a la interfaz.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class MiManejadorLienzo extends LienzoAdapter{
    /*public void shapeAdded(LienzoEvent evt){
        System.out.println("Figura "+evt.getForma()+" añadida");
    }*/
    
    /**
     * Para indicar que una figura ha sido seleccionada.
     *
     */
    public void shapeClicked(){
        System.out.println("Figura 111 añadida");
    }
    
    /**
     * Devuelve el nombre de nuestra figura.
     *
     * @return (tipo: String) nombre de la figura.
     */
    @Override
    public String getNombre(){
        return super.nombre;
    }
    
    /**
     * Para modificar el nombre de una figura.
     *
     * @param nuevo nombre nuevo.
     */
    public void setNombre(String nuevo){
        super.nombre = nuevo;
    }
}

