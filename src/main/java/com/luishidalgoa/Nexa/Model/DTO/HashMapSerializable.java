package com.luishidalgoa.Nexa.Model.DTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class HashMapSerializable<K,T> implements Serializable {
    public HashMap<K,T> map;

    public HashMapSerializable() {
        map=new HashMap<>();
    }
}
