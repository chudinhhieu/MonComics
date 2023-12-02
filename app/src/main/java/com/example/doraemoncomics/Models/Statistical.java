package com.example.doraemoncomics.Models;

public class Statistical {
    private String _id;
    private int count;

    public Statistical(String _id, int count) {
        this._id = _id;
        this.count = count;
    }

    public Statistical() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
