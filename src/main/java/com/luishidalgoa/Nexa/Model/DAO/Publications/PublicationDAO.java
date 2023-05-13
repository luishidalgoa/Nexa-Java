package com.luishidalgoa.Nexa.Model.DAO.Publications;

import com.luishidalgoa.Nexa.Interfaces.iDAOS.iPublicationDAO;
import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PublicationDAO implements iPublicationDAO {
    private final static Logger logger = com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.Publications.PublicationDAO");
    private final Connection con;
    private static PublicationDAO _instance;
    private PublicationDAO() {
        this.con = ConnectionMySQL.getConnect();
    }

    /**
     * Metodo para guardar una publicacion nueva
     * @param entity dd
     * @throws SQLException dd
     */
    @Override
    public void save(Publication entity) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("Call nexadatabase.PublicationSave(?,?)");
        p.setString(1, entity.getText());
        p.setString(2, entity.getUser().getUser_name());
        p.executeUpdate();
        logger.log(Level.INFO, "OK. The Publication has been saved");
    }

    /**
     * Este metodo buscara todas las publicaciones
     *
     * @return dd
     */
    @Override
    public Set<PublicationDTO> findAll() throws SQLException {
        PreparedStatement p = this.con.prepareStatement("Call nexadatabase.PublicationfindAll()");
        ResultSet set = p.executeQuery();
        Set<PublicationDTO> result = new HashSet<>();
        while (set.next()){
            Publication aux=new Publication();
            aux.setId(set.getInt("id"));
            aux.setText(set.getString("text"));
            aux.setUser(UserDAO.getInstance().searchUser(set.getString("user_name")));
            aux.setPublication_date(set.getTimestamp("publication_date"));
            result.add(new PublicationDTO(aux));
        }
        if(result.size() == 0){
            result=null;
        }
        return result;
    }

    /**
     * Este metodo buscara una lista de publicaciones realizada por un usuario concreto
     *
     * @param user_name dd
     * @return dd
     */
    @Override
    public Set<PublicationDTO> findByUser(String user_name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("CALL nexadatabase.PublicationfindByUser(?)");
        p.setString(1, user_name);
        ResultSet set = p.executeQuery();
        Set<PublicationDTO> result = new HashSet<>();
        while(set.next()){
            PublicationDTO aux= new PublicationDTO();
            aux.getPublication().setId(set.getInt("id"));
            aux.getPublication().setUser(UserDAO.getInstance().searchUser(user_name));
            aux.getPublication().setText(set.getString("text"));
            aux.getPublication().setPublication_date(set.getTimestamp("publication_date"));
            result.add(aux);
        }
        if(result.size()==0){
            result=null;
        }
        return result;
    }
    /**
     * Este metodo buscara una coleccion de publicaciones en base al usuario logueado que lo haya compartido.
     * ademas de agregar tambien las publicaciones que haya realizado
     * @param username
     * @return
     * @throws SQLException
     */
    public Set<PublicationDTO> findBySocial(String username) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("CALL nexadatabase.PublicationFindBySocial(?)");
        p.setString(1,username);
        ResultSet set= p.executeQuery();
        Set<PublicationDTO> result = new HashSet<>();
        while (set.next()){
            Publication aux= new Publication();
            aux.setId(set.getInt("id"));
            aux.setText(set.getString("text"));
            aux.setUser(UserDAO.getInstance().searchUser(set.getString("user_name")));
            aux.setPublication_date(set.getTimestamp("publication_date"));
            result.add(new PublicationDTO(aux));
        }
        if(!result.isEmpty()){
            return result;
        }
        return null;
    }
    /**
     * Este metodo buscara una publicacion a partir de su id
     */
    @Override
    public PublicationDTO findById(int id_publication) throws SQLException {
        PreparedStatement p=this.con.prepareStatement("CALL nexadatabase.PublicationfindById(?)");
        p.setInt(1,id_publication);
        ResultSet set= p.executeQuery();
        PublicationDTO result=new PublicationDTO();
        while (set.next()){
            result.getPublication().setId(set.getInt("id"));
            result.getPublication().setUser(UserDAO.getInstance().searchUser(set.getString("user_name")));
            result.getPublication().setText(set.getString("text"));
            result.getPublication().setPublication_date(set.getTimestamp("publication_date"));
        }
        if(result.getPublication().getId()==null){
            result=null;
        }
        return result;
    }

    /**
     * Este metodo eliminara un publicacion
     *
     * @param id dd
     * @return dd
     */
    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("Call nexadatabase.PublicationDelete(?)");
        p.setInt(1, id);
        p.executeUpdate();
        if (findById(id) == null) {
            logger.log(Level.INFO,"Ok. Has been deleted successful");
            return true;
        } else {
            logger.log(Level.WARNING,"Warning. couldn´t delete post");
            return false;
        }
    }

    /**
     * Actualiara el texto de la publicacion. para posteriormente validar la actualizacion en la bbdd
     * @param aux publicacion actualizada
     * @return dd
     * @throws SQLException dd
     */
    @Override
    public boolean update(PublicationDTO aux,String text) throws SQLException {
        PreparedStatement p= this.con.prepareStatement("CALL nexadatabase.PublicationUpdate(?,?)");
        p.setInt(1,aux.getPublication().getId());
        p.setString(2,text);
        p.executeUpdate();
        if(!findById(aux.getPublication().getId()).getPublication().getText().equals(aux.getPublication().getText())){
            logger.log(Level.WARNING, "could not update post");
            return false;
        }
        return true;
    }
    public static PublicationDAO getInstance() {
        if (_instance == null) {
            _instance = new PublicationDAO();
        }
        return _instance;
    }
}