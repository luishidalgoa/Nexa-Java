package com.luishidalgoa.Nexa.Interfaces.iDAOS;

import com.luishidalgoa.Nexa.Model.Domain.Publications.Like;

import java.sql.SQLException;
import java.util.Set;

public interface iLikeDAO {
    public boolean save(Like entity) throws SQLException;

    /**
     * El objetivo es que permita eliminar el like del usuario buscado en la publicacion deseada
     * @param id_publication
     * @return
     */

    public boolean delete(int id_publication,String user_name) throws SQLException;

    /**
     * Elimina los likes que son de la publicacion con el id buscado
     * @return
     */
    public boolean detele(int id_publication) throws SQLException;

    /**
     * Este metodo buscara un Like por el id de la publicacion y el usuario que la hizo.
     * En principio este metodo esta pensado con el objetivo de saber si se un usuario realizo
     * un "Like" dentro en una misma publicacion o comentario
     * @return
     */
    public Like findLike(int id_publication, String user_name) throws SQLException;

    /**
     * Este metodo buscara todos los likes de una publicacion. Los devuelve en forma
     * de Set<>
     * @param id_publication id de la publicacion a recorrer
     * @return
     */
    public Set<Like> findById(int id_publication) throws SQLException;
}
