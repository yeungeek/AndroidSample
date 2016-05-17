package com.yeungeek.publictech.reflection;

import android.util.Log;

/**
 */
@Brand(name = "Good Father")
public class Father extends Person implements Smoke {
    @Brand(name = "Field Annotation")
    private boolean isMan;

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
