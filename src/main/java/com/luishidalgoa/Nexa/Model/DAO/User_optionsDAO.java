package com.luishidalgoa.Nexa.Model.DAO;

import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.Domain.User.User_options;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class User_optionsDAO {
    String findLanguage="SELECT  Language from nexadatabase.user_options where user_name=?";
    String update="UPDATE nexadatabase.user_options SET Language=? where user_name=?";
    private final Connection con;
    private static User_optionsDAO _instance;

    private User_optionsDAO() {
        this.con= ConnectionMySQL.getConnect();
    }

    public static User_optionsDAO get_instance() {
        if(_instance==null){
            _instance=new User_optionsDAO();
        }
        return _instance;
    }

    /**
     * Este metodo busca en la base de datos el lenguaje predefenido por el usuario en sus opciones (user_options).
     * @param username nombre usuario del cual extraeremos su configuracion
     * @return
     * @throws SQLException
     */
    public User_options FindLanguage(String username) throws SQLException {
        PreparedStatement p= this.con.prepareStatement(findLanguage);
        p.setString(1,username);
        ResultSet set=p.executeQuery();
        if(set.next()){
            User_options result=new User_options();
            result.setLanguage(set.getString("Language"));
            return result;
        }
        return null;
    }
    public void update(String username,String Language) throws SQLException {
        PreparedStatement p= this.con.prepareStatement(update);
        p.setString(1,Language);
        p.setString(2,username);
        p.executeUpdate();
    }
}
