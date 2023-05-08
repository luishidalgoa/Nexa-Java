package com.luishidalgoa.Nexa.Model.Domain.Publications;

import com.luishidalgoa.Nexa.Abstracts.Social;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;

public final class Share extends Social {
    public Share(int id, Publication publication, UserDTO user) {
        super(id, publication, user);
    }

    public Share() {
    }

    public Share(Publication publication, UserDTO user) {
        super(publication, user);
    }

    /**
     * Este metodo agregara un nuevo Share. de modo que en cada publicacion solo pueda haber como mucho 1 share por usuario
     */
    @Override
    public void add() {

    }
    /**
     * Este metodo eliminara un Share realizado por un mismo usuario en una misma publicacion.
     */
    @Override
    public void delete() {

    }

    @Override
    public String toString() {
        return "Share{" +
                "publication=" + publication +
                ", user=" + user +
                '}';
    }
}
