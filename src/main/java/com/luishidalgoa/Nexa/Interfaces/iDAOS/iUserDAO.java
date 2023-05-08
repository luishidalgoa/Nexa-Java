package com.luishidalgoa.Nexa.Interfaces.iDAOS;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;

import java.sql.SQLException;
import java.util.Set;

public interface iUserDAO {
    public Set<UserDTO> findAll() throws SQLException;

    /**
     * Este metodo devolvera un usuario con el nombre pasado por parametro
     */
    public UserDTO searchUser(String user_name) throws SQLException;
    public boolean save(User entity) throws SQLException;
    public boolean delete(String user_name) throws SQLException;
}
