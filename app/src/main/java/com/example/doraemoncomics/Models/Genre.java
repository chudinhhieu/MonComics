package com.example.doraemoncomics.Models;

import java.io.Serializable;

public class Genre implements Serializable {
    private String _id,name,description,image;

    public Genre() {
    }

    public Genre(String _id, String name, String description, String image) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
