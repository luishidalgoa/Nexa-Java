package com.luishidalgoa.Nexa.Utils;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    @Test
    void sing_up() {
        assertEquals(Login.Sing_up(new User("Luis","1234","")),false);
    }

    @Test
    void sing_in() {
        UserDTO aux=Login.Sing_in("Luis","1234");
        assertEquals(aux.getUser_name(),aux.getUser_name());
    }


}