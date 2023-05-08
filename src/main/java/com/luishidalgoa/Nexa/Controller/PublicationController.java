package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Interfaces.iControllers.iPublicationController;
import com.luishidalgoa.Nexa.Model.DAO.Publications.LikeDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.ShareDAO;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.DTO.Translated;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Like;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;
import com.luishidalgoa.Nexa.Model.Enum.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;

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
    private PublicationDTO p=new PublicationDTO();
    private UserDTO user;
    private final static java.util.logging.Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.PublicationController");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Check Publication controller");
    }

    /**
     * Este metodo se encarga de actualizar todo lo referente a la card
     */
    @Override
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            Language language= Objects.requireNonNull(User_optionsDAO.get_instance().FindLanguage(user.getUser_name())).getLanguage();
            Menu_options.setText(Translated.get_instance().getTraslated().map.get(language.name())
                    .map.get("Publication_Menu_options"));//buscamos la clave del objeto traducido en el mapa del idioma
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
                this.user=u;
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
            if(LikeDAO.get_instance().findLike(p.getPublication().getId(),user.getUser_name())!=null){
                LikeDAO.get_instance().delete(p.getPublication().getId(),user.getUser_name());
                Label_LikeCount.setText(String.valueOf(Integer.parseInt(Label_LikeCount.getText())-1));
            }else {
                LikeDAO.get_instance().save(new Like(p.getPublication(),user));
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
            if(ShareDAO.get_instance().findShare(p.getPublication().getId(),user.getUser_name())!=null){
                ShareDAO.get_instance().delete(p.getPublication().getId(),user.getUser_name());
                Label_ShareCount.setText(String.valueOf(Integer.parseInt(Label_ShareCount.getText())-1));
            }else {
                ShareDAO.get_instance().save(new Share(p.getPublication(),user));
                Label_ShareCount.setText(String.valueOf(Integer.parseInt(Label_ShareCount.getText())+1));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que habilitara el boton de opciones de la publicaciones en caso de que se valide que la publicacion
     * le pertenece al usuario
     */
    @Override
    public void optionsAvalaible(){
        if(!p.publication.getUser().getUser_name().equals(user.getUser_name())){
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
                Execute.mainController.updatePublicationPanel();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
