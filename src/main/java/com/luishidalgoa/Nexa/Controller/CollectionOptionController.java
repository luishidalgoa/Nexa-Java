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
import java.util.logging.Level;

public class CollectionOptionController implements Initializable {
    @FXML
    private MenuButton MenuButton_collections;
    private Set<CollectionDTO> collections;
    private int id_publication;
    private final static java.util.logging.Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.CollectionOptionController");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.collections = CollectionDAO.getInstance().findByUser(Execute.getUser_logged().getUser_name());
            createItems();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo creara tantos Items dentro del menu desplegable como colecciones tenga el usuario.
     * De este modo permitiremos al usuario guardar sus publicaciones favoritas en colecciones
     */
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

    /**
     * Este metodo lo que hace es crear un evento automaticamente el cual estara atento de si en el item recien creado en el
     * menu desplegable. si se clicka en el pues se agregara la publicacion a la coleccion seleccionada
     * @param name nombre de la coleccion
     */
    public void eventCollection(String name){
        try {
            String username=Execute.getUser_logged().getUser_name();
            if(Publication_CollectionDAO.getInstance().searchByPublication
                    (username,name,id_publication)==null){
                if(!Publication_CollectionDAO.getInstance().add(username,name,id_publication)){
                    logger.log(Level.WARNING,"Could not save collection with name "+name);
                }
            }else{
                if(!Publication_CollectionDAO.getInstance().delete(username,name,id_publication)){
                    logger.log(Level.WARNING,"Could not delete collection with name "+name);
                }
            }
            Execute.getMainController().updatePublicationPanel();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
