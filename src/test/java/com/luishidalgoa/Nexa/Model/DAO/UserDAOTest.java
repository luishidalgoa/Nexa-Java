package com.luishidalgoa.Nexa.Model.DAO;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private UserDAO userDAO=UserDAO.getInstance();

    @Test
    void findAll() {
        try {
            System.out.println(userDAO.findAll().size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void searchUserLuis() {
        UserDTO aux;
        try {
           aux= userDAO.searchUser("Luis");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(aux.getUser().getUser_name(),"Luis");
    }
    @Test
    void searchUserNull() {
        try {
            assertEquals(userDAO.searchUser("sdgfgbh"),null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*@Test
    void save(){
        User aux=new User("prueba","123","");
        try {
            assertEquals(userDAO.save(aux),true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    @Test
    void delete(){
        try{
            assertEquals(userDAO.delete("prueba"),false);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}