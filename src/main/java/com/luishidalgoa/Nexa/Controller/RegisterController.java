package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import com.luishidalgoa.Nexa.Utils.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TextField TextField_user_name;
    @FXML
    private Label Label_warningUser;
    @FXML
    private PasswordField PasswordField_Password;
    @FXML
    private Label Label_login_error;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextField_user_name.textProperty().addListener(observable -> { //Realmente esto no deberia implementarse por privacidad
            try {
                if(UserDAO.getInstance().searchUser(TextField_user_name.getText())==null){
                    Label_warningUser.setStyle("-fx-text-fill: green");
                    Label_warningUser.setText("This user not exist");
                    PasswordField_Password.setDisable(false);
                }else{
                    Label_warningUser.setStyle("-fx-text-fill: red");
                    Label_warningUser.setText("Already exist");
                    PasswordField_Password.setDisable(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void login() throws IOException {
        if(TextField_user_name.getText()!=null && PasswordField_Password!=null){
            try {
                if(UserDAO.getInstance().save(new User(TextField_user_name.getText(), Login.encrypt(PasswordField_Password.getText()),""))){
                    FXMLLoader fxmlLoader = new FXMLLoader(Execute.class.getResource("Controller/Login.fxml"));
                    Parent parent=fxmlLoader.load();
                    Execute.setRoot(parent);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            Label_login_error.setText("you should fill out the fields");
        }
    }

}
