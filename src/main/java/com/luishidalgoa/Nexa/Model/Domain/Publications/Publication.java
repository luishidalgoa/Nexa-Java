package com.luishidalgoa.Nexa.Model.Domain.Publications;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;

import java.sql.Timestamp;
import java.util.Objects;

public class Publication {
    protected Integer id;
    protected Timestamp publication_date;
    protected String text;
    protected UserDTO user;

    public Publication(String text, UserDTO user) {
        this.text = text;
        this.user = user;
    }

    public Publication() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Timestamp publication_date) {
        this.publication_date = publication_date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        Publication that = (Publication) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", publication_date=" + publication_date +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }
}
