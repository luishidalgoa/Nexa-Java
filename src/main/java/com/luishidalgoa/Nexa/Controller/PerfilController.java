package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Abstracts.Controller;
import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.DTO.*;
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
import java.util.*;
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
    private final static java.util.logging.Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.PerfilController");

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
        updatePublicationPanel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Execute.setMainController(this);
        JavaFXStyleDTO.Rounded(perfil, 40);
        updatePublicationPanel();
        UpdateLanguage();
        suggestion_panel();
    }

    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            String language = Objects.requireNonNull(User_optionsDAO.get_instance().FindLanguage(Execute.getUser_logged().getUser_name())).getLanguage().name();
            HashMapSerializable<String, String> translated = Translated.get_instance().getTraslated().map.get(language);
            Home_Home.setText(translated.map.get("Home_Home")); //buscamos la clave del objeto traducido en el mapa del idioma
            Home_Collections.setText(translated.map.get("Home_Collections"));
            Home_Message.setText(translated.map.get("Home_Message"));
            Home_Perfil.setText(translated.map.get("Home_Perfil"));
            Home_Suggestions.setText(translated.map.get("Home_Suggestions"));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void updatePublicationPanel() {
        label_userName.setText(Execute.getUser_logged().getUser_name());
        this.textArea_biography.setText(Execute.getUser_logged().getBiography());
        if (vBox_publications.getChildren().size() > 1) {
//limpiaremos toda la vista de una porccion de nuestro vbox_publication. Porque queremos preservar los 2 elementos principales
            vBox_publications.getChildren().subList(1, vBox_publications.getChildren().size()).clear();
        }
        Set<PublicationDTO> publications = orderByDate();
        if (!publications.isEmpty()) {
            for (PublicationDTO aux : publications) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("publication.fxml"));
                try {
                    Node card = fxmlLoader.load();

                    PublicationController publicationController = fxmlLoader.getController();
                    publicationController.setData(Execute.getUser_logged(), aux);
                    vBox_publications.getChildren().add(card);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        } else {
            logger.log(Level.WARNING, "WARNING. the list Publications of the function: getAllPublications() is empty");
        }
    }

    /**
     * Este metodo en primera instancia buscara una coleccion de usuarios para posteriormente iterar la coleccion e
     * inicializar una vista de la misma incluido su controller. Posteriormente insertaremos el nodo dentro del container
     * de nuestro HomeController
     */
    @Override
    public void suggestion_panel() {
        Set<UserDTO> users = getRecommendUsers();
        if (!users.isEmpty()) {
            for (UserDTO aux : users) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userPanel.fxml"));
                try {
                    Node card = fxmlLoader.load();

                    userPanelController userPanelController = fxmlLoader.getController();
                    userPanelController.setData(aux);
                    suggestion_panel.getChildren().add(card);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        } else {
            logger.log(Level.WARNING, "WARNING. the list RecommendUsers of the function: getRecommendUsers() is empty");
        }
    }

    /**
     * El proposito de este metodo es extraer todos los usuarios de la bbdd
     * Este metodo esta pensado para ser utilizado en el metodo Suggestion_Panel()
     *
     * @return dd
     */
    @Override
    public Set<UserDTO> getRecommendUsers() {
        try {
            Set<UserDTO> result = new HashSet<>();
            Set<UserDTO> users = UserDAO.getInstance().findAll();
            Iterator<UserDTO> it = users.iterator();
            int i = 0;
            while (it.hasNext() && i <= 3) {
                UserDTO aux = it.next();
                result.add(aux);
                i++;
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que se ejecutara cuando se actualice la biografia de un usuario
     */
    public void update() {
        String value = textArea_biography.getText();
        try {
            if (textArea_biography.getText().length() > 0 && //Verificamos que las distintas biografias son distintas
                    !UserDAO.getInstance().searchUser(Execute.getUser_logged().getUser_name()).getBiography().equalsIgnoreCase(value)){
                if(UserDAO.getInstance().updateBiography(Execute.getUser_logged().getUser_name(), value)){
                    label_message_biography.setText(null);
                    btn_update.setVisible(false);
                    textArea_biography.setEditable(false);
                    updatePublicationPanel();
                }
            }else{
                label_message_biography.setText("Changes cannot be applied");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBiography() {
        if (!textArea_biography.isEditable()) {
            textArea_biography.setEditable(true);
            btn_update.setVisible(true);
        }
    }

    /**
     * Metodo que ordenara los resultados del PublicationDAO.findBySocial en orden por fecha
     *
     * @return
     */
    private Set<PublicationDTO> orderByDate() {
        try {
            Set<PublicationDTO> publications = PublicationDAO.getInstance().findBySocial(Execute.getUser_logged().getUser_name());
            publications = Utils.orderByTime(publications);
            return publications;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void optionPanel() throws IOException {
        Execute.newStage("optionsPanel");
    }
}
