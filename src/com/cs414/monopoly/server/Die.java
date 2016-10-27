package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Token;

public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    public Die() {
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
        if (rollOne == rollTwo && !speeding(player)) {
            player.goToJail();
            return 11;
        }
        else if (rollOne != rollTwo)
            player.resetSpeed();
        return sum;
    }

    public boolean speeding(Token player){
        int numDoubles = player.getSpeeding();
        if (numDoubles++ > 2) {
            player.resetSpeed();
            return false;
        }
        player.incrementSpeed(1);
        return true;
    }
}
