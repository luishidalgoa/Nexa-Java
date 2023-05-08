package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class userPanelController {
    @FXML
    private Label label_userName;
    private UserDTO user;
    public void setData(UserDTO u){
        this.label_userName.setText(u.getUser_name());
    }
}
