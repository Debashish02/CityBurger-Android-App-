package com.example.foodorderingapp.Activity;

public class UserModel {
    private String name, mobile, email, review;


    public UserModel(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public UserModel(String name, String email, String password, String review) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel(String name, String mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;

    }
}
