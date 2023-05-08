package com.luishidalgoa.Nexa.Model.Domain.User;

import com.luishidalgoa.Nexa.Model.Enum.Language;

public class User_options {
    private Language Language;

    public User_options(String language) {
        Language = com.luishidalgoa.Nexa.Model.Enum.Language.fromName(language);
    }

    public User_options() {
    }

    public com.luishidalgoa.Nexa.Model.Enum.Language getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = com.luishidalgoa.Nexa.Model.Enum.Language.fromName(language);
    }
}
