package com.luishidalgoa.Nexa.Interfaces.iControllers;

import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;
import com.luishidalgoa.Nexa.Model.DTO.UserDTO;

import java.net.URL;
import java.util.ResourceBundle;

public interface iPublicationController {
    void initialize(URL location, ResourceBundle resources);

    void UpdateLanguage();

    void setData(UserDTO u, PublicationDTO p);

    void Liked();

    void shared();

    void optionsAvalaible();

    void deleted();
}
