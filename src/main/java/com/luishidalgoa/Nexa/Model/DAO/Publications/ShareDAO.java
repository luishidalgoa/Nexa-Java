package com.luishidalgoa.Nexa.Model.DAO.Publications;

import com.luishidalgoa.Nexa.Interfaces.iDAOS.iShareDAO;
import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ShareDAO implements iShareDAO {
    private final Connection con;
    private static ShareDAO _instance;
    PublicationDAO publicationDAO = PublicationDAO.getInstance();
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.Publications.ShareDAO");

    private ShareDAO() {
        this.con = ConnectionMySQL.getConnect();
    }

    /**
     * Este metodo eliminara el "share" de una publicacion especifica realizado
     * por un usuario especifico
     *
     * @param id_publication dd
     * @param user_name dd
     * @return dd
     */

    @Override
    public boolean delete(int id_publication, String user_name) throws SQLException {
        if(findShare(id_publication, user_name) != null){
            PreparedStatement p = this.con.prepareStatement("call nexadatabase.ShareDelete(?,?)");
            p.setInt(1, id_publication);
            p.setString(2, user_name);
            p.executeUpdate();
            if (findShare(id_publication, user_name) != null) {
                logger.log(Level.SEVERE,"WARNING. couldn´t delete post. The post with id "+ id_publication +" and username shared "+user_name+" hasn´t could delete");
                return false;
            }
        }else{
            logger.log(Level.WARNING, "WARNING. The publication share with id "+id_publication+ "and username shared"+ user_name+" Hasn´t could found");
            return false;
        }
        return true;
    }

    /**
     * Este metodo eliminara todos los share de una publicacion
     *
     * @param id_publication dd
     * @return dd
     */
    @Override
    public boolean delete(int id_publication) throws SQLException {
        if(Objects.requireNonNull(findById(id_publication)).size()>0){
            PreparedStatement p = this.con.prepareStatement("call nexadatabase.SharesDelete(?)");
            p.setInt(1, id_publication);
            p.executeUpdate();
            if (Objects.requireNonNull(findById(id_publication)).size()>0) {
                logger.log(Level.SEVERE, "ERROR. The publication with id "+id_publication+ " Hasn´t could delete");
            }
        }else{
            logger.log(Level.WARNING, "WARNING. The publication with id "+id_publication+ " Hasn´t could found");
            return false;
        }

        return findById(id_publication) != null;
    }

    @Override
    public boolean save(Share entity) throws SQLException {
        if (findShare(entity.publication.getId(), entity.user.getUser_name()) == null) {
            PreparedStatement p = this.con.prepareStatement("call nexadatabase.ShareSave(?,?)");
            p.setInt(1, entity.publication.getId());
            p.setString(2, entity.user.getUser_name());
            p.executeUpdate();
            if(findShare(entity.publication.getId(),entity.getUser().getUser_name())==null){
                logger.log(Level.SEVERE, "To publication shared with id "+entity.getPublication().getId()+ " Hasn´t could saved share");
                return false;
            }
        } else {
            logger.log(Level.WARNING, "WARNING. Hans´t could saved publication Shared. This Shared already exist");
            return false;
        }
        return findShare(entity.publication.getId(), entity.user.getUser_name()) != null;
    }

    /**
     * Este metodo buscara un "share" realizado en un publicacion concreta por un usuario concreto
     *
     * @param id_publication dd
     * @param user_name dd
     * @return dd
     */
    @Override
    public Share findShare(int id_publication, String user_name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("call nexadatabase.ShareFindShare(?,?)");
        p.setInt(1, id_publication);
        p.setString(2, user_name);
        ResultSet set = p.executeQuery();
        Share result = new Share();
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
     * Este metodo devolvera todos los share de una publicacion concreta
     *
     * @param id_publication dd
     * @return dd
     */
    @Override
    public Set<Share> findById(int id_publication) throws SQLException {
        //creamos la sentencia
        PreparedStatement p = con.prepareStatement("call nexadatabase.ShareFindById(?)");
        p.setInt(1, id_publication);
        Set<Share> result = new HashSet<>();
        ResultSet set = p.executeQuery();
        //recorremos el set<>
        while (set.next()) {
            if (publicationDAO.findById(id_publication) != null) {
                Share aux = new Share();
                aux.setId(set.getInt("id"));
                aux.setUser(UserDAO.getInstance().searchUser("user_name"));
                aux.setPublication(publicationDAO.findById(id_publication).getPublication());
                result.add(aux);
            }else{
                return null;
            }
        }
        return result;
    }
    public static ShareDAO get_instance(){
        if(_instance==null){
            _instance=new ShareDAO();
        }
        return _instance;
    }
}