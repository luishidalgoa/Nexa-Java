package com.luishidalgoa.Nexa;

import com.luishidalgoa.Nexa.Controller.HomeController;
import com.luishidalgoa.Nexa.Model.DTO.Translated;
import com.luishidalgoa.Nexa.Utils.XMLManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Execute extends Application {

    private static Scene scene;
    public static HomeController mainController;
    private static Stage stage;
   // public static

    @Override
    public void start(Stage stage) throws IOException {
        Translated.get_instance().setTraslated(XMLManager.readXML(Translated.get_instance().getTraslated(),"Language.xml"));
        this.stage=stage;
        this.stage.getIcons().add(new Image("com/luishidalgoa/Nexa/static/images/Logo.png"));
        scene = new Scene(loadFXML("Login"), 930, 620);

        scene.getRoot();

        this.stage.setScene(scene);
        this.stage.setTitle("Nexa App");
        this.stage.setResizable(false);
        this.stage.show();
    }

    public static void setRoot(String nameScene) throws IOException {
        scene.setRoot(loadFXML(nameScene));
        stage.setScene(scene);
        stage.show();
    }
    public static void newStage(String nameScene) throws IOException {
        Stage stage2=new Stage();
        Scene scene2= new Scene(loadFXML(nameScene));
        scene2.setRoot(loadFXML(nameScene));
        stage2.setScene(scene2);
        stage2.show();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Execute.class.getResource("Controller/"+fxml + ".fxml"));
        Parent parent=fxmlLoader.load();
        if (mainController==null && fxmlLoader.getController() instanceof HomeController){
            mainController=fxmlLoader.getController();
        }
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }

    //Dividir setRoot y LoadFXML en 2. primero inicializamos y luego cargamos

}