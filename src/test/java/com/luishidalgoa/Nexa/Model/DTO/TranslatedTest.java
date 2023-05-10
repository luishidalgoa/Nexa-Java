package com.luishidalgoa.Nexa.Model.DTO;

import com.luishidalgoa.Nexa.Utils.XMLManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import com.luishidalgoa.Nexa.Model.Enum.Language;

class TranslatedTest {
    /**
     * Primero inicializamos el HashMap anidado al cual le insertaremos todos los par clave-valor que
     * necesitemos en nuestras vistas. Posteriormente crearemos el HashMap raiz el cual contendra
     * el HashMap con todo traducido al idioma correspondiente
     */
    @Test
    public void firstExecute(){
        HashMapSerializable<String,String> traducido=new HashMapSerializable<>();
        HashMapSerializable<String,String> translate=new HashMapSerializable<>();
        HashMapSerializable<String, HashMapSerializable<String,String>> Translated=new HashMapSerializable<>();

        //ESPAÑOL
            //HOME
            traducido.map.put("Home_Home","Inicio");
            traducido.map.put("Home_Collections","Marcadores");
            traducido.map.put("Home_Message","Mensajes");
            traducido.map.put("Home_Perfil","Mi perfil");
            traducido.map.put("Home_textField_post","Escribe tu publicacion aqui...");
            traducido.map.put("Home_ShowMore","Mostrar recientes...");
            traducido.map.put("Home_Suggestions","Recomendados");
            traducido.map.put("Home_Post","Enviar");
            //Publication
            traducido.map.put("Publication_Menu_options","Opciones");
            traducido.map.put("Publication_Update","Actualizar");
        //Ingles
            //Home
            translate.map.put("Home_Home","Home");
            translate.map.put("Home_Collections","Collections");
            translate.map.put("Home_Message","Message");
            translate.map.put("Home_Perfil","Perfil");
            translate.map.put("Home_textField_post","Make your post here...");
            translate.map.put("Home_ShowMore","Show more...");
            translate.map.put("Home_Suggestions","Suggestions");
            translate.map.put("Home_Post","Post");
            //Publication
            translate.map.put("Publication_Menu_options","Options");
            translate.map.put("Publication_Update","Update");
            Translated.map.put(Language.ES.name(),traducido);
            Translated.map.put(Language.EN.name(),translate);

        com.luishidalgoa.Nexa.Model.DTO.Translated.get_instance().setTraslated(Translated);
        XMLManager.writeXML(com.luishidalgoa.Nexa.Model.DTO.Translated.get_instance().getTraslated(),"Language.xml");
    }
    @Test
    public void readExecute(){
        Translated.get_instance().setTraslated(XMLManager.readXML(Translated.get_instance().getTraslated(),"Language.xml"));
        Assert.assertEquals(Translated.get_instance().getTraslated().map.size()>0,true);
    }
    @Test
    void getTraslated() {
    }

    @Test
    void setTraslated() {
    }
}