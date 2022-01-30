package com.example.rolegame.Objects;

import java.io.Serializable;

public class Profile implements Serializable {

    private int id; //the profile ID
    private String name; //The name of the profile
    private String profileUri; //The Uri of the profile image.
    private boolean isChosen; //To check if the profile is active.
    private boolean bool; //used to save a bool.

    public Profile(int id, String name, String profileUri, boolean isChosen) {
        this.id = id;
        this.name = name;
        this.profileUri = profileUri;
        this.isChosen = isChosen;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }
}
