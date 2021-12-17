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
 * Clase operador para aplicar el operador posterizar sobre una imagen.
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class PosterizarOp extends BufferedImageOpAdapter{
    private int niveles;

    /**
     * Constructor donde definimos el nivel con el que se desea posterizar.
     * 
     * @param niveles los niveles para posterizar.
     */
    public PosterizarOp(int niveles) {
        this.niveles = niveles;
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
        int sample;

        float k = 256.f/niveles;
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);
                    
                    sample = (int)(k * (int)(sample/k));

                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }
}
