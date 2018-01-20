package com.example.jorge.mytestb2w.model;

/**
 * Created by jorge on 19/01/2018.
 */

public class Menu {
    private String _id;
    private String name;
    private String position;
    private Component component;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
