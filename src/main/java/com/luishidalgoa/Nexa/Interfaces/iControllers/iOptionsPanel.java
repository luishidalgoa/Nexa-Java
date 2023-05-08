package com.luishidalgoa.Nexa.Interfaces.iControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public interface iOptionsPanel {
    @FXML
    void handleEN(ActionEvent event);

    @FXML
    void handleES(ActionEvent event);

    void initialize(URL location, ResourceBundle resources);
}
