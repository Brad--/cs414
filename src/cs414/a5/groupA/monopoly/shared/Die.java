package cs414.a5.groupA.monopoly.shared;

import cs414.a5.groupA.monopoly.shared.Token;

public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    public Die() {
    }

    public Token roll(Token player) {
        int rollOne = (int)(6.0*Math.random())+1;
        int rollTwo = (int)(6.0*Math.random())+1;
        // handle speeding and stuff in here
        int sum = rollOne + rollTwo;
        player.updateDieRoll(rollOne, rollTwo);
        if (player.inJail()){
            if( rollOne == rollTwo) { // they get out of jail
                player.getOutofJail();
                player.setCurrentPosition(11);
                return player; // this will leave them at the jail tile and they can go next round
            }
        }
        if (rollOne == rollTwo && speeding(player)) {
            player.goToJail();
            player.setCurrentPosition(11);
            return player;
        }
        else if (rollOne != rollTwo) {
            player.resetSpeed();
            if (checkGoToJailSpace(player, sum)){
                player.goToJail();
                player.setCurrentPosition(11);
            }
            else
                player.updatePosition(sum);
        }
        else{
            if (checkGoToJailSpace(player, sum)){
                player.goToJail();
                player.setCurrentPosition(11);
            }
            else
                player.updatePosition(sum);
        }
        return player;
    }

    public boolean speeding(Token player){
        int numDoubles = player.getSpeeding();
        if (numDoubles+1 > 2) {
            player.resetSpeed();
            return true;
        }
        player.incrementSpeed(1);
        return false;
    }

    public boolean checkGoToJailSpace(Token player, int sum){
        int position = player.getCurrentPosition();
        if (position+sum == 31){
            return true;
        }

        return false;
    }
}
