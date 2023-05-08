package com.luishidalgoa.Nexa.Model.Enum;

public enum Scenes {
    /**
     * Esto me permitira mayor flexibilidad de cara a si en el futuro se renombra alguna de los archivos fxml
     * Ademas es una buena praxis
     */
    Login("src/main/java/com.luishidalgo.Nexa/resources/com.luishidalgo.Nexa/Login"),
    Home("src/main/java/com.luishidalgo.Nexa/resources/com.luishidalgo.Nexa/Home");
    private String url;
    Scenes(String Scene) {
        this.url=Scene;
    }
    public String getUrl(){
        return url;
    }
}
