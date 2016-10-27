package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Token;

public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    public Die() {
    }

    public Token roll(Token player) {
        int rollOne = (int)(6.0*Math.random())+1;
        int rollTwo = (int)(6.0*Math.random())+1;
        // handle speeding and stuff in here
        int sum = rollOne + rollTwo;

        if (player.inJail()){
            if( rollOne == rollTwo) { // they get out of jail
                player.getOutofJail();
                player.setCurrentPosition(11);
                return player; // this will leave them at the jail tile and they can go next round
            }
        }
        if (rollOne == rollTwo && !speeding(player)) {
            player.goToJail();
            player.setCurrentPosition(11);
            return player;
        }
        else if (rollOne != rollTwo) {
            player.resetSpeed();
            int position = player.getCurrentPosition();
            if (position+sum == 31){
                player.goToJail();
                player.setCurrentPosition(11);
                return player;
            }
            else
                player.setCurrentPosition(position+sum);
        }
        return player;
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
