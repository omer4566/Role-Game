package com.example.rolegame.Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Role implements Serializable {

    private int id; //The Role ID
    private String name; //The name of the role
    private int team; //The team that the role belongs to (good, evil...)
    private Ability [] abilities; // abilities that the role has.
    private ArrayList<Mechanics> mechanics; // mechanics that the role has.
    private int imagePosition; // the number of the image that the role has.
    private boolean isChosen; // to check if the profile is active.
    private String description; // the role's description

    public Role(int id, String name, int team, Ability[] abilities, ArrayList<Mechanics> mechanics ,int imagePosition, String description, boolean isChosen) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.abilities = abilities;
        this.mechanics = mechanics;
        this.imagePosition = imagePosition;
        this.description = description;
        this.isChosen = isChosen;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(int imagePosition) {
        this.imagePosition = imagePosition;
    }

    public ArrayList<Mechanics> getMechanics() {
        return mechanics;
    }

    public void setMechanics(ArrayList<Mechanics> mechanics) {
        this.mechanics = mechanics;
    }

    public void addMechanic(Mechanics mechanic) {
        mechanics.add(mechanic);
    }

    public Ability[] getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
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
}
