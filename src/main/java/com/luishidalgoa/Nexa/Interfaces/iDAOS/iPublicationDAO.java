package com.luishidalgoa.Nexa.Interfaces.iDAOS;

import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;

import java.sql.SQLException;
import java.util.Set;

public interface iPublicationDAO {
    public void save(Publication entity) throws SQLException;

    /**
     * Este metodo buscara todas las publicaciones
     * @return
     */
    public Set<PublicationDTO> findAll() throws SQLException;

    /**
     * Este metodo buscara una lista de publicaciones realizada por un usuario concreto
     * @param user_name
     * @return
     */
    public Set<PublicationDTO>findByUser(String user_name) throws SQLException;
    /**
     * Este metodo buscara una publicacion a partir de su id
     */
    public PublicationDTO findById(int id_publication) throws SQLException;
    /**
     * El objetivo de este metodo es buscar todos los compartidos por un usuario
     * @return dd
     */
    public Set<PublicationDTO> findBySocial(String username) throws SQLException;

    /**
     * Este metodo eliminara un publicacion
     * @param id dd
     * @return dd
     */
    public boolean delete(int id) throws SQLException;
    public boolean update(PublicationDTO p,String text) throws SQLException;
}
