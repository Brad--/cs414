package cs414.a5.groupA.monopoly.server;


public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    public Die() {
    }

    public int roll() {

        int rollOne = (int)(6.0*Math.random())+1;
        return rollOne;
    }

    public boolean checkForDoubles(int one, int two){
        return one == two;
    }

}
