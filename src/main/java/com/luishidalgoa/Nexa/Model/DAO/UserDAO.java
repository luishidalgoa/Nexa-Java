package com.luishidalgoa.Nexa.Model.DAO;
import com.luishidalgoa.Nexa.Interfaces.iDAOS.iUserDAO;
import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import com.luishidalgoa.Nexa.Utils.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserDAO implements iUserDAO{
    private final String findAll="Select * from nexadatabase.user";
    private final String searchUser="Select * from nexadatabase.user where user_name=?";
    private final String save= "insert into nexadatabase.user(user_name,pass,biography) values(?,?,?)";
    private final String delete="Delete from nexadatabase.user where user_name=?";
    private Connection con;
    private static UserDAO _instance;

    private UserDAO() {
        this.con = ConnectionMySQL.getConnect();
    }



    @Override
    public Set<UserDTO> findAll() throws SQLException {
        PreparedStatement p= con.prepareStatement(findAll);
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
    public UserDTO buildDTO(User aux){
        UserDTO result=new UserDTO();
        result.setUser(aux);
        return result;
    }
    @Override
    public UserDTO searchUser(String user_name) throws SQLException {
        PreparedStatement p=this.con.prepareStatement(searchUser);
        p.setString(1,user_name);
        ResultSet set=p.executeQuery();
        while (set.next()){
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
     * @param user_name
     * @return
     */
    @Override
    public User findUser(String user_name) throws SQLException {
        PreparedStatement p=this.con.prepareStatement(searchUser);
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
    public UserDTO signIn(String username, String password) throws SQLException {
        if(findUser(username).getPassword().equals(password)){
            return searchUser(username);
        }

        return null;
    }


    @Override
    public boolean save(User entity) throws SQLException {
        PreparedStatement p= this.con.prepareStatement(save);
        UserDTO aux=searchUser(entity.getUser_name());
        if(aux == null && searchUser(entity.getUser_name())==null){
            p.setString(1,entity.getUser_name());
            p.setString(2,new String(entity.getPassword()));
            p.setString(3,entity.getBiography());
            p.executeUpdate();
        }else{
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(String user_name) throws SQLException {
        if(user_name!=null){
            PreparedStatement p = this.con.prepareStatement(delete);
            p.setString(1,user_name);
            p.executeUpdate();
        }
        return (searchUser(user_name)!=null);
    }

    public static UserDAO getInstance(){
        if(_instance==null){
            _instance= new UserDAO();
        }
        return _instance;
    }
}
