package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Interfaces.iControllers.iuserPanelController;
import com.luishidalgoa.Nexa.Model.DTO.JavaFXStyleDTO;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userPanelController implements iuserPanelController, Initializable {
    @FXML
    private Label label_userName;
    @FXML
    private ImageView perfil;
    private UserDTO user;

    /**
     * Este metodo traducira la vista de SuggestionPanel
     * @param u
     */
    @Override
    public void setData(UserDTO u){
        this.label_userName.setText(u.getUser_name());
        this.user=u;
    }
    public void perfil(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Execute.class.getResource("Controller/Perfil.fxml"));
            Parent parent= fxmlLoader.load();

            ((PerfilController)fxmlLoader.getController()).setData(user);//Le seteamos el usuario de la publicacion
            Execute.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFXStyleDTO.Rounded(perfil,60);
    }
}
