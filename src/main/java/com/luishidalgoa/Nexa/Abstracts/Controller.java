package com.luishidalgoa.Nexa.Abstracts;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Set;

public abstract class Controller {

    public abstract void Home();
    public abstract void Collection();
    public abstract void Message();
    public abstract void Perfil();
    /**
     * Actuala los elementos del fxml asignandole el texto en el idioma correspondiente
     */
    public abstract void UpdateLanguage();
    /**
     * Este metodo en primera instancia buscara una coleccion de publicaciones para posteriormente iterar la coleccion e
     * inicializar una vista de la misma incluido su controller. Posteriormente insertaremos el nodo dentro del container
     * de nuestro HomeController
     */
    public abstract void updatePublicationPanel();
    /**
     * Este metodo en primera instancia buscara una coleccion de usuarios para posteriormente iterar la coleccion e
     * inicializar una vista de la misma incluido su controller. Posteriormente insertaremos el nodo dentro del container
     * de nuestro HomeController
     */
    public abstract void suggestion_panel();
    /**
     * El proposito de este metodo es extraer todos los usuarios de la bbdd
     * Este metodo esta pensado para ser utilizado en el metodo Suggestion_Panel()
     * @return dd
     */
    public abstract Set<UserDTO> getRecommendUsers();
    /**
     * Cerramos la sesion del usuario para inciar nuevamente la pantalla de login
     */
    public void logOut(){
        Execute.setUser_logged(null); //Por seguridad
        try {
            FXMLLoader fxmlLoader= Execute.loadFXML("Login");
            Execute.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
