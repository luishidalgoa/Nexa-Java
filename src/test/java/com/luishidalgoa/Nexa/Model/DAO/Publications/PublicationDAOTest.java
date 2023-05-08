package com.luishidalgoa.Nexa.Model.DAO.Publications;

import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Set;

class PublicationDAOTest {
    private PublicationDAO publicationDAO=PublicationDAO.getInstance();

    @Test
    void save() {
    }

    @Test
    void findAll() {
        try {
            Set<PublicationDTO> result=publicationDAO.findAll();
            System.out.println(result.size());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void findByUser()  {
        try {
            Set<PublicationDTO> result= publicationDAO.findByUser("Luis");
            System.out.println(result.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() {
        try {
            PublicationDTO aux= publicationDAO.findById(12);
            System.out.println(aux.getPublication().getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
    }
}