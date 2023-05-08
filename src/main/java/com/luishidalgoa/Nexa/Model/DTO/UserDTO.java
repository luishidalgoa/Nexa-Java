package com.luishidalgoa.Nexa.Model.DTO;

import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import com.luishidalgoa.Nexa.Model.Domain.User.User_options;

import java.sql.SQLException;

public final class UserDTO {
    private User user;
    private User_options userOptions;

    public UserDTO(User user) {
        this.user = user;
        try {
            this.userOptions = User_optionsDAO.get_instance().FindLanguage(user.getUser_name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User_options getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(User_options userOptions) {
        this.userOptions = userOptions;
    }
}
