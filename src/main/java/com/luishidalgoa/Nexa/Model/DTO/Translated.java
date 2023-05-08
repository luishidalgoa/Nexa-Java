package com.luishidalgoa.Nexa.Model.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Translated implements Serializable {
    /**
     * HashMapRaiz: A; HashMap anidado: B;
     *
     * El objetivo de este hashMap anidado es acceder a los datos de cada idioma a partir de
     * la clave HashMap 'A'. una vez accedido . Las claves del nodo hijo del HashMap raiz hara referencia
     * al atributo que deseamos traducir . por lo que obteniendo el acceso a esa clave obtendremos
     * el objeto traducido al idioma deseado
     */
    private static HashMapSerializable<String, HashMapSerializable<String, String>> Traslated=new HashMapSerializable<>();
    @XmlTransient
    private static Translated _instance;
    private Translated() {
    }

    public HashMapSerializable<String, HashMapSerializable<String, String>> getTraslated() {
        return Traslated;
    }

    public void setTraslated(HashMapSerializable<String, HashMapSerializable<String, String>> traslated) {
        Traslated = traslated;
    }

    public static Translated get_instance() {
        if(_instance==null){
            _instance=new Translated();
        }
        return _instance;
    }

    public static void set_instance(Translated _instance) {
        Translated._instance = _instance;
    }
}
