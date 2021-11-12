package com.example.websave.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Images {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Image_Id")
    private int id;

    public String getImage_txt() {
        return image_txt;
    }

    public void setImage_txt(String image_txt) {
        this.image_txt = image_txt;
    }

    private String image_txt;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

    public String getUrlthumbnail() {
        return urlthumbnail;
    }

    public void setUrlthumbnail(String urlthumbnail) {
        this.urlthumbnail = urlthumbnail;
    }

    String urlthumbnail;

    @ColumnInfo(name = "Image_List",typeAffinity = ColumnInfo.BLOB)
    private byte[] images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
