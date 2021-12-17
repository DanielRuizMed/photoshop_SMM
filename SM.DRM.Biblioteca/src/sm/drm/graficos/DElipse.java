/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.graficos;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Clase para crear elipses que hereda de la clase DFormas.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class DElipse extends DFormas{
    
    private Ellipse2D.Double forma;
    
    /**
     * Constructor por defecto que inicaliza los atributos necesarios y crea 
     * la figura.
     */
    public DElipse(){
        super();
        forma = new Ellipse2D.Double();
        super.setNombre("Elipse");
    }

    /**
     * Modificar si la figura tiene el color de fondo degradado activo y crea 
     * el nuevo degradado.
     *
     * @param isdragadado si tiene o no degradado.
     */
    public void setIsdragadado(boolean isdragadado) {
        
        super.setIsdragadado(isdragadado);
        
        pc1 = new Point((int)forma.getMinX(), (int)forma.getMinY());
        pc2 = new Point((int)forma.getMaxX(), (int)forma.getMaxY());
        super.setDegradado(new GradientPaint(pc1, super.getColorBorde(), pc2, super.getColorFondo()));
    }
    
    /**
     * Método que pinta la elipse contemplando los diferentes atributos de la
     * clase(color de fondo, color de borde, ...).
     *
     * @param g2d varuable graphics sobre la que se dibuja.
     * @param s figura.
     */
    @Override
    public void paint(Graphics2D g2d, DFormas s) {
        
        if( s != null ){
        
            pc1 = new Point( (int)forma.getMinX() + ( ( (int)forma.getMaxX() - (int)forma.getMinX() ) /2), 
                                (int)forma.getMinY());
            pc2 = new Point((int)forma.getMinX() + ( ( (int)forma.getMaxX() - (int)forma.getMinX() ) /2), 
                    (int)forma.getMaxY());

            pc3 = new Point((int)forma.getMinX(), 
                                (int)forma.getMinY() + ( ( (int)forma.getMaxY() - (int)forma.getMinY() ) /2));
            pc4 = new Point((int)forma.getMaxX(), 
                    (int)forma.getMinY() + ( ( (int)forma.getMaxY() - (int)forma.getMinY() ) /2)); 
        
            g2d.setComposite(super.getComposicionInicial());
            if(super.isCompoTrans()){
                g2d.setComposite(super.getComposicion());
            }
            
            g2d.setRenderingHints(super.getRenderInicial());
            if(super.isRenderAlisar()){
                g2d.setRenderingHints(super.getRender());
            }

            g2d.setStroke(super.getTrazo());

            //si esta degradado o es plano
            if( super.isRelleno() ){
                if( super.isIsdragadado() ){

                    //segun la direccion de degradado
                    if( isHorizontal() ){
                        super.setDegradado(new GradientPaint(pc1, super.getColorBorde(), pc2, super.getColorFondo()));
                    }else{
                        super.setDegradado(new GradientPaint(pc3, super.getColorBorde(), pc4, super.getColorFondo()));
                    }
                    
                    g2d.setPaint(super.getDegradado());
                }else{
                    g2d.setPaint(super.getColorFondo());
                }

                g2d.fill(forma);
            }

            //color del borde
            g2d.setPaint(super.getColorBorde());

            g2d.draw(forma);
        }
    }

    /**
     * Devolverá un shape de la forma.
     *
     * @return (tipo: Shape) figura.
     */
    @Override
    public Shape getForma() {
        return forma;
    }
}
