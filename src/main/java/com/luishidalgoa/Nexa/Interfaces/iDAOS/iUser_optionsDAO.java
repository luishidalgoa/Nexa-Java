package com.luishidalgoa.Nexa.Interfaces.iDAOS;

import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.Domain.User.User_options;

import java.sql.SQLException;

public interface iUser_optionsDAO {

    User_options FindLanguage(String username) throws SQLException;

    void updateLanguage(String username, String Language) throws SQLException;
}
