package com.luishidalgoa.Nexa.Model.Domain.Collections;

public class Collection {
    private String name;
    private String user_name; //Implementacion lazy. No me interesa construir un Usuario

    public Collection(String name, String user_name) {
        this.name = name;
        this.user_name = user_name;
    }

    public Collection() {
    }

    public String getName() {
        return name;
    }

    public String getUser_name() {
        return user_name;
    }
}
