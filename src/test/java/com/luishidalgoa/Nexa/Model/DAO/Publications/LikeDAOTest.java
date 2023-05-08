package com.luishidalgoa.Nexa.Model.DAO.Publications;
import com.luishidalgoa.Nexa.Model.DAO.UserDAO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Like;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LikeDAOTest {

    LikeDAO likeDAO=LikeDAO.get_instance();
    UserDAO userDAO=UserDAO.getInstance();
    PublicationDAO publicationDAO=PublicationDAO.getInstance();
    @Test
    void delete() {
        try {
            likeDAO.save(new Like(publicationDAO.findById(14).getPublication(),userDAO.searchUser("Luis")));
            assertEquals(likeDAO.delete(14,"Luis"),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDelete() {
        try {
            assertEquals(likeDAO.detele(14),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        try {
            assertEquals(likeDAO.save(new Like(publicationDAO.findById(14).getPublication(),userDAO.searchUser("Jose"))),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findShare() {
        try {
            Like result=likeDAO.findLike(14,"Luis");
            System.out.println(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() {
        try {
            Set<Like> result=likeDAO.findById(14);
            System.out.println(result.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}