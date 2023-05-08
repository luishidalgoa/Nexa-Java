package com.luishidalgoa.Nexa.Model.Domain.User;

import java.util.Objects;
import java.util.Set;

public class User {
    protected String user_name;
    protected String password;
    protected String biography;
    public User() {
    }

    public User(String user_name, String password, String biography) {
        this.user_name = user_name;
        this.password = password;
        this.biography = biography;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", biography='" + biography + '\'' +
                '}';
    }
}
