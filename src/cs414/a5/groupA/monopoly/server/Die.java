package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;


public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    public Die() {
    }

    public int roll() {

        int rollOne = (int)(6.0*Math.random())+1;
        return rollOne;
        // handle speeding and stuff in here
//        int sum = rollOne + rollTwo;
//        player.setPosition(rollOne, rollTwo);
//        if (player.inJail()){
//            if( rollOne == rollTwo) { // they get out of jail
//                player.getOutofJail();
//                player.setPosition(11);
//                return player; // this will leave them at the jail tile and they can go next round
//            }
//        }
//        if (rollOne == rollTwo && speeding(player)) {
//            player.goToJail();
//            player.setPosition(11);
//            return player;
//        }
//        else if (rollOne != rollTwo) {
//            player.resetSpeed();
//            if (checkGoToJailSpace(player, sum)){
//                player.goToJail();
//                player.setPosition(11);
//            }
//            else
//                player.setPosition(sum);
//        }
//        else{
//            if (checkGoToJailSpace(player, sum)){
//                player.goToJail();
//                player.setPosition(11);
//            }
//            else
//                player.setPosition(sum);
//        }

//        int rollOne = (int)(6.0*Math.random())+1;
//        int rollTwo = (int)(6.0*Math.random())+1;
//        // handle speeding and stuff in here
//        int sum = rollOne + rollTwo;
//        player.updateDieRoll(rollOne, rollTwo);
//        if (player.getJailStatus()){
//            if( rollOne == rollTwo) { // they get out of jail
//                player.setOutofJail();
//                player.setPosition(11);
//                return player; // this will leave them at the jail tile and they can go next round
//            }
//        }
//        if (rollOne == rollTwo && speeding(player)) {
//            player.setPosition(10);
//            player.setPosition(11);
//            return player;
//        }
//        else if (rollOne != rollTwo) {
////            player.resetSpeed();
//            if (checkGoToJailSpace(player, sum)){
//                player.setPosition(10);
//                player.setPosition(11);
//            }
//            else
//                player.setPosition(sum);
//        }
//        else{
//            if (checkGoToJailSpace(player, sum)){
//                player.setPosition(10);
//                player.setPosition(11);
//            }
//            else
//                player.setPosition(sum);
//        }
//        return player;
    }

    public boolean checkForDoubles(int one, int two){
        return one == two;
    }

    public boolean speeding(Token player){
//        int numDoubles = player.getSpeeding();
//        if (numDoubles+1 > 2) {
//            player.resetSpeed();
//            return true;
//        }
//        player.incrementSpeed(1);

//        int numDoubles = player.getSpeeding();
//        if (numDoubles+1 > 2) {
//            player.resetSpeed();
//            return true;
//        }
//        player.setSpeed();

        return false;
    }

    public boolean checkGoToJailSpace(Token player, int sum){
        int position = player.getPosition();
        if (position+sum == 31){
            return true;
        }

        return false;
    }
}
