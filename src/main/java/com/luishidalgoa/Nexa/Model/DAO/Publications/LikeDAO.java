package com.luishidalgoa.Nexa.Model.DAO.Publications;

import com.luishidalgoa.Nexa.Interfaces.iDAOS.iLikeDAO;
import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class LikeDAO implements iLikeDAO {
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.Publications.LikeDAO");
    private final Connection con;
    //Almacenaremos los DAOS de user y publication
    private static LikeDAO _instance;
    PublicationDAO publicationDAO = PublicationDAO.getInstance();

    private LikeDAO() {
        this.con = ConnectionMySQL.getConnect();
    }

    /**
     * Este metodo eliminara el "like" de una publicacion especifica realizado
     * por un usuario especifico
     * @param id_publication id a buscar
     * @param user_name nombre a buscar
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public boolean delete(int id_publication, String user_name) throws SQLException {
        if(findLike(id_publication,user_name)!=null){
            PreparedStatement p = this.con.prepareStatement("CALL nexadatabase.`LikeDelete`(?,?)");
            p.setInt(1, id_publication);
            p.setString(2, user_name);
            p.executeUpdate();
            if (findLike(id_publication, user_name) != null) {
                logger.log(Level.WARNING,"WARNING. The Like with id publication: "+id_publication+" and username liked: "+user_name+" hasn´t been deleted");
                return false;
            }
        }else{
            logger.log(Level.WARNING, "WARNING. The publication like with id "+id_publication+ "and username "+ user_name+" Hasn´t could found");
            return false;
        }
        return true;
    }

    /**
     * Este metodo eliminara todos los likes de una publicacion
     * @param id_publication dd
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public boolean detele(int id_publication) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("CALL nexadatabase.`LikesDelete`(?)");
        p.setInt(1, id_publication);
        p.executeUpdate();
        if(findById(id_publication).size()>0){
            logger.log(Level.SEVERE,"ERROR. The Like with id publication "+id_publication+ " could´nt be deleted");
            return false;
        }
        return true;
    }

    @Override
    public boolean save(Like entity) throws SQLException {
        if (findLike(entity.publication.getId(), entity.user.getUser_name()) == null) {
            PreparedStatement p = this.con.prepareStatement("CALL nexadatabase.`LikeSave`(?,?)");
            p.setInt(1, entity.publication.getId());
            p.setString(2, entity.user.getUser_name());
            p.executeUpdate();
        } else {
            logger.log(Level.SEVERE,"ERROR. Could´t be saved The like with publication_id "+ entity.getPublication().getId()+ " and user_name to user "+ entity.getUser().getUser_name());
            return false;
        }
        return true;
    }

    /**
     * Este metodo buscara un "like" realizado en un publicacion concreta por un usuario concreto
     * @param id_publication dd
     * @param user_name dd
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public Like findLike(int id_publication, String user_name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement(/*"CALL nexadatabase.`LikeFindLike`(?,?)"*/"Select * from nexadatabase.likes where id_publication in(select id from nexadatabase.publication where id=?) and user_name LIKE(select user_name from nexadatabase.user where user_name like ?) group by id;");
        p.setInt(1, id_publication);
        p.setString(2, user_name);
        ResultSet set = p.executeQuery();
        Like result = new Like();
        while (set.next()) {
            result.setId(set.getInt("id"));
            result.setPublication(publicationDAO.findById(id_publication).getPublication());
            result.setUser(UserDAO.getInstance().searchUser(user_name));
        }
        if (result.getUser() == null || result.getPublication() == null) {
            result = null;
        }
        return result;
    }

    /**
     *  Este metodo devolvera todos los like de una publicacion concreta
     * @param id_publication id de la publicacion a recorrer
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public Set<Like> findById(int id_publication) throws SQLException {
        //creamos la sentencia
        PreparedStatement p = con.prepareStatement("CALL nexadatabase.`LikeFindById`(?)");
        p.setInt(1, id_publication);
        Set<Like> result = new HashSet<>();
        ResultSet set = p.executeQuery();
        //recorremos el set<>
        while (set.next()) {
            if (publicationDAO.findById(id_publication) != null) {
                Like aux = new Like();
                aux.setId(set.getInt("id"));
                aux.setUser(UserDAO.getInstance().searchUser("user_name"));
                aux.setPublication(publicationDAO.findById(id_publication).getPublication());
                result.add(aux);
            }else{
            }
        }
        return result;
    }
    public static LikeDAO get_instance(){
        if(_instance==null){
            _instance=new LikeDAO();
        }
        return _instance;
    }
}
