package com.luishidalgoa.Nexa.Interfaces.iDAOS;

import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;

import java.sql.SQLException;
import java.util.Set;

public interface iShareDAO {

    /**
     * Este metodo eliminara el "share" de una publicacion especifica realizado
     * por un usuario especifico
     * @param id_publication
     * @param user_name
     * @return
     */
    public boolean delete(int id_publication,String user_name) throws SQLException;

    /**
     * Este metodo eliminara todos los share de una publicacion
     * @param id_publication
     * @return
     */
    public boolean delete(int id_publication) throws SQLException;
    public boolean save(Share entity) throws SQLException;

    /**
     * Este metodo buscara un "share" realizado en un publicacion concreta por un usuario concreto
     * @param id_publication
     * @param user_name
     * @return
     */
    public Share findShare(int id_publication,String user_name) throws SQLException;
    /**
     * Este metodo devolvera todos los share de una publicacion concreta
     * @param id_publication
     * @return
     */
    public Set<Share> findById(int id_publication) throws SQLException;
}
