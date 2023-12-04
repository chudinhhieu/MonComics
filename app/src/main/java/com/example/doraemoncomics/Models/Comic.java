package com.example.doraemoncomics.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comic implements Serializable {

    private String _id;
    private String name;
    private String idGenre;
    private String description;
    private Date publicationDate;
    private String author;
    private String linkCM;
    private String coverImage;
    private List<String> contentImage;

    public Comic(String _id, String name, String idGenre, String description, Date publicationDate, String author, String linkCM, String coverImage, List<String> contentImage) {
        this._id = _id;
        this.name = name;
        this.idGenre = idGenre;
        this.description = description;
        this.publicationDate = publicationDate;
        this.author = author;
        this.linkCM = linkCM;
        this.coverImage = coverImage;
        this.contentImage = contentImage;
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

    public String getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(String idGenre) {
        this.idGenre = idGenre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLinkCM() {
        return linkCM;
    }

    public void setLinkCM(String linkCM) {
        this.linkCM = linkCM;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getContentImage() {
        return contentImage;
    }

    public void setContentImage(List<String> contentImage) {
        this.contentImage = contentImage;
    }
}
