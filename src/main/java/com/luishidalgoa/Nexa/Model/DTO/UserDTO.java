package com.luishidalgoa.Nexa.Model.DTO;

import com.luishidalgoa.Nexa.Model.DAO.User_optionsDAO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import com.luishidalgoa.Nexa.Model.Domain.User.User_options;

import java.sql.SQLException;

public final class UserDTO {
    private String user_name;
    private String biography;
    private User_options userOptions;

    public UserDTO(User user,User_options userOptions) {
        this.user_name = user.getUser_name();
        this.biography= user.getBiography();
        this.userOptions=userOptions;
    }

    public UserDTO() {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public User_options getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(User_options userOptions) {
        this.userOptions = userOptions;
    }
}
