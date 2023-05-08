package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.*;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DTO.*;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;
import com.luishidalgoa.Nexa.Model.Enum.Language;
import com.luishidalgoa.Nexa.Thread.ThreadUpdatePublications;
import com.luishidalgoa.Nexa.Utils.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class HomeController implements Initializable {
    @FXML
    private TextArea textField_post;
    @FXML
    private Label label_message_post;
    @FXML
    private VBox suggestion_panel;
    @FXML
    private VBox vBox_publications;
    @FXML
    private Label label_userName;

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
    private Button btn_post;
    @FXML
    private Button Home_ShowMore;
    @FXML
    private Label Home_Suggestions;
    //-------------------------------
    private UserDTO user_logged;
    PublicationDAO publicationDAO = PublicationDAO.getInstance();

    public HomeController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Check Home controller");
        user_logged= Login.Sing_in("Luis","1234");
        label_userName.setText(user_logged.getUser().getUser_name());
        updatePublicationPanel();
        suggestion_panel();
        UpdateLanguage();

        ThreadUpdatePublications threadUpdatePublications=new ThreadUpdatePublications(this);
        threadUpdatePublications.start();
    }

    public void suggestion_panel() {
        Set<UserDTO> users = getRecommendUsers();
        if (users != null) {
            for (UserDTO aux : users) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userPanel.fxml"));
                try {
                    Node card = fxmlLoader.load();

                    userPanelController userPanelController = fxmlLoader.getController();
                    userPanelController.setData(aux);
                    suggestion_panel.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Metodo encargado de mostrar por pantalla una coleccion de publicacion
     */
    public void updatePublicationPanel() {
        for (int i = vBox_publications.getChildren().size() - 1; i >= 2; i--) {
            vBox_publications.getChildren().remove(i);
        }
        Set<PublicationDTO> publications = getAllPublications();
        if (publications != null) {
            for (PublicationDTO aux : publications) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("publication.fxml"));
                try {
                    Node card = fxmlLoader.load();

                    PublicationController publicationController = fxmlLoader.getController();
                    publicationController.setData(user_logged, aux);
                    vBox_publications.getChildren().add(card);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void post() {
        try {
            if (textField_post.getText().length() > 0 && textField_post.getText().length() <= 255) {
                PublicationDAO.getInstance().save(new Publication(textField_post.getText(), user_logged));
                Execute.mainController.updatePublicationPanel();
            } else {
                label_message_post.setText("Error. The Publication hasn´t been saved.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<PublicationDTO> getAllPublications() {
        try {
            Set<PublicationDTO>publications= publicationDAO.findAll();
            publications=Utils.orderByTime(publications);
            return publications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<UserDTO> getRecommendUsers() {
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
            throw new RuntimeException(e);
        }
    }
    public void switchPerfil(){

    }

    /**
     * Actuala los elementos del fxml asignandole el texto en el idioma correspondiente
     * @throws SQLException
     */
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            Language language=User_optionsDAO.get_instance().FindLanguage(user_logged.getUser().getUser_name()).getLanguage();
            Home_Home.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_Home")); //buscamos la clave del objeto traducido en el mapa del idioma
            Home_Collections.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_Collections"));
            Home_Message.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_Message"));
            Home_Perfil.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_Perfil"));
            Home_Suggestions.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_Suggestions"));
            btn_post.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_Post"));
            Home_ShowMore.setText(Translated.get_instance().getTraslated().map.get(language.name()).map.get("Home_ShowMore"));
            updatePublicationPanel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void optionPanel() throws IOException {
        Execute.newStage("optionsPanel");
    }
    public UserDTO getUser_logged() {
        return user_logged;
    }

    public void setUser_logged(UserDTO user_logged) {
        this.user_logged = user_logged;
    }
}
