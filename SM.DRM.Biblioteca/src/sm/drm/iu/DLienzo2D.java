/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.iu;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import sm.drm.eventos.LienzoEvent;
import sm.drm.eventos.LienzoListener;
import sm.drm.graficos.DElipse;
import sm.drm.graficos.DFormas;
import sm.drm.graficos.DLinea;
import sm.drm.graficos.DRectangulo;
import sm.drm.graficos.DRectanguloRounds;

/**
 * Clase lienzo, donde se mostrarán o modificarán figuras imágenes, videos y
 * demás.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class DLienzo2D extends javax.swing.JPanel {

    //constantes
    public static final int PUNTO = 0;
    public static final int LINEA = 1;
    public static final int RECTANGULO = 2;
    public static final int CIRCULO = 3;
    public static final int RECTANGULOROUNDS = 4;
    public static final int TRAZOLIBRE = 5;

    //atributos
    private DFormas figuraSeleccionada;
    private static int forma;
    private Point2D p, pAux;
    private Color color;
    private boolean ventanaClipActiva, volcado;
    private static boolean mover;
    private DFormas shape;

    ArrayList<DFormas> vShape = new ArrayList();
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();

    //Imagen
    BufferedImage imagen = null;
    private Ellipse2D clipArea = new Ellipse2D.Float(0, 0, 200, 200);
    private Rectangle2D clipAreaRect = new Rectangle2D.Double(0, 0, 200, 200);

    /**
     * Constructor por defecto que inicaliza los atributos necesarios y
     * componentes.
     */
    public DLienzo2D() {
        initComponents();
        color = Color.BLACK;
        mover = false;
        forma = LINEA;
        pAux = new Point(0, 0);
        ventanaClipActiva = false;
        imagen = null;
        figuraSeleccionada = null;
    }

    /**
     * Método que dibuja la zona de dibujo, marco y por cada figura llama al
     * método que la pinta.
     *
     * @param g componente grafica utilizada para dibujar.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //Pinta imagen inicial blanca, a modo de lienzo
        DrawImagenNull(g2d);

        for (DFormas s : vShape) {
            s.paint(g2d, s);
        }
    }

    /**
     * Método que dibuja la zona de dibujo y marco. La imagen de dibujo en
     * blanco la añade si no hay ya una imagen(ejemplo abrir imagen, se toma
     * está como fondo). También se controla la zona de visión.
     *
     * @param g2d componente grafica utilizada para dibujar.
     */
    private void DrawImagenNull(Graphics2D g2d) {

        if (imagen == null) {
            imagen = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        }

        //Marco e imagen blanca
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle2D.Float(0, 0, imagen.getWidth(), imagen.getHeight()));
        g2d.drawImage(imagen, 0, 0, this);
        g2d.setPaint(Color.BLACK);
        g2d.draw(new Rectangle2D.Float(0, 0, imagen.getWidth(), imagen.getHeight()));

        //zona limitada
        clipAreaRect = new Rectangle2D.Double(0, 0, imagen.getWidth(), imagen.getHeight());
        g2d.setClip(clipAreaRect);

        //Circulo de visión
        if (ventanaClipActiva) {
            g2d.setClip(clipArea);
        }
    }

    /**
     * Método que crea la figura indicada en el atributo forma.
     *
     * @param punto1 punto incial para crear la figura.
     * @param punto2 punto final para crear la figura.
     * @return (tipo: DFormas) forma creada.
     */
    public DFormas createShape(Point2D punto1, Point2D punto2) {

        //vemos si los puntos y la forma son distintos de null o por defecto Punto
        if (punto1 == null || (punto2 == null && forma != PUNTO)) {
            return null;
        }

        switch (forma) {//almacenamos en shape la creación de dicha forma
            /*case PUNTO:
                //shape = new Line2D.Double(punto1, punto1);
                shape = new Line2D.Double(punto1, punto1);
                figuraSeleccionada = shape;
                this.repaint();
                break;*/
            case LINEA:
                shape = new DLinea(punto1, punto1);
                figuraSeleccionada = shape;
                break;
            case RECTANGULO:
                shape = new DRectangulo();
                figuraSeleccionada = shape;
                ((RectangularShape) shape.getForma()).setFrameFromDiagonal(punto1, punto1);
                break;
            case RECTANGULOROUNDS:
                shape = new DRectanguloRounds();
                figuraSeleccionada = shape;
                ((RectangularShape) shape.getForma()).setFrameFromDiagonal(punto1, punto1);
                break;
            case TRAZOLIBRE:
                shape = new DRectangulo();
                figuraSeleccionada = shape;
                ((RectangularShape) shape.getForma()).setFrameFromDiagonal(punto1, punto1);
                break;
            case CIRCULO:
                shape = new DElipse();
                figuraSeleccionada = shape;
                ((Ellipse2D) shape.getForma()).setFrameFromDiagonal(punto1, punto1);
                break;
            default:
                shape = null;
                break;
        }

        return shape;
    }

    /**
     * Método para actualizar la longitud de las diferentes figuras.
     *
     * @param punto1 punto incial para la nueva figura.
     * @param punto2 punto final para la nueva figura.
     */
    private void updateShape(Point2D punto1, Point2D punto2) {

        if (shape.getForma() instanceof Line2D) {
            ((Line2D) shape.getForma()).setLine(punto1, punto2);
        } else if (shape.getForma() instanceof RectangularShape) {
            ((RectangularShape) shape.getForma()).setFrameFromDiagonal(punto1, punto2);
        } else if (shape.getForma() instanceof Ellipse2D) {
            ((Ellipse2D) shape.getForma()).setFrameFromDiagonal(punto1, punto2);
        }

    }

    /**
     * Método al que pasado un punto nos dice si hay una fingura en dicho punto.
     *
     * @param p punto donde deseamos buscar.
     * @return (tipo: DFormas) figura encontrada.
     */
    private DFormas getSelectedShape(Point2D p) {

        for (DFormas s : vShape) {//recorremos el vector
            if (contains((DFormas) s, p)) {//y vemos si el punto pasado esta cerca del algun shape contenido en la lista
                return s;
            }
        }
        return null;
    }

    /**
     * Método al que pasado un punto y una figura nos dice si dicha figura tiene
     * ese punto.
     *
     * @param sh figura a comparar.
     * @param p punto donde deseamos buscar.
     * @return (tipo: boolean) si conincide o no.
     */
    public boolean contains(DFormas sh, Point2D p) {

        if (sh instanceof DLinea) {//si es linea utilizamos metodo creado por nosotros, al no disponer de el linea
            return isNear((Line2D) sh.getForma(), p);
        } else {
            return sh.getForma().contains(p);
        }
    }

    /**
     * Método para mover o cambiar la localización de una figura.
     *
     * @param s figura a mover.
     * @param p nuevo punto.
     */
    public void setLocation(DFormas s, Point2D p) {

        if (s != null) {

            if (s instanceof DLinea) {//si es linea calculamos la diferencia entre el punto shape y el pasado

                double difx = p.getX() - ((Line2D) s.getForma()).getX1();
                double dify = p.getY() - ((Line2D) s.getForma()).getY1();

                //cremos el punto nuevo mas la diferencia anterior calculada
                Point2D newP = new Point2D.Double(((Line2D) s.getForma()).getX2() + difx, ((Line2D) s.getForma()).getY2() + dify);
                ((Line2D) s.getForma()).setLine(p, newP);

            } else if (s.getForma() instanceof RectangularShape) {//si es un rectangulo

                RectangularShape r = (RectangularShape) s.getForma();
                r.setFrame(p, new Dimension((int) r.getWidth(), (int) r.getHeight()));

            } else if (s.getForma() instanceof Ellipse2D) {//si es una elipse

                Ellipse2D r = (Ellipse2D) s.getForma();
                r.setFrame(p, new Dimension((int) r.getWidth(), (int) r.getHeight()));
            }
        }

    }

    /**
     * Método para ver si un punto está cerca o coincide con la posición de una
     * línea.
     *
     * @param line linea donde comparar.
     * @param p punto.
     * @return (tipo: boolean) si conincide o no.
     */
    private boolean isNear(Line2D line, Point2D p) {

        /*if (line.getP1().equals(line.getP2())) {//si es un punto
            return line.getP1().distance(p) <= 3.0;
        } else {*/
        return line.ptLineDist(p) <= 1.0;
        //}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(204, 204, 204));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que se lanza cuando el ratón es presionado y que dependiendo si la
     * opción mover está seleccionada crea o mueve una figura.
     *
     * @param evt evento lanzador.
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        p = evt.getPoint();
        if (!mover) {
            vShape.add(0, createShape(p, p));
            notifyShapeAddedEvent(new LienzoEvent(this, shape, color));
            figuraSeleccionada = vShape.get(0);
        } else {
            shape = getSelectedShape(evt.getPoint());
            if (shape != null) {
                double x = (shape.getForma() instanceof Line2D) ? ((Line2D) shape.getForma()).getX1() : shape.getForma().getBounds2D().getX();
                double y = (shape.getForma() instanceof Line2D) ? ((Line2D) shape.getForma()).getY1() : shape.getForma().getBounds2D().getY();
                pAux.setLocation(x - p.getX(), y - p.getY());
            }
            figuraSeleccionada = shape;

        }

        notifyPropertyChangeEvent();
    }//GEN-LAST:event_formMousePressed

    /**
     * Método que se lanza cuando el ratón es arrastrado(mienstras se hace
     * click) y que dependiendo si la opción mover, actualiza la posición de una
     * figura o indica el punto final de la figura nueva.
     *
     * @param evt evento lanzador.
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        Point localp = evt.getPoint();

        if (!mover) {

            if (forma != DLienzo2D.PUNTO) {
                updateShape(p, evt.getPoint());
            }
        } else {

            setLocation(shape, new Point2D.Double(localp.getX() + pAux.getX(), localp.getY() + pAux.getY()));
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Método que se lanza cuando el ratón es movido(sin hacer click) para
     * mediante una zona de recorte ver donde movemos con el ratón.
     *
     * @param evt evento lanzador.
     */
    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (ventanaClipActiva) {
            Point corner = evt.getPoint();
            corner.translate((int) clipArea.getWidth() / 2,
                    (int) clipArea.getHeight() / 2);
            clipArea.setFrameFromCenter(evt.getPoint(), corner);
            this.repaint();
        }
    }//GEN-LAST:event_formMouseMoved

    /**
     * Devuelce la imagen del lienzo
     *
     * @return (tipo: BufferedImage) imagen.
     */
    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * Devuelve la imagen del lienzo, dependiendo del parámetro genera una
     * copia.
     *
     * @param drawVector si se desea copiar o coger la original.
     * @return (tipo: BufferedImage) imagen.
     */
    public BufferedImage getImagen(boolean drawVector) {
        if (drawVector) {
            BufferedImage imgout = new BufferedImage(imagen.getWidth(), imagen.getHeight(), imagen.getType());
            boolean opacoActual = this.isOpaque();
            if (imagen.getColorModel().hasAlpha()) {
                this.setOpaque(false);
            }
            this.paint(imgout.createGraphics());
            this.setOpaque(opacoActual);
            return imgout;
        }

        return imagen;
    }

    /**
     * Modifica la imagen del lienzo y lo redimensiona.
     *
     * @param imagen imagen nueva.
     */
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;

        //Poner el lienzo al tamaño de la imagen y si es mayor pone el scroll
        if (imagen != null) {
            setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
        }
    }

    /**
     * Método para indicar al lienzo que se desean grabar todas las figuras en
     * la imagen.
     *
     */
    public void VolcarImagen() {

        BufferedImage imgout = new BufferedImage(imagen.getWidth(), imagen.getHeight(), imagen.getType());
        boolean opacoActual = this.isOpaque();
        if (imagen.getColorModel().hasAlpha()) {
            this.setOpaque(false);
        }
        this.paint(imgout.createGraphics());
        this.setOpaque(opacoActual);
        this.vShape = new ArrayList();

        imagen = imgout;
        this.repaint();
    }

    /**
     * Devuelve la forma.
     *
     * @return (tipo: int) forma.
     */
    public static int getForma() {
        return DLienzo2D.forma;
    }

    /**
     * Modifica la forma.
     *
     * @param forma nueva forma.
     */
    public static void setForma(int forma) {
        DLienzo2D.forma = forma;
    }

    /**
     * Modifica el relleno de la figura seleccionada.
     *
     * @param relleno si hay relleno o no.
     */
    public void setRelleno(boolean relleno) {

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setRelleno(relleno);
        }
    }

    /**
     * Modifica el degradado de la figura seleccionada.
     *
     * @param degradado si hay degradado o no.
     */
    public void setDegradado(boolean degradado) {

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setIsdragadado(degradado);
        }
    }

    /**
     * Modifica la direccion del degradado de la figura seleccionada.
     *
     * @param dir sverdadero si es horizontal, si no vertical.
     */
    public void setDireccion(boolean dir) {

        if (figuraSeleccionada != null) {

            figuraSeleccionada.setHorizontal(dir);
        }
    }

    /**
     * Devuelve si esta marcada la opción mover figura.
     *
     * @return (tipo: boolean) si eesta marcada la opción.
     */
    public static boolean isMover() {
        return DLienzo2D.mover;
    }

    /**
     * Modifica la opción mover figura.
     *
     * @param mover si se desea mover o no las figuras.
     */
    public static void setMover(boolean mover) {
        DLienzo2D.mover = mover;
    }

    /**
     * Modifica el grososr del trazo.
     *
     * @param valor nuevo valor de grosor.
     */
    public void setStrokeFloat(int valor) {
        /*this.valorStroke = valor;*/

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setGrosor(valor);
        }
    }

    /**
     * Devuelve color seleccionado en el lienzo.
     *
     * @return (tipo: Color) último color elegido por los colores
     * predeterminados o paleta.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Modifica el color del lienzo y color de relleno la figura seleccionada
     *
     * @param color nuevo color.
     */
    public void setColorFondo(Color color) {

        this.color = color;

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setColorFondo(color);
        }
    }

    /**
     * Modifica el color del lienzo y color de borde la figura seleccionada
     *
     * @param color nuevo color.
     */
    public void setColorBorde(Color color) {

        this.color = color;

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setColorBorde(color);
        }
    }

    /**
     * Modifica el alisado del lienzo y en la figura seleccionada
     *
     * @param alisado nuevo alisado.
     */
    public void setAlisado(boolean alisado) {

        /*this.alisado = alisado;*/

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setRenderAlisar(alisado);
        }
    }

    /**
     * Modifica la transparencia del lienzo y en la figura seleccionada
     *
     * @param transparente nuevo valor transparente.
     */
    public void setTransparente(boolean transparente, float valor) {

        /*this.transparente = transparente;*/

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setCompoTrans(transparente, valor);
        }
    }

    /**
     * Modifica si se desea ver por la zona de recorte.
     *
     * @param ventanaClipActiva si se desea o no.
     */
    public void setVentanaClipActiva(boolean ventanaClipActiva) {
        this.ventanaClipActiva = ventanaClipActiva;
    }

    /**
     * Devuelve si la ventana de visión por recorte está activa.
     *
     * @return (tipo: boolean) activa o no.
     */
    public boolean isVentanaClipActiva() {
        return ventanaClipActiva;
    }

    /**
     * Modifica el tipo de trazo de la figura seleccionada.
     *
     * @param tipo nuevo tipo.
     */
    public void setTipoTrazo(int tipo) {

        if (figuraSeleccionada != null) {
            figuraSeleccionada.setTipoTrazo(tipo);
        }

        this.repaint();
    }

    /**
     * Devuelve la figura seleccionada(última creada o clickada).
     *
     * @return (tipo: DFormas) figura.
     */
    public DFormas getFiguraSeleccionada() {
        return figuraSeleccionada;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * Metodo que añade una escucha cuando se crea el lienzo.
     *
     * @param listener evento lanzador.
     */
    public void addLienzoListener(LienzoListener listener) {
        if (listener != null) {
            lienzoEventListeners.add(listener);
        }
    }

    /**
     * Metodo que notifica cuando una nueva figura se a añadido al lienzo.
     *
     * @param evt evento lanzador.
     */
    private void notifyShapeAddedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeAdded(evt);
            }
        }
    }

    /**
     * Metodo que notifica cuando se modifica un atributo de una figura del
     * lienzo.
     *
     */
    private void notifyPropertyChangeEvent() {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeClicked();

                if (figuraSeleccionada != null) {
                    listener.setNombre(figuraSeleccionada.getNombre());
                }

            }
        }
    }

}
