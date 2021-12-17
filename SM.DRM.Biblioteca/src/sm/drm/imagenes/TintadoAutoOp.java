/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.drm.imagenes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.TintOp;

/**
 * Clase operador propio de tintado automático con la escala de grises
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class TintadoAutoOp extends TintOp {

    /**
     * Constructor para generar un nuevo tipo de operador.
     * 
     * @param mixColor color a aplicar en el tintado
     * @param mixValue
     */
    public TintadoAutoOp(Color mixColor, float mixValue) {
        super(mixColor, mixValue);
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
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);

                int media = (pixelComp[0] + pixelComp[1] + pixelComp[2]) / 3;
                double umbral = media / 255.0;

                pixelCompDest[0] = (int)( ( umbral * Color.GRAY.getRGB() ) +(1.0-umbral)*pixelComp[0]);
                pixelCompDest[1] = (int)( ( umbral * Color.GRAY.getRGB() ) +(1.0-umbral)*pixelComp[1]);
                pixelCompDest[2] = (int)( ( umbral * Color.GRAY.getRGB() ) +(1.0-umbral)*pixelComp[2]);

                destRaster.setPixel(x, y, pixelCompDest);
                
            }
        }
        
        
        
        return dest;
    }
}
