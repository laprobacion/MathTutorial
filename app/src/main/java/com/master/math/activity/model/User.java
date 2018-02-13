package com.master.math.activity.model;

public class User extends BaseModel{
    private String username;
    private String password;
    private String age;
    private String gender;
    private boolean isAdmin;
    private int score;

    public int getScore() {        return score;    }
    public void setScore(int score) {        this.score = score;    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) { this.gender = gender; }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
