package com.example.android.mobileprogrammingassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by safwanx on 11/25/17.
 */

public class Data {

    @SerializedName("users")
    @Expose
    private List<User> users = null;

    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

}