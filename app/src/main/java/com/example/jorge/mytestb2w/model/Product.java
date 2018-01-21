package com.example.jorge.mytestb2w.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jorge on 20/01/2018.
 */

public class Product implements Serializable{
    private int id;
    private List<Image> image;
    private String name;
    private int rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
}
