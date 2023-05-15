package com.luishidalgoa.Nexa.Controller.Collection;

import com.luishidalgoa.Nexa.Model.DTO.CollectionDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionCardController implements Initializable {
    @FXML
    private Label label_collectionName;
    @FXML
    private Label label_publicationsNumber;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void setData(CollectionDTO collection){
        this.label_collectionName.setText(collection.getCollection().getName());
        this.label_publicationsNumber.setText(String.valueOf(collection.getCollectionPublication().size()));
    }

}
