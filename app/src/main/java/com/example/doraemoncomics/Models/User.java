package com.example.doraemoncomics.Models;

import java.io.Serializable;

public class User implements Serializable {
    private String _id;
    private String fullname;
    private String password;
    private String email;
    private String username;
    private String avatar;

    public User() {
    }

    public User(String _id, String fullname, String password, String email, String username, String avatar) {
        this._id = _id;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.avatar = avatar;
    }
    public User(String fullname, String password, String email, String username, String avatar) {
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.avatar = avatar;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
