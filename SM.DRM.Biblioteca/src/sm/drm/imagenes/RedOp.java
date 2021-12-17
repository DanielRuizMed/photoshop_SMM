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
 * Clase operador para resaltar el color rojo
 *
 * @author: Daniel Ruiz Medina
 * @version: 01/06/2021
 */
public class RedOp extends BufferedImageOpAdapter {

    private int umbral;

    /**
     * Constructor donde definir el umbral hasta donde se desea resaltar.
     * 
     * @param umbral nivel de umbral a aplicar en el operador.
     */
    public RedOp(int umbral) {
        this.umbral = umbral;
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

                pixelCompDest[0] = pixelComp[0];
                pixelCompDest[1] = pixelComp[1];
                pixelCompDest[2] = pixelComp[2];
                
                if( (pixelComp[0] - pixelComp[1] - pixelComp[2]) < umbral ){
                    int media = (pixelComp[0] + pixelComp[1] + pixelComp[2]) / 3;
                    
                    pixelCompDest[0] = media;
                    pixelCompDest[1] = media;
                    pixelCompDest[2] = media;
                    
                }
                
                destRaster.setPixel(x, y, pixelCompDest);
            }
        }
        
        
        
        return dest;
    }
}
