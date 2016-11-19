package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.server.exception.HouseException;
import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.Token;

import static cs414.a5.groupA.monopoly.server.PropertyGroup.BLUE;
import static cs414.a5.groupA.monopoly.server.PropertyGroup.BROWN;
import static cs414.a5.groupA.monopoly.server.PropertyGroup.RAILROAD;

public class DeedUtil {

    public void increaseHousingCount(Deed d) throws HouseException {
        if(d.getHousingCount() < 5) {
            d.setHousingCount(d.getHousingCount() + 1);
            calcRent(d);
        } else {
        	throw new HouseException("You are at max housing");
        }
    }

    public void changeOwnership(Deed d, Token newOwner) {
        d.setOwner(newOwner);
        calcRent(d);
    }
    
    public static int calcRent(DatabaseDeed deed) {
    	Deed oldStyle = new Deed(deed.getPosition());
    	oldStyle.setHousingCount(deed.getHousingCount());
    	return calcRent(oldStyle);
    }
    
    public static int calcRent(Deed deed) {
    	int houseRent = 0;
        switch(deed.getPosition()) {
            // start brown
            case 1:
                if(deed.hasHotel())
                    houseRent = 250;
                else if(deed.getHousingCount() == 0)
                    houseRent = 2;
                else if(deed.getHousingCount() == 1)
                    houseRent = 10;
                else if(deed.getHousingCount() == 2)
                    houseRent = 30;
                else if(deed.getHousingCount() == 3)
                    houseRent = 90;
                else if(deed.getHousingCount() == 4)
                    houseRent = 160;
                break;
            case 3:
                if(deed.hasHotel())
                    houseRent = 450;
                else if(deed.getHousingCount() == 0)
                    houseRent = 4;
                else if(deed.getHousingCount() == 1)
                    houseRent = 20;
                else if(deed.getHousingCount() == 2)
                    houseRent = 60;
                else if(deed.getHousingCount() == 3)
                    houseRent = 180;
                else if(deed.getHousingCount() == 4)
                    houseRent = 320;
                break;
            // start lightblue
            case 6:
                if(deed.hasHotel())
                    houseRent = 550;
                else if(deed.getHousingCount() == 0)
                    houseRent = 6;
                else if(deed.getHousingCount() == 1)
                    houseRent = 30;
                else if(deed.getHousingCount() == 2)
                    houseRent = 90;
                else if(deed.getHousingCount() == 3)
                    houseRent = 270;
                else if(deed.getHousingCount() == 4)
                    houseRent = 400;
                break;
            case 8:
                if(deed.hasHotel())
                    houseRent = 550;
                else if(deed.getHousingCount() == 0)
                    houseRent = 6;
                else if(deed.getHousingCount() == 1)
                    houseRent = 30;
                else if(deed.getHousingCount() == 2)
                    houseRent = 90;
                else if(deed.getHousingCount() == 3)
                    houseRent = 270;
                else if(deed.getHousingCount() == 4)
                    houseRent = 400;
                break;
            case 9:
                if(deed.hasHotel())
                    houseRent = 600;
                else if(deed.getHousingCount() == 0)
                    houseRent = 8;
                else if(deed.getHousingCount() == 1)
                    houseRent = 40;
                else if(deed.getHousingCount() == 2)
                    houseRent = 100;
                else if(deed.getHousingCount() == 3)
                    houseRent = 300;
                else if(deed.getHousingCount() == 4)
                    houseRent = 450;
                break;
            // start purple
            case 11:
                if(deed.hasHotel())
                    houseRent = 750;
                else if(deed.getHousingCount() == 0)
                    houseRent = 10;
                else if(deed.getHousingCount() == 1)
                    houseRent = 50;
                else if(deed.getHousingCount() == 2)
                    houseRent = 150;
                else if(deed.getHousingCount() == 3)
                    houseRent = 450;
                else if(deed.getHousingCount() == 4)
                    houseRent = 625;
                break;
            case 13:
                if(deed.hasHotel())
                    houseRent = 750;
                else if(deed.getHousingCount() == 0)
                    houseRent = 10;
                else if(deed.getHousingCount() == 1)
                    houseRent = 50;
                else if(deed.getHousingCount() == 2)
                    houseRent = 150;
                else if(deed.getHousingCount() == 3)
                    houseRent = 450;
                else if(deed.getHousingCount() == 4)
                    houseRent = 625;
                break;
            case 14:
                if(deed.hasHotel())
                    houseRent = 900;
                else if(deed.getHousingCount() == 0)
                    houseRent = 12;
                else if(deed.getHousingCount() == 1)
                    houseRent = 60;
                else if(deed.getHousingCount() == 2)
                    houseRent = 180;
                else if(deed.getHousingCount() == 3)
                    houseRent = 500;
                else if(deed.getHousingCount() == 4)
                    houseRent = 700;
                break;
            // start orange
            case 16:
                if(deed.hasHotel())
                    houseRent = 950;
                else if(deed.getHousingCount() == 0)
                    houseRent = 14;
                else if(deed.getHousingCount() == 1)
                    houseRent = 70;
                else if(deed.getHousingCount() == 2)
                    houseRent = 200;
                else if(deed.getHousingCount() == 3)
                    houseRent = 550;
                else if(deed.getHousingCount() == 4)
                    houseRent = 750;
                break;
            case 18:
                if(deed.hasHotel())
                    houseRent = 950;
                else if(deed.getHousingCount() == 0)
                    houseRent = 14;
                else if(deed.getHousingCount() == 1)
                    houseRent = 70;
                else if(deed.getHousingCount() == 2)
                    houseRent = 200;
                else if(deed.getHousingCount() == 3)
                    houseRent = 550;
                else if(deed.getHousingCount() == 4)
                    houseRent = 750;
                break;
            case 19:
                if(deed.hasHotel())
                    houseRent = 1000;
                else if(deed.getHousingCount() == 0)
                    houseRent = 16;
                else if(deed.getHousingCount() == 1)
                    houseRent = 80;
                else if(deed.getHousingCount() == 2)
                    houseRent = 220;
                else if(deed.getHousingCount() == 3)
                    houseRent = 600;
                else if(deed.getHousingCount() == 4)
                    houseRent = 800;
                break;
            // start red
            case 21:
                if(deed.hasHotel())
                    houseRent = 1050;
                else if(deed.getHousingCount() == 0)
                    houseRent = 18;
                else if(deed.getHousingCount() == 1)
                    houseRent = 90;
                else if(deed.getHousingCount() == 2)
                    houseRent = 250;
                else if(deed.getHousingCount() == 3)
                    houseRent = 700;
                else if(deed.getHousingCount() == 4)
                    houseRent = 875;
                break;
            case 23:
                if(deed.hasHotel())
                    houseRent = 1050;
                else if(deed.getHousingCount() == 0)
                    houseRent = 18;
                else if(deed.getHousingCount() == 1)
                    houseRent = 90;
                else if(deed.getHousingCount() == 2)
                    houseRent = 250;
                else if(deed.getHousingCount() == 3)
                    houseRent = 700;
                else if(deed.getHousingCount() == 4)
                    houseRent = 875;
                break;
            case 24:
                if(deed.hasHotel())
                    houseRent = 1100;
                else if(deed.getHousingCount() == 0)
                    houseRent = 20;
                else if(deed.getHousingCount() == 1)
                    houseRent = 100;
                else if(deed.getHousingCount() == 2)
                    houseRent = 300;
                else if(deed.getHousingCount() == 3)
                    houseRent = 750;
                else if(deed.getHousingCount() == 4)
                    houseRent = 925;
                break;
            // start yellow
            case 26:
                if(deed.hasHotel())
                    houseRent = 1150;
                else if(deed.getHousingCount() == 0)
                    houseRent = 22;
                else if(deed.getHousingCount() == 1)
                    houseRent = 110;
                else if(deed.getHousingCount() == 2)
                    houseRent = 330;
                else if(deed.getHousingCount() == 3)
                    houseRent = 800;
                else if(deed.getHousingCount() == 4)
                    houseRent = 975;
                break;
            case 27:
                if(deed.hasHotel())
                    houseRent = 1150;
                else if(deed.getHousingCount() == 0)
                    houseRent = 22;
                else if(deed.getHousingCount() == 1)
                    houseRent = 110;
                else if(deed.getHousingCount() == 2)
                    houseRent = 330;
                else if(deed.getHousingCount() == 3)
                    houseRent = 800;
                else if(deed.getHousingCount() == 4)
                    houseRent = 975;
                break;
            case 29:
                if(deed.hasHotel())
                    houseRent = 1200;
                else if(deed.getHousingCount() == 0)
                    houseRent = 24;
                else if(deed.getHousingCount() == 1)
                    houseRent = 120;
                else if(deed.getHousingCount() == 2)
                    houseRent = 360;
                else if(deed.getHousingCount() == 3)
                    houseRent = 850;
                else if(deed.getHousingCount() == 4)
                    houseRent = 1025;
                break;
            // start green
            case 31:
                if(deed.hasHotel())
                    houseRent = 1275;
                else if(deed.getHousingCount() == 0)
                    houseRent = 26;
                else if(deed.getHousingCount() == 1)
                    houseRent = 130;
                else if(deed.getHousingCount() == 2)
                    houseRent = 390;
                else if(deed.getHousingCount() == 3)
                    houseRent = 900;
                else if(deed.getHousingCount() == 4)
                    houseRent = 1100;
                break;
            case 32:
                if(deed.hasHotel())
                    houseRent = 1275;
                else if(deed.getHousingCount() == 0)
                    houseRent = 26;
                else if(deed.getHousingCount() == 1)
                    houseRent = 130;
                else if(deed.getHousingCount() == 2)
                    houseRent = 390;
                else if(deed.getHousingCount() == 3)
                    houseRent = 900;
                else if(deed.getHousingCount() == 4)
                    houseRent = 1100;
                break;
            case 34:
                if(deed.hasHotel())
                    houseRent = 1400;
                else if(deed.getHousingCount() == 0)
                    houseRent = 28;
                else if(deed.getHousingCount() == 1)
                    houseRent = 150;
                else if(deed.getHousingCount() == 2)
                    houseRent = 450;
                else if(deed.getHousingCount() == 3)
                    houseRent = 1000;
                else if(deed.getHousingCount() == 4)
                    houseRent = 1200;
                break;
            // start blue
            case 37:
                if(deed.hasHotel())
                    houseRent = 1500;
                else if(deed.getHousingCount() == 0)
                    houseRent = 35;
                else if(deed.getHousingCount() == 1)
                    houseRent = 175;
                else if(deed.getHousingCount() == 2)
                    houseRent = 500;
                else if(deed.getHousingCount() == 3)
                    houseRent = 1100;
                else if(deed.getHousingCount() == 4)
                    houseRent = 1300;
                break;
            case 39:
                if(deed.hasHotel())
                    houseRent = 2000;
                else if(deed.getHousingCount() == 0)
                    houseRent = 50;
                else if(deed.getHousingCount() == 1)
                    houseRent = 200;
                else if(deed.getHousingCount() == 2)
                    houseRent = 600;
                else if(deed.getHousingCount() == 3)
                    houseRent = 1400;
                else if(deed.getHousingCount() == 4)
                    houseRent = 1700;
                break;
            default:
//                System.err.println("This space shouldn't be generating rent here! Position: " + position );
                break;
        }
        return houseRent;

        //TODO: This is kind of impossible / convoluted now, we can do it later.
//        if(hasPropertyGroupMonopoly())
//            houseRent *= 2;
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
