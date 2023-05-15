module com.luishidalgoa.Nexa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires javafx.graphics;
    opens com.luishidalgoa.Nexa to javafx.fxml;
    exports com.luishidalgoa.Nexa;
    exports com.luishidalgoa.Nexa.Controller;
    opens com.luishidalgoa.Nexa.Controller to javafx.fxml;
    opens com.luishidalgoa.Nexa.Model.DTO to java.xml.bind;

    // other module declarations here
    opens com.luishidalgoa.Nexa.Model.Connections;
    exports com.luishidalgoa.Nexa.Interfaces.iControllers;
    opens com.luishidalgoa.Nexa.Interfaces.iControllers to javafx.fxml;
    exports com.luishidalgoa.Nexa.Controller.Collection;
    opens com.luishidalgoa.Nexa.Controller.Collection to javafx.fxml;
}
