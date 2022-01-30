package com.example.rolegame.Objects;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.rolegame.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GlobalClass extends Application {

    //a list of the game's abilities.
    private static final Ability [] ability = {
            new Ability(0,"kill", "This ability lets you kill another player.", true, "Death", false),
            new Ability(1,"protect", "This ability lets you protect another player from any attack in the same night.", true, "Protected", false),
            new Ability(2,"shield", "This ability protects you from any night attack 50% of the time", false, "Protected", false),
            new Ability(3,"zombie","This ability activates when you kill or revive another player, this will cause the player to become your role the next day.", false, "Zombiefaction", true),
            new Ability(4,"revive", "This ability lets you revive another player during the night, (he will be revived the next day).", true, "Reborn", false),
            new Ability(5,"invulnerable", "This ability makes you invulnerable to any attacks during the night.", false, "Protected", false),
            new Ability(6,"trustworthy","This ability makes it that you will remain alive when getting voted out.",false,"Protected",false),
            new Ability(7,"alignment flip", "This ability allows you to flip the team of another player.", true, "Flip", false),
            new Ability(8,"role exchange", "This ability allows you to exchange the role of another player with yours", true, "Replace", false),
            new Ability(9,"nullify", "This ability allows you to nullify non physical attacks",true,"Nullified", false)

    };

    // a list of the game's mechanics.
    private static final Mechanics [] mechanics = {
            new Mechanics(0,"At least 1", "At least 1 of this role is must in every game, but more can be adjusted.",1),
            new Mechanics(1,"At least 2", "At least 2 of this role is must in every game, but more can be adjusted.",1),
            new Mechanics(2,"1 per 3", "This role is adjusted to 1 player per 3 players playing.",2),
            new Mechanics(3,"1 per 4", "This role is adjusted to 1 player per 4 players playing.",2),
            new Mechanics(4,"1 per 5", "This role is adjusted to 1 player per 5 players playing.",2),
            new Mechanics(5,"1 per 6", "This role is adjusted to 1 player per 4 players playing.",2),
            new Mechanics(6,"Max 1", "Not more then 1 of this role can be adjusted to a player",3),
            new Mechanics(7,"Max 2", "Not more then 2 of this role can be adjusted to a player",3)
            //new Mechanics(8,"Night 2", "This role can use his ability on up to 2 different players that night", 4),
            //new Mechanics(9,"Night 3", "This role can use his ability on up to 3 different players that night", 4)
    };

    // a list of the mechanic types.
    private static final ArrayList<String> headMechanics = new ArrayList<>(Arrays.asList(
            "At least",
            "1 per",
            "Max"
            //"Night"
    ));

    // a list of the role Images
    private static final ArrayList <Integer> images = new ArrayList<>(Arrays.asList(
            R.drawable.role_img_1,
            R.drawable.role_img_2,
            R.drawable.role_img_3,
            R.drawable.role_img_4,
            R.drawable.role_img_5,
            R.drawable.role_img_6
    ));

    //The profiles that were chosen for the current game.
    private static ArrayList <Profile> profiles;

    public static ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public static void setProfiles(ArrayList<Profile> setProfiles) {
        profiles = setProfiles;
    }

    public static ArrayList<String> getHeadMechanics () {
        return headMechanics;
    }

    //get the role images
    public static ArrayList<Integer> getImages () {
        return images;
    }

    //get the ability array.
    public static Ability[] getAbility() {
        return ability;
    }

    //get the ability arrayList.
    public static ArrayList<Ability> getArrayList(){
        return new ArrayList<Ability>(Arrays.asList(ability));
    }

    //get the ability array.
    public static Mechanics[] getMechanics() {
        return mechanics;
    }

    //get the ability arrayList.
    public static ArrayList<Mechanics> getMechanicsArrayList(){
        return new ArrayList<Mechanics>(Arrays.asList(mechanics));
    }

    //convert the positions array to string to get into the database.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String convertIntToString(ArrayList<Integer> positions){
        String convert = positions.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        return convert;
    }

    //convert String to array.
    public static ArrayList<Integer> convertStringToArray(String string){
        ArrayList<Integer> positions = new ArrayList<>();
        if (string == null)
        {
            return positions = null;
        }
        else
        {
            String[] intoArr = string.split(",");
            for (int i = 0; i < intoArr.length; i++)
            {
                positions.add(Integer.parseInt(intoArr[i]));
            }
            return positions;
        }
    }
}
