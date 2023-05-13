package com.luishidalgoa.Nexa.Model.DAO;
import com.luishidalgoa.Nexa.Interfaces.iDAOS.iUserDAO;
import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO implements iUserDAO{
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.UserDAO");
    private final Connection con;
    private static UserDAO _instance;

    private UserDAO() {
        this.con = ConnectionMySQL.getConnect();
    }

    /**
     * Este metodo devolvera una lista de todos los usuarios que existan en la bbdd. Soy consciente que para un proyecto
     * pequeño como este es una opcion valida . Sin embargo me gustaria aprender a crear algoritmos complejos
     * para de algun modo devolver una lista mas selectiva en lugar de todos los usuarios lo cual en cuanto a rendimiento
     * es menos eficiente
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public Set<UserDTO> findAll() throws SQLException {
        PreparedStatement p= con.prepareStatement("CALL nexadatabase.UserFindAll()");
        ResultSet users= p.executeQuery();
        Set<UserDTO>result=new HashSet<>();
        while(users.next()){
            User aux= new User();
            aux.setUser_name(users.getString("user_name"));
            aux.setPassword(users.getString("pass"));
            aux.setBiography(users.getString("biography"));
            result.add(buildDTO(aux));
        }
        return result;
    }

    /**
     * El objetivo de este metodo es construir un DTO de usuario. de este modo refactorizo mi codigo. Pero esta pensado para
     * ser usado exclusivamente en el DAO
     * @param aux Objeto del cual crearemos el DAO
     * @return dd
     */
    private UserDTO buildDTO(User aux){
        UserDTO result=new UserDTO();
        if(aux!=null){
            result.setUser_name(aux.getUser_name());
            result.setBiography(aux.getBiography());
            try {
                result.setUserOptions(User_optionsDAO.get_instance().FindLanguage(aux.getUser_name()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Metodo el cual devolvera un DTO de usuario . Este metodo buscara en la base de datos si existe el usuario solicitado
     * Y lo devolvera en forma de DTO por motivos de seguridad ademas de motivos de simplificacion
     * @param user_name Nombre de usuario solicitado
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public UserDTO searchUser(String user_name) throws SQLException {
        PreparedStatement p=this.con.prepareStatement("CALL nexadatabase.UserSearchUser(?)");
        p.setString(1,user_name);
        ResultSet set=p.executeQuery();
        if(set.next()){
            User aux= new User();
            aux.setUser_name(set.getString("user_name"));
            aux.setPassword(set.getString("pass"));
            aux.setBiography(set.getString("biography"));
            return buildDTO(aux);
        }
        return null;
    }

    /**
     * A diferencia del dto el cual no devuelve una constraseña por seguridad.
     * esta cosulta devolveria un usuario con su password. El objetivo unico de esta consulta
     * es devolverselo al sistema logueo
     * @param user_name dd
     * @return dd
     */
    private User findUser(String user_name) throws SQLException {
        PreparedStatement p=this.con.prepareStatement("CALL nexadatabase.UserSearchUser(?)");
        p.setString(1,user_name);
        ResultSet set=p.executeQuery();
        User result=new User();
        while (set.next()){
            result.setUser_name(set.getString("user_name"));
            result.setBiography(set.getString("biography"));
            result.setPassword(set.getString("pass"));
        }
        if(result.getUser_name()==null){
            result=null;
        }
        return result;
    }


    //----------

    /**
     * Una vez verificado la identidad desde el metodo Sing_in de la clase Utils.Login. Retornara el usuario Construido en DTO
     * @param username Usuario en el que se iniciara la sesion
     * @param password la contraseña del usuario
     * @return dd
     * @throws SQLException dd
     */
    public UserDTO signIn(String username, String password) throws SQLException {
        if(findUser(username)!=null && findUser(username).getPassword().equals(password)){
            return searchUser(username);
        }
        logger.log(Level.SEVERE,"Couldn´t sing in with the user count "+username);
        return null;
    }

    /**
     * Guardara los datos de un nuevo usuario. Este metodo sera llamado desde el metodo Sing_Up de la clase Utils.Login
     * @param entity Usuario del cual guardaremos la informacion en la bbdd
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public boolean save(User entity) throws SQLException {
        PreparedStatement p= this.con.prepareStatement("CALL nexadatabase.UserSave(?,?,?)");
        UserDTO aux=searchUser(entity.getUser_name());
        if(aux == null && searchUser(entity.getUser_name())==null){
            p.setString(1,entity.getUser_name());
            p.setString(2, entity.getPassword());
            p.setString(3,entity.getBiography());
            p.executeUpdate();
        }else{
            logger.log(Level.WARNING,"WARNING. Has not could saved the user with user_name "+entity.getUser_name());
            return false;
        }
        return true;
    }

    /**
     * Eliminara el usuario solicitado de la base de datos
     * @param user_name usuario a eliminar
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public boolean delete(String user_name) throws SQLException {
        if(user_name!=null && searchUser(user_name)!=null){
            PreparedStatement p = this.con.prepareStatement("CALL nexadatabase.UserDelete(?)");
            p.setString(1,user_name);
            p.executeUpdate();
        }
        if(searchUser(user_name)!=null){
            logger.log(Level.WARNING,"WARNING. The user with user_name "+ user_name +" Has not could been deleted");
            return false;
        }
        return true;
    }

    /**
     * Actualizara en la base de datos la biografia del usuario
     * @param username valor dedicado para el nombre del usuario del cual realizaremos la actualizacion
     * @param value valor del nuevo estado de la biografia del usuario que se desea actualizar
     * @return devuelve true o false en base si la operacion fue satisfactoria o no
     */
    public boolean updateBiography(String username,String value) throws SQLException {
        PreparedStatement p= this.con.prepareStatement("Call nexadatabase.UserUpdateBiography(?,?)");
        p.setString(1,username);
        p.setString(2,value);
        p.executeUpdate();
        if(!searchUser(username).getBiography().equals(value)){
            return false;
        }
        return true;
    }

    public static UserDAO getInstance(){
        if(_instance==null){
            _instance= new UserDAO();
        }
        return _instance;
    }
}
