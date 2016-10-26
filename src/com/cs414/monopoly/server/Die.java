package com.cs414.monopoly.server;

public class Die {
    // Last 2 rolls data structure is kind of up to interpretation, so I ain't gon' skeleton that
    public Die() {    }

    public int roll() {
        // handle speeding and stuff in here
        int rollOne = (int)(6.0*Math.random())+1;
        int rollTwo = (int)(6.0*Math.random())+1;
        return (rollOne+rollTwo);
    }
}
