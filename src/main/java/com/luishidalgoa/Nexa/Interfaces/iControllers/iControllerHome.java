package com.luishidalgoa.Nexa.Interfaces.iControllers;

import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;

import java.io.IOException;
import java.util.Set;

public interface iControllerHome {
    public void suggestion_panel();
    public void updatePublicationPanel();
    public void post();

    private Set<PublicationDTO> getAllPublications() {
        return null;
    }

    private Set<UserDTO> getRecommendUsers() {
        return null;
    }
    public void switchPerfil();
    public void UpdateLanguage();
    public void optionPanel() throws IOException;
}
