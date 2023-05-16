package com.luishidalgoa.Nexa.Model.DAO.Collection;

import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Publication_CollectionDAO {
    private static Publication_CollectionDAO _instnace;
    private final static Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.Collection.Publication_collectionDAO");
    private final Connection con;

    public Publication_CollectionDAO() {
        this.con = ConnectionMySQL.getConnect();
    }

    /**
     * Metodo que buscara una publicacion concreta dentro de una coleccion
     * @param username nombre del usuario dueño de la lista
     * @param name lista donde buscaremos la publicacion
     * @param id publicacion concreta
     * @return dd
     * @throws SQLException dd
     */
    public PublicationDTO searchByPublication(String username, String name, int id) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("Select id_publication from nexadatabase.publications_collection where user_name=? and name=? and id_publication=?");
        p.setString(1, username);
        p.setString(2, name);
        p.setInt(3, id);
        ResultSet set = p.executeQuery();
        PublicationDTO result = null;
        while (set.next()) {
            result = PublicationDAO.getInstance().findById(set.getInt("id_publication"));
        }
        if (result == null) {
            result = null;
            logger.log(Level.WARNING,"The publication with id: "+id+" was not found in the collection "+name+" of the user "+username);
        }
        return result;
    }

    /**
     * Este metodo guardara una referencia a la publicacion dentro de una coleccion
     * @param username nombre del usuario dueño de la lista
     * @param name coleccion donde se guardara la referencia a la publicacion
     * @param id publicacion concreta
     * @return dd
     * @throws SQLException dd
     */
    public boolean add(String username, String name, int id) throws SQLException {

        PreparedStatement p = this.con.prepareStatement("INSERT INTO nexadatabase.publications_collection(user_name, name,id_publication) values (?,?,?)");
        p.setString(1, username);
        p.setString(2, name);
        p.setInt(3, id);
        p.executeUpdate();
        if (searchByPublication(username, name, id) != null) {
            return true;
        }
        logger.log(Level.WARNING,"Could not add post with id "+id+" was not found in the collection "+name+" of the user "+username);
        return false;

    }

    /**
     * Metodo que eliminara la relacion entre una publicacion dentro de una coleccion. por lo que la publicacion
     * desapareceria dentro de la coleccion 'X' del usuario 'Y'
     *
     * @param username nombre usuario dueño de la coleccion
     * @param name     nombre de la coleccion
     * @param id       id de la publicacion
     * @return true si la operacion fue correcta o false en caso contrario
     */
    public boolean delete(String username, String name, int id) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("DELETE FROM nexadatabase.publications_collection where user_name=? and name=? and id_publication=?");
        p.setString(1, username);
        p.setString(2, name);
        p.setInt(3, id);
        p.executeUpdate();
        if (searchByPublication(username, name, id) == null) {
            return true;
        }
        logger.log(Level.WARNING,"could not remove reference to collection publication: "+name);
        return false;
    }
    /**
     * Devuelve una lista set de publicaciones dentro de una coleccion
     * @param user_name nombre del usuario dueño de la coleccion
     * @param name nombre de la coleccion
     * @return dd
     * @throws SQLException dd
     */
    public Set<PublicationDTO> findByCollection(String user_name, String name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("call nexadatabase.CollectionFindPublications(?,?)");
        p.setString(1,user_name);
        p.setString(2,name);
        ResultSet set= p.executeQuery();
        Set<PublicationDTO>result=new HashSet<>();
        while (set.next()){
            PublicationDTO aux= PublicationDAO.getInstance().findById(set.getInt("id_publication"));
            if(aux!=null){
                result.add(aux);
            }else{
                logger.log(Level.WARNING,"WARNING. the publication that you are iterating is empty");
                return null; //Forzamos que si ocurre algun problema se devuelva null
            }
        }
        return result;
    }
    public static Publication_CollectionDAO getInstance() {
        if (_instnace == null) {
            _instnace = new Publication_CollectionDAO();
        }
        return _instnace;
    }
}
