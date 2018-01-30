package com.master.math.activity.model;

import java.io.Serializable;

/**
 * Created by Laher on 1/17/2018.
 */

public class BaseModel implements Serializable {
    private String id;
    private boolean isActive;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isActive() {
        return true;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
