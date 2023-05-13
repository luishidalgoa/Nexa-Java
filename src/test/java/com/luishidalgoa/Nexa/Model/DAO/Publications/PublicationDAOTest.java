package com.luishidalgoa.Nexa.Model.DAO.Publications;

import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class PublicationDAOTest {
    private PublicationDAO publicationDAO=PublicationDAO.getInstance();

    @Test
    void save() {
        try {
            PublicationDAO.getInstance().save(new Publication("TEST", UserDAO.getInstance().searchUser("prueba")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll() {
        try {
            Set<PublicationDTO> result=publicationDAO.findAll();
            assertEquals(result.size()>0,true);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void findByUser()  {
        try {
            Set<PublicationDTO> result= publicationDAO.findByUser("Luis");
            assertEquals(result.size()>0,true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() {
        try {
            PublicationDTO aux= publicationDAO.findById(12);
            assertEquals(aux!=null,true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {

    }
    @Test
    void findBySocial(){
    }
}