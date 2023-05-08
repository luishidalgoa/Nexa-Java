package com.luishidalgoa.Nexa.Thread;

import com.luishidalgoa.Nexa.Controller.HomeController;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadUpdatePublications extends Thread{
    private int PublicationsCount;
    private final Initializable Controller;
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Thread.ThreadUpdatePublication");
    public ThreadUpdatePublications(Initializable Controller) {
        this.Controller=Controller;
        try {
            this.PublicationsCount= Objects.requireNonNull(PublicationDAO.getInstance().findAll()).size();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            while (true){
                if(Objects.requireNonNull(PublicationDAO.getInstance().findAll()).size()!=PublicationsCount){
                    PublicationsCount= Objects.requireNonNull(PublicationDAO.getInstance().findAll()).size();
                    Platform.runLater(() -> ((HomeController)Controller).updatePublicationPanel()); //LAMBDA
                    logger.log(Level.INFO,"Ok. The thread updated");
                }
                Thread.sleep(5000);
            }
        }catch (InterruptedException e) {
            logger.log(Level.SEVERE,"ERROR. The thread has been interrumped "+ e.getMessage());
            throw new RuntimeException(e);

        } catch (SQLException e) {
            logger.log(Level.SEVERE,"ERROR. Had ocurred some error on the sql server "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
