package com.cs414.monopoly.shared;

import com.cs414.monopoly.Exceptions.HotelException;
import com.cs414.monopoly.Exceptions.HouseException;

/**
 * Created by bradley on 10/18/16.
 */
public class Railroad extends Deed {
    public Railroad(Board board, int position) {
        super(board, position);
        this.price = 200;
    }

    @Override
    public void addHouse() throws HouseException {
        throw new HouseException("You can't add houses to a railroad");
    }

    @Override
    public void addHotel() throws HotelException {
        throw new HotelException("You can't add a hotel to a railroad");
    }

    @Override
    public void changeOwnership(Token newOwner) {
        super.changeOwnership(newOwner);
        calcRent();
    }

    private void calcRent() {
        int numRailroads = 1; // board.getNumRailroads(owner)

        houseRent = 25 * (int)(Math.pow(2, (numRailroads - 1)));
    }
}
