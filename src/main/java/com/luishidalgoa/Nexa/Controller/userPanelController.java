package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Interfaces.iControllers.iuserPanelController;
import com.luishidalgoa.Nexa.Model.DTO.JavaFXStyleDTO;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFXStyleDTO.Rounded(perfil,60);
    }
}
