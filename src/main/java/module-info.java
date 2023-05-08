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
}
