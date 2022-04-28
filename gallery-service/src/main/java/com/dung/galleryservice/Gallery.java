package com.dung.galleryservice;

import java.util.List;

public class Gallery {
    private String id;

    private List<Object> images;

    public Gallery() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }
}
