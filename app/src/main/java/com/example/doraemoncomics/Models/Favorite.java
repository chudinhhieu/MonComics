package com.example.doraemoncomics.Models;

import java.io.Serializable;

public class Favorite implements Serializable {
    private String _id,id_user,id_comic;

    public Favorite(String id_user, String id_comic) {
        this.id_user = id_user;
        this.id_comic = id_comic;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_comic() {
        return id_comic;
    }

    public void setId_comic(String id_comic) {
        this.id_comic = id_comic;
    }
}
