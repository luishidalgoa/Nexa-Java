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

    @Override
    public String toString() {
        return "Like{" +
                "publication=" + publication +
                ", user=" + user +
                '}';
    }
}
