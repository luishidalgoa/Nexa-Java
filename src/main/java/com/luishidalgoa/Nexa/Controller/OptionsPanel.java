package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Interfaces.iControllers.iOptionsPanel;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.Enum.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class OptionsPanel implements Initializable, iOptionsPanel {
    private final static java.util.logging.Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Controller.OptionsPanel");

    /**
     * Metodo que se ejecutara cuando se detecte que se presione un boton de la vista.
     * Este metodo actualizara en la tabla user_options la configuracion de lenguaje a Ingles
     *
     * @param event dd
     */
    @Override
    @FXML
    public void handleEN(ActionEvent event) {
        try {
            User_optionsDAO.get_instance().updateLanguage(Execute.getUser_logged().getUser_name(), Language.EN.name());
            Execute.getMainController().UpdateLanguage();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que se ejecutara cuando se detecte que se presione un boton de la vista.
     * Este metodo actualizara en la tabla user_options la configuracion de lenguaje a Español
     *
     * @param event dd
     */
    @Override
    @FXML
    public void handleES(ActionEvent event) {
        try {
            User_optionsDAO.get_instance().updateLanguage(Execute.getUser_logged().getUser_name(), Language.ES.name());
            Execute.getMainController().UpdateLanguage();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Ejecutara el metodo logout de la clase abstracta Controller. Este otro metodo cerrara sesion automaticamente
     */
    public void logOut(){
        Execute.getMainController().logOut();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}