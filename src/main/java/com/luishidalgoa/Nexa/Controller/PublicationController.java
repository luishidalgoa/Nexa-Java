package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.Publications.LikeDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.ShareDAO;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
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
import java.util.ResourceBundle;

public class PublicationController implements Initializable {

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Check Publication controller");
    }
    public void UpdateLanguage() {
        try {
            //Buscamos el idioma en la configuracion del usuario
            Language language=User_optionsDAO.get_instance().FindLanguage(user.getUser_name()).getLanguage();
            Menu_options.setText(Translated.get_instance().getTraslated().map.get(language.name())
                    .map.get("Publication_Menu_options"));//buscamos la clave del objeto traducido en el mapa del idioma
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setData(UserDTO u, PublicationDTO p){
        try {
            this.p=p;
            this.user=u;
            UpdateLanguage();
            this.Label_Date.setText(p.getBeetwenDate());
            this.Label_LikeCount.setText(String.valueOf(LikeDAO.get_instance().findById(p.getPublication().getId()).size()));
            this.Label_ShareCount.setText(String.valueOf(ShareDAO.get_instance().findById(p.getPublication().getId()).size()));
            this.TextArea_Text.setText(p.getPublication().getText());
            this.label_UserName.setText(p.getPublication().getUser().getUser_name());
            optionsAvalaible();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
            throw new RuntimeException(e);
        }
    }
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
            throw new RuntimeException(e);
        }
    }
    public void optionsAvalaible(){
        if(!p.publication.getUser().getUser_name().equals(user.getUser_name())){
            this.Menu_options.setVisible(false);
        }
    }
    public void deleted(){
        try {
            if(PublicationDAO.getInstance().delete(this.p.getPublication().getId())){
                Execute.mainController.updatePublicationPanel();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
