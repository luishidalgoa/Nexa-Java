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

    @Override
    public String toString() {
        return "Share{" +
                "publication=" + publication +
                ", user=" + user +
                '}';
    }
}
