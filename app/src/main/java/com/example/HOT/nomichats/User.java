package com.example.HOT.nomichats;

/**
 * Created by KSHITIZ on 3/20/2018.
 * ------THIS CLASS IS FOR FETCHING A USER DATA-----
 */

public class User {
    public String name;
    public String status;
    public String image;
    public String thumbnail;
    User(){ }

    public User(String name, String status, String image, String thumbnail) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public String getName() { return name; }


    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbImage() {
        return thumbnail;
    }

    public void setThumbImage(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}
