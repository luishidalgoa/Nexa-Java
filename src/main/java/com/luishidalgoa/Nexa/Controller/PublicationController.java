package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Interfaces.iControllers.iPublicationController;
import com.luishidalgoa.Nexa.Model.DAO.Collection.CollectionDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.LikeDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.ShareDAO;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.DTO.*;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Like;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;
import com.luishidalgoa.Nexa.Model.Enum.Language;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class PublicationController implements Initializable, iPublicationController {

    @FXML
    private Label Label_Date;
    @FXML
    private Label Label_LikeCount;
    @FXML
    private Label Label_ShareCount;
    @FXML
    private TextArea TextArea_Text;
    @FXML
    private Label label_UserName;
    @FXML
    MenuButton Menu_options;
    @FXML
    private Button update;
    private PublicationDTO p=new PublicationDTO();
    @FXML
    private ImageView perfil;
    private final static java.util.logging.Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.PublicationController");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFXStyleDTO.Rounded(perfil, 40);
    }
    /**
     * Este metodo se encarga de actualizar todo lo referente a la card
     */
    @Override
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            Language language= Objects.requireNonNull(User_optionsDAO.get_instance().FindLanguage(Execute.getUser_logged().getUser_name())).getLanguage();
            HashMapSerializable<String, String> aux=Translated.get_instance().getTraslated().map.get(language.name());
            Menu_options.setText(aux.map.get("Publication_Menu_options"));//buscamos la clave del objeto traducido en el mapa del idioma
            update.setText(aux.map.get("Publication_Update"));
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Seteara los datos recividos desde HomeController 'UpdatePublications()' a despues de haber buscado todas las publicaciones
     * De modo que construira la card para posteriormente en UpdatePublication() cargarlo
     * @param u usuario de la publicacion
     * @param p Contenido de la publicacion
     */
    @Override
    public void setData(UserDTO u, PublicationDTO p){
        try {
            if(u!=null && p!=null){
                this.p=p;
                UpdateLanguage();
                this.Label_Date.setText(p.getBeetwenDate());
                this.Label_LikeCount.setText(String.valueOf(LikeDAO.get_instance().findById(p.getPublication().getId()).size()));
                this.Label_ShareCount.setText(String.valueOf(Objects.requireNonNull(ShareDAO.get_instance().findById(p.getPublication().getId())).size()));
                this.TextArea_Text.setText(p.getPublication().getText());
                this.label_UserName.setText(p.getPublication().getUser().getUser_name());
            }else{
                logger.log(Level.WARNING,"was not going to can set data on function 'setData()' because UserDTO or PublicationDTO its null");
            }
            optionsAvalaible();
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * Metodo intermediario encargado de validar si existe o no el like. A partir de existir o no ejecutara
     * llamara al dao para agregar un nuevo share o borrarlo
     */
    @Override
    public void Liked(){
        try {
            if(LikeDAO.get_instance().findLike(p.getPublication().getId(),Execute.getUser_logged().getUser_name())!=null){
                LikeDAO.get_instance().delete(p.getPublication().getId(),Execute.getUser_logged().getUser_name());
                Label_LikeCount.setText(String.valueOf(Integer.parseInt(Label_LikeCount.getText())-1));
            }else {
                LikeDAO.get_instance().save(new Like(p.getPublication(),Execute.getUser_logged()));
                Label_LikeCount.setText(String.valueOf(Integer.parseInt(Label_LikeCount.getText())+1));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo intermediario encargado de validar si existe o no el share. A partir de existir o no ejecutara
     * llamara al dao para agregar un nuevo share o borrarlo
     */
    @Override
    public void shared(){
        try {
            if(ShareDAO.get_instance().findShare(p.getPublication().getId(),Execute.getUser_logged().getUser_name())!=null){
                ShareDAO.get_instance().delete(p.getPublication().getId(),Execute.getUser_logged().getUser_name());
                Label_ShareCount.setText(String.valueOf(Integer.parseInt(Label_ShareCount.getText())-1));
            }else {
                ShareDAO.get_instance().save(new Share(p.getPublication(),Execute.getUser_logged()));
                Label_ShareCount.setText(String.valueOf(Integer.parseInt(Label_ShareCount.getText())+1));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo ejecutara un submenu para agregar la publicacion dentro de una lista o eliminarlo de una
     */
    public void Collected(){
        try {
            FXMLLoader fxmlLoader= Execute.loadFXML("collectionOption");
            Parent p= fxmlLoader.load();
            ((CollectionOptionController)fxmlLoader.getController()).setData(this.p.getPublication().getId());
            Execute.newStage(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Metodo que habilitara el boton de opciones de la publicaciones en caso de que se valide que la publicacion
     * le pertenece al usuario
     */
    @Override
    public void optionsAvalaible(){
        if(!p.publication.getUser().getUser_name().equals(Execute.getUser_logged().getUser_name())){
            this.Menu_options.setVisible(false);
        }
    }

    /**
     * Metodo que ejecutara el proceso de borrado de una publicacion. Sera el intermediario entre el controller y el DAO
     */
    @Override
    public void deleted(){
        try {
            if(PublicationDAO.getInstance().delete(this.p.getPublication().getId())){
                Execute.getMainController().updatePublicationPanel(); //ACTUALIZAR CUANDO CREE EL PERFIL USUARIO
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void updateOpt(){
        TextArea_Text.setEditable(true);
        update.setVisible(true);
    }

    /**
     * Este  metodo sera ejecutado cuando se confirmen los cambios en la vista de la publicacion. de modo que se
     * actualizara la publicacion
     */
    public void update(){
        try {
            PublicationDAO.getInstance().update(p,TextArea_Text.getText());
            Execute.getMainController().updatePublicationPanel(); //ACTUALIZAR CUANDO CREE EL PERFIL USUARIO
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /**
     * Cambiamosa la pantalla del perfil usuario
     */
    public void showPerfil(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Execute.class.getResource("Controller/Perfil.fxml"));
            Parent parent= fxmlLoader.load();

            ((PerfilController)fxmlLoader.getController()).setData(p.getPublication().getUser());//Le seteamos el usuario de la publicacion
            Execute.setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
