package com.luishidalgoa.Nexa.Model.Domain.Publications;

import com.luishidalgoa.Nexa.Abstracts.Social;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;

public final class Like extends Social {
    public Like(int id, Publication publication, UserDTO user) {
        super(id, publication, user);
    }

    public Like(Publication publication, UserDTO user) {
        super(publication, user);
    }

    public Like() {
    }

    /**
     * Este metodo agregara un nuevo Like. de modo que en cada publicacion solo pueda haber como mucho 1 like por usuario
     */
    @Override
    public void add() {

    }
    /**
     * Este metodo eliminara un Like realizado por un mismo usuario en una misma publicacion.
     */
    @Override
    public void delete() {

    }

    @Override
    public String toString() {
        return "Like{" +
                "publication=" + publication +
                ", user=" + user +
                '}';
    }
}
