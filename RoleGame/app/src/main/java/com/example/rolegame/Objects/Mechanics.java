package com.example.rolegame.Objects;

import java.io.Serializable;

public class Mechanics extends Ability implements Serializable {

    //Mechanic types

    //type 1 = At least (number) in every game when role is selected.
    //There are many types each of them has his uniqueness.
    private int type;

    public Mechanics(int id, String name, String description, int type) {
        super(id, name, description, false, null, false);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
