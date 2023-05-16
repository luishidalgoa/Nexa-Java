package com.luishidalgoa.Nexa.Model.DAO.Collection;

import com.luishidalgoa.Nexa.Model.Connections.ConnectionMySQL;
import com.luishidalgoa.Nexa.Model.DAO.Publications.PublicationDAO;
import com.luishidalgoa.Nexa.Model.DTO.CollectionDTO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.Domain.Collections.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollectionDAO {
    private static CollectionDAO _instnace;
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.Collection.CollectionDAO");
    private final Connection con;
    private CollectionDAO() {
        this.con = ConnectionMySQL.getConnect();
    }



    /**
     * Buscara todas las colecciones que posee el usuario solicitado
     * @param username nombre usuario poseedor de las colecciones
     * @return dd
     * @throws SQLException dd
     */
    public Set<CollectionDTO> findByUser(String username) throws SQLException {
        PreparedStatement p= this.con.prepareStatement("call nexadatabase.CollectionFindByUser(?)");
        p.setString(1,username);
        ResultSet set= p.executeQuery();
        Set<CollectionDTO> result= new HashSet<>();
        while (set.next()){
            CollectionDTO aux= new CollectionDTO();
            aux.setCollection(new Collection(set.getString("name"),username));
            aux.setCollectionPublication(Publication_CollectionDAO.getInstance().findByCollection(username,set.getString("name")));
            result.add(aux);
        }
        return result;
    }

    /**
     * Guardara una nueva coleccion para el usuario 'X'
     * @param username usuario al cual se le agregara la coleccion
     * @param name nombre de la coleccion
     * @return dd
     * @throws SQLException dd
     */
    public boolean add(String username,String name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("call nexadatabase.CollectionAdd(?,?)");
        p.setString(1,username);
        p.setString(2,name);
        p.executeUpdate();
        if(Publication_CollectionDAO.getInstance().findByCollection(username,name)!=null){
            return true;
        }else{
            logger.log(Level.WARNING,"Could not save collection");
            return false;
        }
    }

    /**
     * Este metodo eliminara un coleccion poseida por el usuario 'X'
     * @param username usuario dueño de la coleccion
     * @param name nombre de la coleccion
     * @return true o false dependiendo de haber sido satisfactorio la operacion
     * @throws SQLException dd
     */
    public boolean delete(String username,String name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("call nexadatabase.CollectionDelete(?,?)");
        p.setString(1, username);
        p.setString(2,name);
        p.executeUpdate();
        if(Publication_CollectionDAO.getInstance().findByCollection(username,name)!=null){
            logger.log(Level.WARNING,"Could not delete collection");
            return false;
        }
        return true;
    }
    public static CollectionDAO getInstance() {
        if(_instnace==null){
            _instnace=new CollectionDAO();
        }
        return _instnace;
    }
}
