package com.example.jorge.mytestb2w.model.ProductDetail;

import java.util.List;

/**
 * Created by jorge on 21/01/2018.
 */

public class ResultImage {
    private List<Images> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}
