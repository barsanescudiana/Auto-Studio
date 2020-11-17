package com.example.autostudio;

import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String userDisplayName;
    private String userEmail;
    private Uri userPhoto;

    public User(String userId, String userDisplayName, String userEmail, Uri userPhoto) {
        this.userId = userId;
        this.userDisplayName = userDisplayName;
        this.userEmail = userEmail;
        this.userPhoto = userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Uri getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Uri userPhoto) {
        this.userPhoto = userPhoto;
    }
}
