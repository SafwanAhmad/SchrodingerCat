package com.example.android.mobileprogrammingassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by safwanx on 11/25/17.
 */

public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Expose
    @SerializedName("name")

    private String name;

    @Expose
    @SerializedName("image")
    private String imageLink;

    @Expose
    @SerializedName("items")
    private List<String> items;
}
