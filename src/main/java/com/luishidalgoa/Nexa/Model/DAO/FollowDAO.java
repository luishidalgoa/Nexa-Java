package com.luishidalgoa.Nexa.Model.DAO;

import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Logger;

public class FollowDAO {
    private final static Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.User_optionsDAO");
    private final Connection con;
    private static FollowDAO _instance;
    String followers = "select * from nexadatabase.follow where follow_user LIKE ?";
    String followed = "select * from nexadatabase.follow where follow.user_name LIKE ?";
    String follow = "insert into nexadatabase.follow(user_name, follow_user) VALUES (?,?)";//1º ? nombre de usuario que seguira, 2º nombre usuario del seguido
    String unFollow = "delete from nexadatabase.follow where user_name LIKE ? and follow_user LIKE ?";
    String searchFollow = "select * from nexadatabase.follow where follow.user_name LIKE ? and follow.follow_user LIKE ?";

    private FollowDAO() {
        this.con = ConnectionMySQL.getConnect();
    }

    /**
     * Este método devolverá una lista de todos los usuarios que siguen al usuario deseado
     *
     * @param username nombre usuario del que queremos conocer sus seguidores
     * @return devuelve una lista de usuarios que sigue al usuario origen
     */
    public HashSet<UserDTO> getFollowers(String username) throws SQLException {
        PreparedStatement p = this.con.prepareStatement(followers);
        p.setString(1, username);
        ResultSet set = p.executeQuery();
        HashSet<UserDTO> result = new HashSet<>();
        while (set.next()) {
            UserDTO aux = UserDAO.getInstance().searchUser(set.getString("user_name"));
            result.add(aux);
        }
        return result;
    }

    /**
     * Este método devolverá una lista de todos a los usuarios a los que sigue un usuario
     *
     * @param username nombre de usuario del cual queremos saber los usuarios que sigue
     * @return devuelve la lista de usuarios que sigue
     */
    public HashSet<UserDTO> getFollowed(String username) throws SQLException {
        PreparedStatement p = this.con.prepareStatement(followed);
        p.setString(1, username);
        ResultSet set = p.executeQuery();
        HashSet<UserDTO> result = new HashSet<>();
        while (set.next()) {
            UserDTO aux = UserDAO.getInstance().searchUser(set.getString("follow_user"));
            result.add(aux);
        }
        return result;
    }

    /**
     * Este método permitira a un usuario seguir a otro usuario
     */
    public void follow(String username, String user_follow) throws SQLException {
        PreparedStatement p = this.con.prepareStatement(follow);
        p.setString(1, username);
        p.setString(2, user_follow);
        p.executeUpdate();
    }

    /**
     * Este método permitira a un usuario dejar de seguir a otro usuario
     */
    public void unFollow(String username, String user_follow) throws SQLException {
        PreparedStatement p = this.con.prepareStatement(unFollow);
        p.setString(1, username);
        p.setString(2, user_follow);
        p.executeUpdate();
    }

    public boolean searchFollow(String username, String user_follow) throws SQLException {
        PreparedStatement p = this.con.prepareStatement(searchFollow);
        p.setString(1, username);
        p.setString(2, user_follow);
        ResultSet set = p.executeQuery();
        while (set.next()){
            String followed = set.getString("follow_user");//Usuario seguido
            String follower = set.getString("user_name");//Usuario que sigue
            return followed != null && follower != null;
        }
        return false;
    }

    public static FollowDAO get_instance() {
        if (_instance == null) {
            _instance = new FollowDAO();
        }
        return _instance;
    }
}
