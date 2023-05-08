package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Interfaces.iControllers.iuserPanelController;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class userPanelController implements iuserPanelController {
    @FXML
    private Label label_userName;
    private UserDTO user;

    /**
     * Este metodo traducira la vista de SuggestionPanel
     * @param u
     */
    @Override
    public void setData(UserDTO u){
        this.label_userName.setText(u.getUser_name());
    }
}
