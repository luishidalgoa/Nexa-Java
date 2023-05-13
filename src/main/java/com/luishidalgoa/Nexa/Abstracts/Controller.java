package com.luishidalgoa.Nexa.Abstracts;

import com.luishidalgoa.Nexa.Model.DTO.UserDTO;
import java.util.Set;

public abstract class Controller {
    public abstract void Home();
    public abstract void Collection();
    public abstract void Message();
    public abstract void Perfil();
    public abstract void UpdateLanguage();
    public abstract void updatePublicationPanel();
    public abstract void suggestion_panel();
    public abstract Set<UserDTO> getRecommendUsers();


}
