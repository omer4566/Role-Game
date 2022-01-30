package com.example.rolegame.Objects;

import android.util.Log;

import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class GameClass {

    /* The node class is quite hard to explain but ill try my best:
    *  node is when there are 2 effects that when casted on the same player together something happens.
    *  for example: when a player is protected and someone tries to kill him, the "protected" effect
    *  saves the player from any harm.
    *  so basically node is two effects that are merged together.
    */
    static class node {
        int nodeId; //the Id of the node
        String independent; // the effect is independent and doesn't need another effect to work.
        String dependent; // the effect is dependent and it requires another effect to work.

        node (int nodeId ,String independent, String dependent) {
            this.nodeId = nodeId;
            this.independent = independent;
            this.dependent = dependent;
        }

        public String getDependent() {
            return dependent;
        }

        public void setDependent(String dependent) {
            this.dependent = dependent;
        }

        public int getNodeId() {
            return nodeId;
        }

        public void setNodeId(int nodeId) {
            this.nodeId = nodeId;
        }

        public String getIndependent() {
            return independent;
        }

        public void setIndependent(String independent) {
            this.independent = independent;
        }

        public boolean checkIndependent(String effect) {
            if (this.independent.equals(effect))
            {
                return true;
            }
            return false;
        }

        public boolean checkDependent(String effect) {
            if (this.dependent.equals(effect))
            {
                return true;
            }
            return false;
        }

    }

    //the game nodes.
    private static node [] nodes = {
            new node(1,"Death", "Protected"),
            new node(2,"Death", "Zombiefaction"),
            new node(3,"Death", null),
            new node(4,"Reborn", "Zombiefaction"),
            new node(5,"Reborn", null),
            new node(6,"Replace", "Nullified"),
            new node(7,"Replace", null),
            new node(8,"Flip", "Nullified"),
            new node(9,"Flip", null)
    };

    private static ArrayList<String> currentIGEffects; //The effects available in the current session.
    private static ArrayList<node> currentIGNodes; //The nodes available in the current session.

    private static ArrayList<String> independentEffects; //The effects that are independent in the current session.
    private static ArrayList<String> dependentEffects; //The effects that are dependent in the current session.

    public static void setCurrentIGEffects(ArrayList<Role> currentIGRoles) {

        currentIGNodes = new ArrayList<>();
        independentEffects = new ArrayList<>();
        dependentEffects = new ArrayList<>();

        ArrayList<Ability> abilities = new ArrayList<>();
        for (int i = 0; i < currentIGRoles.size(); i++) {
            Ability[] abilitiesArr = currentIGRoles.get(i).getAbilities();
            if (abilitiesArr != null) {
                for (int j = 0; j < abilitiesArr.length; j++) {
                    abilities.add(abilitiesArr[j]);
                }
            }
        }

        //removes ability duplicates.
        ArrayList<Ability> newAbilities = new ArrayList<>();
        for (Ability element : abilities) {
            if (!newAbilities.contains(element)) {
                newAbilities.add(element);
            }
        }

        //gets the effects
        ArrayList<String> effects = new ArrayList<>();
        for (Ability element : newAbilities) {
            effects.add(element.getEffect());
        }

        currentIGEffects = effects;

        //gets the nodes for the current session.
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < effects.size(); j++) {
                if (nodes[i].checkIndependent(effects.get(j))) {
                    independentEffects.add(effects.get(j));
                    for (int r = 0; r < effects.size(); r++) {
                        if (!currentIGNodes.contains(nodes[i]))
                        {
                            if (nodes[i].getDependent() != null) {
                                if (nodes[i].checkDependent(effects.get(r))) {
                                    dependentEffects.add(effects.get(r));
                                    currentIGNodes.add(nodes[i]);
                                }
                            } else if (nodes[i].getDependent() == null) {
                                currentIGNodes.add(nodes[i]);
                            }
                        }
                    }
                }
            }
        }

        String crin = " ";
        for (int i = 0; i < currentIGNodes.size(); i++){
            crin = crin + currentIGNodes.get(i).getIndependent() + " " + currentIGNodes.get(i).getDependent() + " | ";
        }

        Log.d( "session effects:", crin); //the current session's effects.

        //removes duplicates from indep and dep lists.
        ArrayList<String> newInDep = new ArrayList<>();
        ArrayList<String> newDep = new ArrayList<>();

        for (String element : independentEffects) {
            if (!newInDep.contains(element)) {
                newInDep.add(element);
            }
        }
        independentEffects = newInDep;

        for (String element : dependentEffects) {
            if (!newDep.contains(element)) {
                newDep.add(element);
            }
        }

        dependentEffects = newDep;
    }

    // a function that is used to check the player has any nodes active and if he does,
    // this function returns the methods needed.
    public static ArrayList <Integer> checkForNodes(ArrayList<String> activeEffects) {
        ArrayList<Integer> methods = new ArrayList<>();
        for (int i = 0; i < independentEffects.size(); i++) {
            if (activeEffects.contains(independentEffects.get(i))) {
                ArrayList<node> currentNodes = new ArrayList<>();
                for (int j = 0; j < currentIGNodes.size(); j++) {
                    if (currentIGNodes.get(j).checkIndependent(independentEffects.get(i))) {
                        currentNodes.add(currentIGNodes.get(j));
                    }
                }
                if (currentNodes.size() > 0) {
                    for (int j = 0; j < currentNodes.size(); j++) {
                        if (j == currentNodes.size()-1) {
                            methods.add(currentNodes.get(j).getNodeId());
                        }
                        for (int r = 0; r < dependentEffects.size(); r++) {
                            if (activeEffects.contains(dependentEffects.get(r)) && currentNodes.get(j).checkDependent(dependentEffects.get(r))) {
                                //return this specific node's method.
                                methods.add(currentNodes.get(j).getNodeId());

                                //finishing the loop
                                j = currentNodes.size();
                                j++;
                                r = dependentEffects.size();
                                r++;
                            }
                        }
                    }
                } else if (currentNodes.size() == 1) {
                    //only 1 node was found, which means it has no dependent in the current game, return effect method.
                    methods.add(currentNodes.get(0).getNodeId());

                }
            }
        }
        return methods;
    }
}
