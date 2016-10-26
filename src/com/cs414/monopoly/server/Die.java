package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Token;

import java.util.HashMap;

public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    HashMap<Token, int[]> userMap;
    public Die() {
        userMap = new HashMap<>();
    }

    public int roll(Token player) {
        int rollOne = (int)(6.0*Math.random())+1;
        int rollTwo = (int)(6.0*Math.random())+1;
        // handle speeding and stuff in here
        int sum = rollOne + rollTwo;

        if (player.inJail()){
            if( rollOne == rollTwo) { // they get out of jail
                player.getOutofJail();
                return 0; // this will leave them at the jail tile and they can go next round
            }
        }
        if (rollOne == rollTwo && !speeding(player,rollOne, rollTwo)) {
            player.goToJail();
            return 11;
        }
        return sum;
    }

    public boolean speeding(Token player, int rollOne, int rollTwo){
        int [] rolls = userMap.get(player);
        // so we will know where to add
        // first roll
        int fOne = rolls[1];
        int fTwo = rolls[2];
        // second roll
        int sOne = rolls[3];
        int sTwo = rolls[4];
        if (rollOne == fOne && rollOne == fTwo && sOne == rollOne && sTwo == rollOne && !player.inJail()) {
            rolls[0] = 0;
            rolls[1] = 0;
            rolls[2] = 0;
            rolls[3] = 0;
            rolls[4] = 0;
            userMap.put(player,rolls); // reset their rolls they are going to jail!
            return false;
        }

        int numRolls = rolls[0];
        if (numRolls ==0){
            rolls[0] = 1;
            rolls[1] = rollOne;
            rolls[2] = rollTwo;
        }
        else if (numRolls ==1){
            rolls[0] =0;
            rolls[3] = rollOne;
            rolls[4] = rollTwo;
        }
        userMap.put(player,rolls);
        return true;
    }
}
