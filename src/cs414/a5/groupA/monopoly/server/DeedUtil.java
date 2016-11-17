package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.server.exception.HotelException;
import cs414.a5.groupA.monopoly.server.exception.HouseException;
import cs414.a5.groupA.monopoly.shared.Token;

import static cs414.a5.groupA.monopoly.server.PropertyGroup.BLUE;
import static cs414.a5.groupA.monopoly.server.PropertyGroup.BROWN;
import static cs414.a5.groupA.monopoly.server.PropertyGroup.RAILROAD;

public class DeedUtil {

    public void addHouse(Deed d) throws HouseException {
        if(d.getNumHouses() == 4)
            throw new HouseException("You can't add another house");
        if(d.getNumHouses() < 4) {
            d.setNumHouses(d.getNumHouses() + 1);
            d.calcRent();
        }
    }

    public void addHotel(Deed d) throws HotelException {
        if(d.hasHotel())
            throw new HotelException("You can't add another hotel");
        if(d.getNumHouses() != 4)
            throw new HotelException("You can't add a hotel until you have 4 houses");

        d.setHasHotel(true);
        d.setNumHouses(0);
        d.calcRent();
    }

    public void changeOwnership(Deed d, Token newOwner) {
        d.setOwner(newOwner);
        d.calcRent();
    }

    protected boolean hasPropertyGroupMonopoly(Deed d, Board board) {
        // This will check against itself, so start at 0
        if(d.getOwner() == null)
            return false;
        int numProperties = 0;
        for(Deed curr : board.getOwnedDeeds(d.getOwner()))
            if(d.getPropertyGroup() == curr.getPropertyGroup())
                numProperties++;

        int totalProperties;
        if(d.getPropertyGroup() == BROWN || d.getPropertyGroup() == BLUE)
            totalProperties = 2;
        else if(d.getPropertyGroup() == RAILROAD)
            totalProperties = 4;
        else
            totalProperties = 3;

        if(numProperties == totalProperties)
            return true;
        else
            return false;
    }

    // Returns the mortgage value of the property
    public void mortgageProperty(Deed d) {
        if(d.getOwner() != null) {
//            d.getOwner().earnRent(d.getPrice() / 2);
            d.setOwner(null);
        }
    }

    public void action(Deed d, Token target) {
//        target.payRent(d.getRent());
//        d.getOwner().earnRent(d.getRent());
    }
}
