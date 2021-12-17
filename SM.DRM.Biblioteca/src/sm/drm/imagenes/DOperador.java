/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.imagenes;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Clase operador propio donde añadimos un valor a cada banda aclarando 
 * dicha imagen.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class DOperador extends BufferedImageOpAdapter{
    private int parametro;

    /**
     * Constructor donde definimos el parametro a sumar.
     * 
     * @param parametro valor se desea sumar sumar.
     */
    public DOperador(int parametro) {
        this.parametro = parametro;
    }

    /**
     * Método para aplicar un filtro a una imagen origen y se devuelve 
     * en una destino.
     * 
     * @param src imagen a la que aplicar el filtro
     * @param dest imagen donde se desea aplicar el filtro
     * 
     * @return (tipo: BufferedImage) imagen con el filtro aplicado.
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) { //si la imagen destino es null se crea
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);

                pixelCompDest[0] = (pixelComp[1]+pixelComp[2])/parametro % 255;
                pixelCompDest[1] = (pixelComp[0]+pixelComp[2])/parametro % 255;
                pixelCompDest[2] = (pixelComp[1]+pixelComp[0])/parametro % 255;
                
                destRaster.setPixel(x, y, pixelCompDest);
            }
        }
        return dest;
    }
}
