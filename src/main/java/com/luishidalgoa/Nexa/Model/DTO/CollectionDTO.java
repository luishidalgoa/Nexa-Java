package com.luishidalgoa.Nexa.Model.DTO;

import com.luishidalgoa.Nexa.Model.DAO.Collection.CollectionDAO;
import com.luishidalgoa.Nexa.Model.Domain.Collections.Collection;

import java.sql.SQLException;
import java.util.Set;

public class CollectionDTO {
    private Collection collection;
    private Set<PublicationDTO> collectionPublication;

    public CollectionDTO() {
    }

    public CollectionDTO(Collection collection) {
        this.collection = collection;
        try {
            this.collectionPublication= CollectionDAO.getInstance().findByCollection(collection.getName(),collection.getUser_name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Set<PublicationDTO> getCollectionPublication() {
        return collectionPublication;
    }

    public void setCollectionPublication(Set<PublicationDTO> collectionPublication) {
        this.collectionPublication = collectionPublication;
    }
}
