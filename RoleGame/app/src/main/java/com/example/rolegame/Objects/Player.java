package com.example.rolegame.Objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private Role role; // the player's role
    private Profile profile; // the player's profile
    private int turn; // the turn in the current game
    private boolean isAlive; // a bool to check if the player is alive
    private ArrayList<String> activeEffect; // the effects of the current player
    private ArrayList<Ability> passiveAbilities; // the passive abilities of the current player
    private ArrayList<Player> effectGiver; // an ArrayList to save the player that gave effect to the player
    private String nightText; // the text shown at night if something unique happened
    private int votes; // the current amount of votes the player has

    public Player(Role role, Profile profile, int turn, boolean isAlive, ArrayList<String> activeEffect, ArrayList<Ability> passiveAbilities) {
        this.role = role;
        this.profile = profile;
        this.turn = turn;
        this.isAlive = isAlive;
        this.activeEffect = activeEffect;
        this.passiveAbilities = passiveAbilities;
        this.effectGiver = new ArrayList<>();
        this.nightText = "";
        this.votes = 0;
    }

    public int getVotes() {
        return votes;
    }

    public void resetVotes() {
        this.votes = 0;
    }
    public void addVotes(int amount) {
        this.votes += amount;
    }

    public String getNightText() {
        return nightText;
    }

    public void setNightText(String nightText) {
        this.nightText = nightText;
    }

    public ArrayList<Player> getEffectGiver() {
        return effectGiver;
    }

    public void setEffectGiver(ArrayList<Player> effectGiver) {
        this.effectGiver = effectGiver;
    }

    public void addEffectGiver(Player player) {
        this.effectGiver.add(player);
    }

    public void clearEffects(){
        activeEffect.clear();
        effectGiver.clear();
    }

    public ArrayList<Ability> getPassiveAbilities() {
        return passiveAbilities;
    }

    public void addPassiveAbility(Ability ability) {
        this.passiveAbilities.add(ability);
    }

    public void setPassiveAbilities(ArrayList<Ability> passiveAbilities) {
        this.passiveAbilities = passiveAbilities;
    }

    public ArrayList<String> getActiveEffect() {
        return activeEffect;
    }

    public void setActiveEffect(ArrayList<String> activeEffect) {
        this.activeEffect = activeEffect;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
