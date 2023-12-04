package com.example.doraemoncomics.Models;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private String _id,id_user,id_comic,noiDung;
    private Date thoiGian;

    public Comment(String _id, String id_user, String id_comic, String noiDung, Date thoiGian) {
        this._id = _id;
        this.id_user = id_user;
        this.id_comic = id_comic;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
    }

    public Comment(String id_user, String id_comic, String noiDung) {
        this.id_user = id_user;
        this.id_comic = id_comic;
        this.noiDung = noiDung;
    }

    public Comment(String noiDung) {
        this.noiDung = noiDung;
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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }
}
