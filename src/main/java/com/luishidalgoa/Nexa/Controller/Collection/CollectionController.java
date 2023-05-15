package com.luishidalgoa.Nexa.Controller.Collection;

import com.luishidalgoa.Nexa.Abstracts.Controller;
import com.luishidalgoa.Nexa.Controller.PerfilController;
import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Collection.CollectionDAO;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.DTO.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;

/**
 * Este controller a diferencia del collection controller. sera el encargado de controllar la interaccion entre el usuario
 * y la colleccion de publicaciones del mismo mientras que el CollectionController permite controlar todas las collecciones
 * del usuario
 */
public class CollectionController extends Controller implements Initializable {
    @FXML
    private VBox suggestion_panel;
    @FXML
    private VBox vBox_publications;
    @FXML
    private Label label_userName;
    @FXML
    private Label label_message_biography;
    @FXML
    private TextArea textArea_biography;

    //--------------VISUAL---------
    @FXML
    private Label Collection_Home;
    @FXML
    private Label Collection_Collections;
    @FXML
    private Label Collection_Message;
    @FXML
    private Label Collection_Perfil;
    @FXML
    private Label Collection_Suggestions;
    @FXML
    private ImageView perfil;
    private final static java.util.logging.Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.CollectionController");
    @FXML
    @Override
    public void Home() {
        try {
            FXMLLoader fxmlLoader=Execute.loadFXML("Home");
            Execute.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Collection() {

    }

    @Override
    public void Message() {

    }

    @Override
    public void Perfil() {
        try {
            FXMLLoader fxmlLoader=Execute.loadFXML("Perfil");
            Execute.setRoot(fxmlLoader.load());
            if(fxmlLoader.getController() instanceof PerfilController){
                ((PerfilController)fxmlLoader.getController()).setData(Execute.getUser_logged());
            }else{
                logger.log(Level.WARNING,"WARNING. The controller launch doesn´t a Perfil controller");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            String language = Objects.requireNonNull(Objects.requireNonNull(User_optionsDAO.get_instance().FindLanguage(Execute.getUser_logged().getUser_name())).getLanguage().name());
            HashMapSerializable<String, String> translated = Translated.get_instance().getTraslated().map.get(language);
            Collection_Home.setText(translated.map.get("Home_Home")); //buscamos la clave del objeto traducido en el mapa del idioma
            Collection_Collections.setText(translated.map.get("Home_Collections"));
            Collection_Message.setText(translated.map.get("Home_Message"));
            Collection_Perfil.setText(translated.map.get("Home_Perfil"));
            Collection_Suggestions.setText(translated.map.get("Home_Suggestions"));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePublicationPanel() {
        if (vBox_publications.getChildren().size() > 1) {
//limpiaremos toda la vista de una porccion de nuestro vbox_publication. Porque queremos preservar los 2 elementos principales
            vBox_publications.getChildren().subList(1, vBox_publications.getChildren().size()).clear();
        }
        try {
            Set<CollectionDTO> collection=CollectionDAO.getInstance().findByUser(Execute.getUser_logged().getUser_name());
            if (!collection.isEmpty()) {
                for (CollectionDTO aux : collection) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CollectionCard.fxml"));
                    try {
                        Node card = fxmlLoader.load();

                        CollectionCardController collectionCardController = fxmlLoader.getController();
                        collectionCardController.setData(aux);
                        vBox_publications.getChildren().add(card);
                    } catch (IOException e) {
                        logger.log(Level.SEVERE,e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }else{
                logger.log(Level.WARNING,"WARNING. the list Publications of the function: getAllPublications() is empty");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void suggestion_panel() {

    }

    @Override
    public Set<UserDTO> getRecommendUsers() {
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Execute.setMainController(this);
        JavaFXStyleDTO.Rounded(this.perfil,60);
        updatePublicationPanel();
    }
    public void optionPanel() throws IOException {
        Execute.newStage("optionsPanel");
    }
}

