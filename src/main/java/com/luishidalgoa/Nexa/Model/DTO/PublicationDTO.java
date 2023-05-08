package com.luishidalgoa.Nexa.Model.DTO;

import com.luishidalgoa.Nexa.Model.DAO.Publications.LikeDAO;
import com.luishidalgoa.Nexa.Model.DAO.Publications.ShareDAO;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Like;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Publication;
import com.luishidalgoa.Nexa.Model.Domain.Publications.Share;
import com.luishidalgoa.Nexa.Utils.Utils;

import java.sql.SQLException;
import java.util.Set;

public class PublicationDTO {
    public Publication publication;
    public Set<Like> likes;
    public Set<Share> shares;
    private String beetwenDate;

    public PublicationDTO(Publication publication) {
        this.publication = publication;
        try {
            this.likes = LikeDAO.get_instance().findById(publication.getId());
            this.shares = ShareDAO.get_instance().findById(publication.getId());
            this.beetwenDate= Utils.calculateDifference(publication.getPublication_date());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PublicationDTO() {
        this.publication=new Publication();
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Share> getShares() {
        return shares;
    }

    public void setShares(Set<Share> shares) {
        this.shares = shares;
    }

    public String getBeetwenDate() {
        return beetwenDate;
    }

    public void setBeetwenDate(String beetwenDate) {
        this.beetwenDate = beetwenDate;
    }
}
