package com.luishidalgoa.Nexa.Model.DAO.Publications;

import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShareDAOTest {
    private ShareDAO shareDAO=ShareDAO.get_instance();
    PublicationDAO publicationDAO=PublicationDAO.getInstance();
    UserDAO userDAO=UserDAO.getInstance();
    @Test
    void delete() {
        try {
            shareDAO.save(new Share(publicationDAO.findById(14).getPublication(),userDAO.searchUser("Luis")));
            assertEquals(shareDAO.delete(14,"Luis"),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDelete() {
        try {
            assertEquals(shareDAO.delete(14),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        try {
            assertEquals(shareDAO.save(new Share(publicationDAO.findById(14).getPublication(),userDAO.searchUser("Jose"))),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findShare() {
        try {
            Share result=shareDAO.findShare(14,"Luis");
            System.out.println(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() {
        try {
            Set<Share> result=shareDAO.findById(14);
            System.out.println(result.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}