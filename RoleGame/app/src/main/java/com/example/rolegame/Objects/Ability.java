package com.example.rolegame.Objects;

import java.io.Serializable;

public class Ability implements Serializable {

    private int id; //ID for the ability
    private String name; //The name of the ability
    private String description; //what the ability does.
    private boolean active; // the type of the ability (Active / Passive)
    private boolean checked; // a bool that checks if the user had chosen this ability.
    private String effect; /// the effect of the ability (what the ability does in game)
    private boolean sideEffect; // a bool that checks if the ability is a sideEffect or just an effect.

    public Ability(int id, String name, String description, boolean active, String effect, boolean sideEffect) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.checked = false;
        this.effect = effect;
        this.sideEffect = sideEffect;
    }

    public boolean isSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(boolean sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean compatible) {
        this.active = compatible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
