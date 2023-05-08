package com.luishidalgoa.Nexa.Model.DAO;

import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.Domain.User.User_options;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class User_optionsDAO {
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.User_optionsDAO");
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
     * @return devuelve el objeto de los opciones del usuario
     * @throws SQLException dd
     */
    public User_options FindLanguage(String username) throws SQLException {
        PreparedStatement p= this.con.prepareStatement("CALL nexadatabase.User_optionsFindLanguage(?)");
        p.setString(1,username);
        ResultSet set=p.executeQuery();
        if(set.next()){
            User_options result=new User_options();
            result.setLanguage(set.getString("Language"));
            return result;
        }
        return null;
    }

    /**
     * Este metodo seteara en la configuracion del usuario en la bbdd el nuevo idioma seleccionado
     *
     * @param username usuario al cual actualizaremos el campo
     * @param Language idioma a insertar
     * @throws SQLException dd
     */
    public void updateLanguage(String username, String Language) throws SQLException {
        if (UserDAO.getInstance().searchUser(username)!=null){
            PreparedStatement p= this.con.prepareStatement("CALL nexadatabase.User_optionsUpdateLanguage(?,?)");
            p.setString(1,username);
            p.setString(2,Language);
            p.executeUpdate();
            if(!Objects.requireNonNull(FindLanguage(username)).getLanguage().name().equals(Language)){
                logger.log(Level.WARNING,"WARNING. Failed to update user language settings");
            }
        }
    }
}
