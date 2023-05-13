package com.luishidalgoa.Nexa.Controller;

import com.luishidalgoa.Nexa.Execute;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
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

public class LoginController implements Initializable {
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
                    Label_warningUser.setStyle("-fx-text-fill: red");
                    Label_warningUser.setText("This user not found");
                    PasswordField_Password.setDisable(true);
                }else{
                    Label_warningUser.setStyle("-fx-text-fill: green");
                    Label_warningUser.setText("Already exist");
                    PasswordField_Password.setDisable(false);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void login() throws IOException {
        if(TextField_user_name.getText()!=null && PasswordField_Password!=null){
            UserDTO user= Login.Sing_in(TextField_user_name.getText(),PasswordField_Password.getText());
            if(user==null){
                Label_login_error.setText("the username or password are incorrect");
            }else{
                Execute.setUser_logged(user);
                FXMLLoader fxmlLoader = new FXMLLoader(Execute.class.getResource("Controller/Home.fxml"));
                Parent parent=fxmlLoader.load();

                Execute.setRoot(parent);
            }
        }else{
            Label_login_error.setText("you should fill out the fields");
        }
    }

}
