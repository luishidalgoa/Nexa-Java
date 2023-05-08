package com.luishidalgoa.Nexa.Model.Enum;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public enum Language implements Serializable {
    ES,
    EN;

    /**
     * Este metodo comprobara que el nombre pasado por parametrosea igual que alguno de los Enumeradores ya
     * predefinidos
     * @param name nombre a comprobar
     * @return devuelve el Enum si lo ha encontrado. si no devuelvo Null
     */
    public static Language fromName(String name){
        for (Language e: Language.values()){
            if(e.name().equalsIgnoreCase(name)){
                return e;
            }
        }
        return null;
    }
}
