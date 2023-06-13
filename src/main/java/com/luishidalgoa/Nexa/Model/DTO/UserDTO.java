package com.luishidalgoa.Nexa.Model.DTO;

import com.luishidalgoa.Nexa.Model.DAO.FollowDAO;
import com.luishidalgoa.Nexa.Model.Domain.User.User;
import com.luishidalgoa.Nexa.Model.Domain.User.User_options;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;

public final class UserDTO {
    private String user_name;
    private String biography;
    private User_options userOptions;
    private HashSet<UserDTO> follow;
    private HashSet<UserDTO> followed;

    public UserDTO(User user,User_options userOptions) {
        this.user_name = user.getUser_name();
        this.biography= user.getBiography();
        this.userOptions=userOptions;
        try {
            follow= FollowDAO.get_instance().getFollowers(user_name);
            followed=FollowDAO.get_instance().getFollowed(user_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    public HashSet<UserDTO> getFollow() {
        try {
            return FollowDAO.get_instance().getFollowers(user_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFollow(HashSet<UserDTO> follow) {
        this.follow = follow;
    }

    public HashSet<UserDTO> getFollowed() {
        System.out.println(user_name);
        try {
            return FollowDAO.get_instance().getFollowed(user_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFollowed(HashSet<UserDTO> followed) {
        this.followed = followed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(user_name, userDTO.user_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name);
    }
}
