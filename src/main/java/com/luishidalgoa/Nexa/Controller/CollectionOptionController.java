package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Collection.CollectionDAO;
import com.luishidalgoa.Nexa.Model.DAO.Collection.Publication_CollectionDAO;
import com.luishidalgoa.Nexa.Model.DTO.CollectionDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

public class CollectionOptionController implements Initializable {
    @FXML
    private MenuButton MenuButton_collections;
    private Set<CollectionDTO> collections;
    private int id_publication;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.collections = CollectionDAO.getInstance().findByUser(Execute.getUser_logged().getUser_name());
            createItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createItems() {
        for (CollectionDTO aux : collections) {
            MenuItem menuItem = new MenuItem(aux.getCollection().getName());
            menuItem.setOnAction(new EventHandler<ActionEvent>() { // Agregamos un evento que permitira agregar o borrar en una coleccion una publicacion
                @Override
                public void handle(ActionEvent event) {
                    eventCollection(aux.getCollection().getName());
                }
            });
            MenuButton_collections.getItems().add(menuItem);
        }
    }
    public void setData(int id_publication){
        this.id_publication=id_publication;
    }
    public void eventCollection(String name){
        try {
            String username=Execute.getUser_logged().getUser_name();
            if(Publication_CollectionDAO.getInstance().searchByPublication
                    (username,name,id_publication)==null){
                if(!Publication_CollectionDAO.getInstance().add(username,name,id_publication)){
                    //LOGGER
                }
            }else{
                if(!Publication_CollectionDAO.getInstance().delete(username,name,id_publication)){
                    //LOGGER
                }
            }
            Execute.getMainController().updatePublicationPanel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
