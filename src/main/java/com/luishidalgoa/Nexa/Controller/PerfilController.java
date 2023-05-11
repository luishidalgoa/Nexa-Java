package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Abstracts.Controller;
import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.DTO.HashMapSerializable;
import com.luishidalgoa.Nexa.Model.DTO.JavaFXStyleDTO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.DTO.Translated;
import com.luishidalgoa.Nexa.Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class PerfilController extends Controller implements Initializable {
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
    private Label Home_Home;
    @FXML
    private Label Home_Collections;
    @FXML
    private Label Home_Message;
    @FXML
    private Label Home_Perfil;
    @FXML
    private Button btn_update;
    @FXML
    private Label Home_Suggestions;
    @FXML
    private ImageView perfil;
    PublicationDAO publicationDAO = PublicationDAO.getInstance();
    private final static java.util.logging.Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.PerfilController");
    @Override
    public void Home() {
        try {
            Execute.setRoot(Execute.loadFXML("Home"));
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Execute.setMainController(this);
        JavaFXStyleDTO.Rounded(perfil,40);
        updatePublicationPanel();
    }
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            String language= Objects.requireNonNull(User_optionsDAO.get_instance().FindLanguage(Execute.getUser_logged().getUser_name())).getLanguage().name();
            HashMapSerializable<String,String> translated= Translated.get_instance().getTraslated().map.get(language);
            Home_Home.setText(translated.map.get("Home_Home")); //buscamos la clave del objeto traducido en el mapa del idioma
            Home_Collections.setText(translated.map.get("Home_Collections"));
            Home_Message.setText(translated.map.get("Home_Message"));
            Home_Perfil.setText(translated.map.get("Home_Perfil"));
            Home_Suggestions.setText(translated.map.get("Home_Suggestions"));
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void update(){

    }

    public void updatePublicationPanel() {
        if (vBox_publications.getChildren().size() > 1) {
//limpiaremos toda la vista de una porccion de nuestro vbox_publication. Porque queremos preservar los 2 elementos principales
            vBox_publications.getChildren().subList(1, vBox_publications.getChildren().size()).clear();
        }
        Set<PublicationDTO> publications = getUserPublications();
        if (!publications.isEmpty()) {
            for (PublicationDTO aux : publications) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("publication.fxml"));
                try {
                    Node card = fxmlLoader.load();

                    PublicationController publicationController = fxmlLoader.getController();
                    publicationController.setData(Execute.getUser_logged(), aux);
                    vBox_publications.getChildren().add(card);
                } catch (IOException e) {
                    logger.log(Level.SEVERE,e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }else{
            logger.log(Level.WARNING,"WARNING. the list Publications of the function: getAllPublications() is empty");
        }
    }
    private Set<PublicationDTO> getUserPublications(){
        try {
            Set<PublicationDTO>publications= publicationDAO.findAll();
            publications= Utils.orderByTime(publications);
            return publications;
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void optionPanel() throws IOException {
        Execute.newStage("optionsPanel");
    }
}
