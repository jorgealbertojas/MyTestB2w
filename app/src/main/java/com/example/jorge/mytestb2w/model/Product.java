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
    private String url_image_small;
    private String url_image_big;
    private String value;
    private String price;
    private int quantity;

    public String getUrl_image() {
        return url_image_small;
    }

    public void setUrl_image(String url_image) {
        this.url_image_small = url_image;
    }


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

    public String getUrl_image_small() {
        return url_image_small;
    }

    public void setUrl_image_small(String url_image_small) {
        this.url_image_small = url_image_small;
    }

    public String getUrl_image_big() {
        return url_image_big;
    }

    public void setUrl_image_big(String url_image_big) {
        this.url_image_big = url_image_big;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
