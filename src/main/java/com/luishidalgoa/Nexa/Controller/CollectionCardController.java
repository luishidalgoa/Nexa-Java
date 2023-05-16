package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Collection.CollectionDAO;
import com.luishidalgoa.Nexa.Model.DTO.CollectionDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class CollectionCardController implements Initializable {
    @FXML
    private Label label_collectionName;
    @FXML
    private Label label_publicationsNumber;
    private CollectionDTO collectionDTO;
    private final static java.util.logging.Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.CollectionCardController");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Setearemos los datos de la coleccion como su nombre o el numero de publicaciones que tiene agregadas
     * @param collection
     */
    public void setData(CollectionDTO collection){
        this.label_collectionName.setText(collection.getCollection().getName());
        this.label_publicationsNumber.setText(String.valueOf(collection.getCollectionPublication().size()));
        this.collectionDTO=collection;
    }

    /**
     * Metodo que elimina la coleccion del usuario
     */
    public void delete(){
        try {
            if(!CollectionDAO.getInstance().delete(Execute.getUser_logged().getUser_name(),label_collectionName.getText())){
                logger.log(Level.WARNING,"Could not delete collection with name "+label_collectionName.getText());
            }
            Execute.getMainController().updatePublicationPanel();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * Muestra la escena de la coleccion. es decir la vista en la que se ven
     * las publicaciones agregadas
     */
    public void showCollection(){
        try {
            FXMLLoader fxmlLoader= Execute.loadFXML("CollectionPublications");
            Execute.setRoot(fxmlLoader.load());
            Execute.setMainController(fxmlLoader.getController());
            ((CollectionPublicationController)Execute.getMainController()).setData(collectionDTO);
        } catch (IOException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
