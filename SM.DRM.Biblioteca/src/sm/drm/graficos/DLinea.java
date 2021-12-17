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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Clase para crear lineas que hereda de la clase DFormas.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class DLinea extends DFormas{
    
    private Line2D.Double forma;
    
    /**
     * Constructor para crear una línea pasando los puntos de inicio y fin.
     * @param p1 punto del principio de la línea.
     * @param p2 punto del final de la línea.
     */
    public DLinea(Point2D p1,Point2D p2){
        super();
        forma = new Line2D.Double(p1,p2);
        super.setNombre("Línea");
    }

    /**
     * Modificar si la figura tiene el color de fondo degradado activo y crea 
     * el nuevo degradado.
     *
     * @param isdragadado si tiene o no degradado.
     */
    public void setIsdragadado(boolean isdragadado) {
        
        super.setIsdragadado(isdragadado);
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
                    
                    super.setDegradado(new GradientPaint(pc1, super.getColorBorde(), pc2, super.getColorFondo()));
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
