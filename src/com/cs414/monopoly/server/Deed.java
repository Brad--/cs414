package com.cs414.monopoly.server;

class Deed {
    // Purchase cost
    private int price;
    // Cost paid by players who do not own the space
    private int rent;
    private int numHouses;
    private int numHotels;
    private String owner; // Change this to Token when that exists

    Deed() {

    }

    public double calcRent() {
        return 0.0;
    }

    public void action() {
        // calculate rent, probably some internals
    }
}
