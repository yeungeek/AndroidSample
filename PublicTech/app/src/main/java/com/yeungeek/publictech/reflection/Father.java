package com.yeungeek.publictech.reflection;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 */
@Brand(name = "Good Father")
public class Father extends Person implements Smoke {
    @Brand(name = "Field Annotation")
    private boolean isMan;

    private List<String> stringList = new ArrayList<>();


    public Father() {
    }

    public Father(String name, int age) {
        super(name, age);
    }

    @Brand(name = "Method Annotation")
    public boolean isMan() {
        return isMan;
    }

    public Father setMan(boolean man) {
        isMan = man;
        return this;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public Father setStringList(List<String> stringList) {
        this.stringList = stringList;
        return this;
    }

    
    @Override
    public void smoke() {
        Log.d("test", "### smoke");
    }

    @Override
    public String toString() {
        return "Father{" +
                "isMan=" + isMan +
                "} " + super.toString();
    }
}
