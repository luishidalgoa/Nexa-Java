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
    public abstract void UpdateLanguage();
    public abstract void updatePublicationPanel();
    public abstract void suggestion_panel();
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
