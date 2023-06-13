package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashSet;

public class FollowPanelController {
    @FXML
    private AnchorPane FollowersPanel;
    @FXML
    private VBox PerfilContainer;
    private HashSet<UserDTO> follows;
    public void close(){
        FollowersPanel.getChildren();
        Parent parentNode=FollowersPanel.getParent();
        parentNode.layout();
        ((AnchorPane)parentNode).getChildren().remove(FollowersPanel);
    }
    public void setData(HashSet<UserDTO> follows){
        this.follows=follows;
        showUsers();
    }
    public void Perfil() {
        try {
            FXMLLoader fxmlLoader= Execute.loadFXML("Perfil");
            Execute.setRoot(fxmlLoader.load());
            if(fxmlLoader.getController() instanceof PerfilController){
                ((PerfilController)fxmlLoader.getController()).setData(Execute.getUser_logged());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showUsers() {
        PerfilContainer.getChildren().clear();
        if (!follows.isEmpty()) {
            for (UserDTO aux : follows) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userPanel.fxml"));
                try {
                    Node card = fxmlLoader.load();

                    userPanelController userPanelController = fxmlLoader.getController();
                    userPanelController.setData(aux);
                    PerfilContainer.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
