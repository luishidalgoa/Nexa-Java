package com.luishidalgoa.Nexa.Model.DTO;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class JavaFXStyleDTO{
    /**
     * Devuelve una imagen con un recorte a la medida preseleccionada
     * @param image imagen que vamos a asignarle el corte
     * @param size tamaño del corte / redondeo
     * @return imagen redondeada
     */
    public static ImageView Rounded(ImageView image,int size){
        Rectangle clip = new Rectangle(image.getFitWidth(), image.getFitHeight());
        clip.setArcWidth(size);
        clip.setArcHeight(size);
        image.setClip(clip);
        return image;
    }
}
