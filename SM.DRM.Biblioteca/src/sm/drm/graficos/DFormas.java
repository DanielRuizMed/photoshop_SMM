/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * Clase padre de nuestras figuras que engloba los principales atributos y
 * métodos.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public abstract class DFormas {

    //Atributos que comparten todas las formas
    private Color colorFondo;
    private Color colorBorde;

    private Composite composicion, composicionInicial;
    private RenderingHints render, renderInicial;
    private boolean compoTrans, renderAlisar, relleno, isdragadado, horizontal;

    private Stroke trazo;
    private float grosor;
    private int tipoTrazo;
    private String nombre;
    private float patronDiscontinuidad[] = {15.0f, 15.0f};

    private Paint degradado;
    Point pc1 = new Point(430, 40), pc2 = new Point(550, 160),pc3,pc4;

    private Shape shape;

    /**
     * Constructor por defecto que inicaliza los atributos necesarios.
     */
    public DFormas() {
        super();
        colorFondo = Color.BLACK;
        colorBorde = Color.BLACK;
        composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        composicionInicial = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
        render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderInicial = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        compoTrans = false;
        renderAlisar = false;
        relleno = false;
        isdragadado = false;
        horizontal = false;

        degradado = new GradientPaint(pc1, colorBorde, pc2, colorFondo);

        grosor = 1.0F;
        tipoTrazo = 0;
        trazo = new BasicStroke(grosor);
    }

    /**
     * Constructor por parámetros donde podemos añadir color a nuestra figura
     * nueva.
     *
     * @param fondo color de relleno de nuestra figura.
     * @param borde color de la frontera de nuestra figura.
     */
    public DFormas(Color fondo, Color borde) {
        super();
        colorFondo = fondo;
        colorBorde = borde;
        composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        compoTrans = false;
        renderAlisar = false;
        relleno = false;
        isdragadado = false;
        horizontal = false;

        degradado = new GradientPaint(pc1, colorBorde, pc2, colorFondo);

        grosor = 1.0F;
        tipoTrazo = 0;
        trazo = new BasicStroke(grosor);
    }

    /**
     * Devuelve el color de fondo de nuestra figura.
     *
     * @return (tipo: Color) color de fondo de la figura.
     */
    public Color getColorFondo() {
        return colorFondo;
    }

    /**
     * Modificar el color de fondo de nuestra figura.
     *
     * @param colorFondo color nuevo a sustituir.
     */
    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
        degradado = new GradientPaint(pc1, colorBorde, pc2, colorFondo);
    }

    /**
     * Devuelve el color de borde de nuestra figura.
     *
     * @return (tipo: Color) color de border de la figura.
     */
    public Color getColorBorde() {
        return colorBorde;
    }

    /**
     * Modificar el color de borde de nuestra figura.
     *
     * @param colorBorde color nuevo a sustituir.
     */
    public void setColorBorde(Color colorBorde) {
        this.colorBorde = colorBorde;
        degradado = new GradientPaint(pc1, colorBorde, pc2, colorFondo);
    }

    /**
     * Devuelve la composición de nuestra figura.
     *
     * @return (tipo: Composite) composición de la figura.
     */
    public Composite getComposicion() {
        return composicion;
    }

    /**
     * Modificar la composición de nuestra figura.
     *
     * @param composicion composición nueva.
     */
    public void setComposicion(Composite composicion) {
        this.composicion = composicion;
    }

    /**
     * Devuelve el render de nuestra figura.
     *
     * @return (tipo: RenderingHints) render de la figura.
     */
    public RenderingHints getRender() {
        return render;
    }

    /**
     * Modificar render de nuestra figura.
     *
     * @param render render nuevo.
     */
    public void setRender(RenderingHints render) {
        this.render = render;
    }

    /**
     * Devuelve si la composición(Transparencia) está activa.
     *
     * @return (tipo: boolean) si está activa o no.
     */
    public boolean isCompoTrans() {
        return compoTrans;
    }

    /**
     * Modificar si deseamos que se aplique la composición(Transparencia).
     *
     * @param compoTrans composición nueva.
     */
    public void setCompoTrans(boolean compoTrans, float valor) {
        this.compoTrans = compoTrans;
        this.composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, valor);
    }

    /**
     * Devuelve si el render está activo.
     *
     * @return (tipo: boolean) si está activo o no.
     */
    public boolean isRenderAlisar() {
        return renderAlisar;
    }

    /**
     * Modificar si deseamos que se aplique el render.
     *
     * @param renderAlisar si está activo o no.
     */
    public void setRenderAlisar(boolean renderAlisar) {
        this.renderAlisar = renderAlisar;
    }

    /**
     * Devuelve el trazo.
     *
     * @return (tipo: Stroke) trazo de la figura.
     */
    public Stroke getTrazo() {
        return trazo;
    }

    /**
     * Modificar el tipo de trazo.
     *
     * @param trazo nuevo trazo.
     */
    public void setTrazo(Stroke trazo) {
        this.trazo = trazo;
    }

    /**
     * Devuelve el grosor del trazo.
     *
     * @return (tipo: float) grosor del trazo de la figura.
     */
    public float getGrosor() {
        return grosor;
    }

    /**
     * Modificar el grosor del trazo. Cada vez que modificamos el grosor
     * recalculamos o creamos el nuevo trazo con el valor pasado
     *
     * @param grosor nuevo grosor.
     */
    public void setGrosor(float grosor) {
        this.grosor = grosor;

        switch (tipoTrazo) {
            case 0:
                trazo = new BasicStroke(grosor);
                break;

            case 1:

                trazo = new BasicStroke(grosor,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_MITER, 1.0f,
                        patronDiscontinuidad, 0.0f);
                break;
        }
    }

    /**
     * Devuelve el tipo de trazo.
     *
     * @return (tipo: int) tipo del trazo de la figura.
     */
    public int getTipoTrazo() {
        return tipoTrazo;
    }

    /**
     * Modificar el tipo de trazo.
     *
     * @param tipoTrazo nuevo tipo de trazo.
     */
    public void setTipoTrazo(int tipoTrazo) {
        this.tipoTrazo = tipoTrazo;

        switch (tipoTrazo) {
            case 0:
                trazo = new BasicStroke(grosor);
                break;

            case 1:

                trazo = new BasicStroke(grosor,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_MITER, 1.0f,
                        patronDiscontinuidad, 0.0f);
                break;
        }
    }

    /**
     * Devuelve el patrón o distancia de la discontinuidad del trazo.
     *
     * @return (tipo: float[]) array con la distancia del trazo discontinuo.
     */
    public float[] getPatronDiscontinuidad() {
        return patronDiscontinuidad;
    }

    /**
     * Modificar el patrón o distancia de la discontinuidad del trazo.
     *
     * @param patronDiscontinuidad patrón de discontinuidad del trazo.
     */
    public void setPatronDiscontinuidad(float[] patronDiscontinuidad) {
        this.patronDiscontinuidad = patronDiscontinuidad;
    }

    /**
     * Devuelve si la figura tiene color de fondo.
     *
     * @return (tipo: boolean) si tiene o no relleno.
     */
    public boolean isRelleno() {
        return relleno;
    }

    /**
     * Modificar si la figura tiene color de fondo.
     *
     * @param relleno si tiene o no relleno.
     */
    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }

    /**
     * Devuelve si la figura tiene el color de fondo degradado activo.
     *
     * @return (tipo: boolean) si tiene o no degradado.
     */
    public boolean isIsdragadado() {
        return isdragadado;
    }

    /**
     * Modificar si la figura tiene el color de fondo degradado activo.
     *
     * @param isdragadado si tiene o no degradado.
     */
    public void setIsdragadado(boolean isdragadado) {
        this.isdragadado = isdragadado;
    }

    /**
     * Devuelve el degradado de la figura.
     *
     * @return (tipo: Paint) degradado de la figura.
     */
    public Paint getDegradado() {
        return degradado;
    }

    /**
     * Modificar el degradado de la figura.
     *
     * @param degradado nuevo degradado.
     */
    public void setDegradado(Paint degradado) {
        this.degradado = degradado;
    }

    /**
     * Devuelve si el tipo de degradado es horizontal
     *
     * @return (tipo: boolean) si es horizontal o no.
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Modificar si el tipo de degradado es horizontal
     *
     * @param horizontal si es horizontal o no..
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Método que pinta la figura contemplando los diferentes atributos de la
     * clase(color de fondo, color de borde, ...).
     *
     * @param g2d varuable graphics sobre la que se dibuja.
     * @param s figura que se desea dibujar.
     */
    public void paint(Graphics2D g2d, DFormas s) {

        if( s != null ){
            if (compoTrans) {
                g2d.setComposite(composicion);
            }
            if (renderAlisar) {
                g2d.setRenderingHints(render);
            }

            g2d.setStroke(trazo);

            //si esta degradado o es plano
            if (relleno) {
                if (isdragadado) {

                    degradado = new GradientPaint(pc1, colorBorde, pc2, colorFondo);
                    g2d.setPaint(degradado);
                } else {
                    g2d.setPaint(colorFondo);
                }

                g2d.fill((Shape) s);
            }

            //color del borde
            g2d.setPaint(colorBorde);

            g2d.draw((Shape) s);
        }
    }

    /**
     * Devuelve la composición que debe tener por defecto.
     *
     * @return (tipo: Composite) composición.
     */
    public Composite getComposicionInicial() {
        return composicionInicial;
    }

    /**
     * Devuelve el render que debe tener por defecto.
     *
     * @return (tipo: RenderingHints) render.
     */
    public RenderingHints getRenderInicial() {
        return renderInicial;
    }

    /**
     * Devuelve el nombre de la figura.
     *
     * @return (tipo: String) nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modificar el nombre de la figura.
     *
     * @param nombre nuevo nombre de la figura.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devolverá un shape de la forma.
     *
     * @return (tipo: Shape) figura.
     */
    public abstract Shape getForma();

}
