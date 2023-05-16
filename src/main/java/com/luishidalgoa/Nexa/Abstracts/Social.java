package com.luishidalgoa.Nexa.Abstracts;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;

import java.util.Objects;

public abstract class Social {
    private Integer id;
    public Publication publication;
    public UserDTO user;

    public Social(int id, Publication publication, UserDTO user) {
        this.id = id;
        this.publication = publication;
        this.user = user;
    }

    public Social() {
    }

    public Social(Publication publication, UserDTO user) {
        this.publication = publication;
        this.user = user;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Social social = (Social) o;
        return Objects.equals(id, social.id) && Objects.equals(publication.getId(), social.publication.getId()) && Objects.equals(user.getUser_name(), social.user.getUser_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publication, user);
    }
}
