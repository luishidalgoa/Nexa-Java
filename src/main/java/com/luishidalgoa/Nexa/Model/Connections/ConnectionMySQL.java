package com.luishidalgoa.Nexa.Model.Connections;
import com.luishidalgoa.Nexa.Utils.XMLManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ConnectionMySQL {

    private String file = "conexion.xml";
    private static ConnectionMySQL _newInstance;
    private static Connection con;

    private  ConnectionMySQL() {
        ConnectionData dc = XMLManager.readXML(new ConnectionData(),file);
        try {
            con = DriverManager.getConnection(dc.getServer() + "/" + dc.getDatabase(), dc.getUsername(), dc.getPassword());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            con = null;
            e.printStackTrace();
        }
    }

    public static Connection getConnect() {
        if (_newInstance== null) {
            _newInstance= new ConnectionMySQL();
        }
        return con;
    }

}
