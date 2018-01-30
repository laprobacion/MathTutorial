package com.master.math.activity.util;


import com.master.math.activity.model.User;

/**
 * Created by Laher on 1/20/2018.
 */

public class AppCache {
    public static AppCache _this;
    private boolean isPageOneDestroyed;

    public boolean isPageOneDestroyed() {
        return isPageOneDestroyed;
    }

    public void setPageOneDestroyed(boolean pageOneDestroyed) {
        isPageOneDestroyed = pageOneDestroyed;
    }

    private User user;
    private String username;

    public static AppCache getInstance(){
        if(_this == null){
            _this = new AppCache();
        }
        return _this;
    }

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return  this.user;
    }

    public void setUsername(String username) {this.username = username; }
    public String getUsername() { return  this.username; }
}
