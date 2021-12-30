package nju.java.logic.system.engine.utils;

import java.io.Serializable;

public class OData implements Serializable {
    public long triggerTime;
    public String name;
    public String input;
    public OData(long triggerTime, String input, String name){
        this.triggerTime = triggerTime;
        this.input = input;
        this.name = name;
    }
}
