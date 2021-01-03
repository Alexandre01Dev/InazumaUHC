package be.alexandre01.inazuma.uhc.utils;

import java.util.ArrayList;

public class CastObject {
    private Object o;
    public CastObject(Object o){
        this.o = o;
    }

    public String getString(){
        return (String) o;
    }

    public int getInt(){
        return (int) o;
    }

    public float getFloat(){
        return (float) o;

    }

    public double getDouble(){
        return (double) o;
    }

    public byte getByte(){
        return (byte) o;
    }

    public Object get(){
        return o;

    }

    public void setObject(Object o){
        this.o = o;
    }
}
