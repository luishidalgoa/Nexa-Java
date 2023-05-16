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
import java.util.logging.Logger;

public class CollectionDAO {
    private static CollectionDAO _instnace;
    private final static Logger logger= com.luishidalgoa.Nexa.Utils.Logger.CreateLogger("com.luisidalgoa.com.Model.DAO.Collection.CollectionDAO");
    private final Connection con;
    private CollectionDAO() {
        this.con = ConnectionMySQL.getConnect();
    }
    public Set<PublicationDTO>findByCollection(String user_name,String name) throws SQLException {
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
                return null; //Forzamos que si ocurre algun problema se devuelva null
            }
        }
        return result;
    }
    public Set<CollectionDTO> findByUser(String username) throws SQLException {
        PreparedStatement p= this.con.prepareStatement("call nexadatabase.CollectionFindByUser(?)");
        p.setString(1,username);
        ResultSet set= p.executeQuery();
        Set<CollectionDTO> result= new HashSet<>();
        while (set.next()){
            CollectionDTO aux= new CollectionDTO();
            aux.setCollection(new Collection(set.getString("name"),username));
            aux.setCollectionPublication(findByCollection(username,set.getString("name")));
            result.add(aux);
        }
        return result;
    }
    public boolean add(String username,String name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("call nexadatabase.CollectionAdd(?,?)");
        p.setString(1,username);
        p.setString(2,name);
        p.executeUpdate();
        if(findByCollection(username,name)!=null){
            return true;
        }else{
            return false;
        }
    }
    public boolean delete(String username,String name) throws SQLException {
        PreparedStatement p = this.con.prepareStatement("call nexadatabase.CollectionDelete(?,?)");
        p.setString(1, username);
        p.setString(2,name);
        p.executeUpdate();
        if(findByCollection(username,name)!=null){
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
