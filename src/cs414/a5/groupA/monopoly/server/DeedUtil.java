package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.server.exception.HouseException;
import cs414.a5.groupA.monopoly.shared.Token;

import static cs414.a5.groupA.monopoly.server.PropertyGroup.BLUE;
import static cs414.a5.groupA.monopoly.server.PropertyGroup.BROWN;
import static cs414.a5.groupA.monopoly.server.PropertyGroup.RAILROAD;

public class DeedUtil {

    public void increaseHousingCount(Deed d) throws HouseException {
        if(d.getHousingCount() < 5) {
            d.setHousingCount(d.getHousingCount() + 1);
            d.calcRent();
        } else {
        	throw new HouseException("You are at max housing");
        }
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
    
    public static String getDeedHexColorByStringPropertyGroup(String propertyGroup) {
    	String hexColor = "#000000";
    	if(propertyGroup != null) {
	    	switch(propertyGroup) {
			case "BROWN":
				hexColor = "#955436";
				break;
			case "LIGHTBLUE":
				hexColor = "#a9e1fc";
				break;
			case "PURPLE":
				hexColor = "#d93c97";
				break;
			case "ORANGE":
				hexColor = "#f7921c";
				break;
			case "RED":
				hexColor = "#ed1b24";
				break;
			case "YELLOW":
				hexColor = "#fff000";
				break;
			case "GREEN":
				hexColor = "#00a650";
				break;
			case "BLUE":
				hexColor = "#0071bd";
				break;
			}
    	}
    	
    	return hexColor;
    }
}
