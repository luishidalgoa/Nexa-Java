package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.Enum.Language;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OptionsPanel implements Initializable {
    @FXML
    private MenuItem EN;
    @FXML
    private MenuItem ES;

    @FXML
    public void handleEN(ActionEvent event) {
        try {
            User_optionsDAO.get_instance().update(Execute.mainController.getUser_logged().getUser().getUser_name(), Language.EN.name());
            Execute.mainController.UpdateLanguage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleES(ActionEvent event) {
        try {
            User_optionsDAO.get_instance().update(Execute.mainController.getUser_logged().getUser().getUser_name(), Language.ES.name());
            Execute.mainController.UpdateLanguage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("CHECK Option panel");
    }
}