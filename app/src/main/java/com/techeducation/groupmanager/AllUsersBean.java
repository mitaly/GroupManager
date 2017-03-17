package com.techeducation.groupmanager;

/**
 * Created by mitaly on 5/3/17.
 */

public class AllUsersBean {
    int profilePhoto;
    String name;

    public AllUsersBean(int profilePhoto, String name) {
        this.profilePhoto = profilePhoto;
        this.name = name;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
