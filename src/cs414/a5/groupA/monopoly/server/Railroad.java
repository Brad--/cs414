package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.server.exception.HotelException;
import cs414.a5.groupA.monopoly.server.exception.HouseException;

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

    @Override
    protected void calcRent() {
        int numRailroads = 1; // board.getNumRailroads(owner)

        houseRent = 25 * (int)(Math.pow(2, (numRailroads - 1)));
    }
}
