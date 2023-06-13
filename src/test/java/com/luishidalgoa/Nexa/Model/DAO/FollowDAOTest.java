package com.luishidalgoa.Nexa.Model.DAO;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class FollowDAOTest {

    @Test
    void getFollowers() {
        try {
            HashSet<UserDTO>result = FollowDAO.get_instance().getFollowers("Jose");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getFollowed() {
        try {
            HashSet<UserDTO>result = FollowDAO.get_instance().getFollowed("Luis");
            for (UserDTO aux: result){
                System.out.println(aux.getUser_name());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void follow(){
        try {
            FollowDAO.get_instance().follow("Jose","frgthjyu");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void unFollow(){
        try {
            FollowDAO.get_instance().unFollow("Luis","Jose");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}