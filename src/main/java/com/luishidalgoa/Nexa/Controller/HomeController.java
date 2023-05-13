package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Abstracts.Controller;
import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.DTO.*;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;
import com.luishidalgoa.Nexa.Thread.ThreadUpdatePublications;
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

public class HomeController extends Controller implements Initializable{
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
    @FXML
    private ImageView perfil;
    @FXML
    private ImageView perfil1;
    VBox update;
    //-------------------------------
    PublicationDAO publicationDAO = PublicationDAO.getInstance();
    ThreadUpdatePublications threadUpdatePublications;
    private final static java.util.logging.Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.HomeController");

    public HomeController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Execute.setMainController(this);
        update= (VBox) vBox_publications.getChildren().get(0);
        JavaFXStyleDTO.Rounded(perfil,50);
        JavaFXStyleDTO.Rounded(perfil1,60);
        setData();
    }

    /**
     * Metodo que una vez iniializado la clase Controller. sera utilizada para construir el usuario y otros requisitos
     * elementales para el correcto funcionamiento.
     */
    public void setData(){
        try {
            label_userName.setText(Execute.getUser_logged().getUser_name());
            updatePublicationPanel();
            suggestion_panel();
            UpdateLanguage();

            threadUpdatePublications=new ThreadUpdatePublications(this);
            threadUpdatePublications.start();
        }catch (Exception e){
            logger.log(Level.SEVERE,e.getMessage());
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
        }else{
            logger.log(Level.WARNING,"WARNING. the list RecommendUsers of the function: getRecommendUsers() is empty");
        }
    }
    /**
     * El proposito de este metodo es extraer todos los usuarios de la bbdd
     * Este metodo esta pensado para ser utilizado en el metodo Suggestion_Panel()
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
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo en primera instancia buscara una coleccion de publicaciones para posteriormente iterar la coleccion e
     * inicializar una vista de la misma incluido su controller. Posteriormente insertaremos el nodo dentro del container
     * de nuestro HomeController
     */
    public void updatePublicationPanel() {
        if(((VBox)vBox_publications.getChildren().get(0)).getChildren().get(1).getId().equals(update.getChildren().get(1).getId())){//borramos el elemento update
            vBox_publications.getChildren().remove(0);
        }
        if (vBox_publications.getChildren().size() > 1) {
//limpiaremos toda la vista de una porccion de nuestro vbox_publication. Porque queremos preservar los 2 elementos principales
            vBox_publications.getChildren().subList(1, vBox_publications.getChildren().size()).clear();
        }
        Set<PublicationDTO> publications = getAllPublications();
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

    /**
     * Este metodo extraera el texto insertado dentro del textField_Post de nuestra vista. de modo que construira
     * una publicacion y la guardara en la bbdd . por ultimo ejecutara el metodo @updatePublicationPanel()
     */
    public void post() {
        try {
            if (textField_post.getText().length() > 0 && textField_post.getText().length() <= 255) {
                PublicationDAO.getInstance().save(new Publication(textField_post.getText(), Execute.getUser_logged()));
                updatePublicationPanel();
            } else {
                logger.log(Level.WARNING,"WARNING. The Publication hasn´t been saved. its very short");
                label_message_post.setText("The Publication is very Short");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * El proposito de este metodo es extraer todas las publicaciones y llamar a otro metodo el cual ordenara la lista
     * Este metodo esta pensado para ser utilizado en el metodo updatePublicationPanel()
     * @return dd
     */
    private Set<PublicationDTO> getAllPublications() {
        try {
            Set<PublicationDTO>publications= publicationDAO.findAll();
            publications=Utils.orderByTime(publications);
            return publications;
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Home() {updatePublicationPanel();}

    @Override
    public void Collection() {
        threadUpdatePublications.interrupt();
    }

    @Override
    public void Message() {
        threadUpdatePublications.interrupt();
    }

    public void Perfil(){
        try {
            Execute.setRoot(Execute.loadFXML("Perfil"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        threadUpdatePublications.interrupt();
    }

    /**
     * Actuala los elementos del fxml asignandole el texto en el idioma correspondiente
     */
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            String language= Objects.requireNonNull(User_optionsDAO.get_instance().FindLanguage(Execute.getUser_logged().getUser_name())).getLanguage().name();
            HashMapSerializable<String,String>translated= Translated.get_instance().getTraslated().map.get(language);
            Home_Home.setText(translated.map.get("Home_Home")); //buscamos la clave del objeto traducido en el mapa del idioma
            Home_Collections.setText(translated.map.get("Home_Collections"));
            Home_Message.setText(translated.map.get("Home_Message"));
            Home_Perfil.setText(translated.map.get("Home_Perfil"));
            Home_Suggestions.setText(translated.map.get("Home_Suggestions"));
            btn_post.setText(translated.map.get("Home_Post"));
            Home_ShowMore.setText(translated.map.get("Home_ShowMore"));
            updatePublicationPanel();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo ejecutado desde el hilo ThreadUpdate . Mostrara el boton actualizar en la escena
     */
    public void showUpdate(){
        vBox_publications.getChildren().add(0,update);
    }
    public void optionPanel() throws IOException {
        Execute.newStage("optionsPanel");
    }
}
