package cs414.a5.groupA.monopoly.server;

import static cs414.a5.groupA.monopoly.server.PropertyGroup.*;

import com.google.gwt.core.shared.GWT;

import cs414.a5.groupA.monopoly.server.exception.HotelException;
import cs414.a5.groupA.monopoly.server.exception.HouseException;
import cs414.a5.groupA.monopoly.shared.Token;

public class Deed extends Space{
    protected int price;
    protected int houseRent;
    private int numHouses;
    private boolean hasHotel;
    protected PropertyGroup propertyGroup;
    
    public Deed(int position) {
        super(position);
        calcPriceAndPropertyGroup();
        calcRent();
        setName();
        hasHotel  = false;
        numHouses = 0;

    }

    public boolean hasHotel() {
        return hasHotel;
    }

    public void setHasHotel(boolean b) {
        hasHotel = b;
    }

    public int getNumHouses() {
        return numHouses;
    }

    public void setNumHouses(int a) {
        numHouses = a;
    }

    public Token getOwner() {
        return owner;
    }

    public void setOwner(Token t) {
        owner = t;
    }

    public int getRent() {
        return houseRent;
    }

    // Returns the mortgage value of the property
    public void mortgageProperty() {
    	// GD 11.15.16 Needs redone after token refactor
//        if(owner != null) {
//            owner.earnRent(price / 2);
//            owner = null;
//        }
    }

//    @Override
//    public void action(Token target) {
//    	// GD 11.15.16 Needs redone after token refactor
////        target.payRent(houseRent);
////        owner.earnRent(houseRent);
//    }
    public int getPrice() {
        return price;
    }

    public PropertyGroup getPropertyGroup() {
        return propertyGroup;

    }

    protected void setName() {
        switch(position) {
            case 0:
                setName("Go");
                break;
            case 1:
                setName("Bean Trees");
                break;
            case 2:
                setName("Community Chest");
                break;
            case 3:
                setName("Blackbird");
                break;
            case 4:
                setName("CSU Stadium Tax");
                break;
            case 5:
                setName("Laurel Station");
                break;
            case 6:
                setName("Cuppy's");
                break;
            case 7:
                setName("Chance");
                break;
            case 8:
                setName("Little Bird");
                break;
            case 9:
                setName("Bindle");
                break;
            case 10:
                setName("Jail");
                break;
            case 11:
                setName("Momo Lolo");
                break;
            case 12:
                setName("Electric");
                break;
            case 13:
                setName("Morgan's Grind");
                break;
            case 14:
                setName("Sweet Sinsations");
                break;
            case 15:
                setName("Mulberry Station");
                break;
            case 16:
                setName("FoCo Coffee House");
                break;
            case 17:
                setName("Community Chest");
                break;
            case 18:
                setName("Wild Board");
                break;
            case 19:
                setName("Alley Cat");
                break;
            case 20:
                setName("Free Parking");
                break;
            case 21:
                setName("Bean Cycle");
                break;
            case 22:
                setName("Chance");
                break;
            case 23:
                setName("Human Bean");
                break;
            case 24:
                setName("Daz Bog");
                break;
            case 25:
                setName("Olive Station");
                break;
            case 26:
                setName("Cups Coffee");
                break;
            case 27:
                setName("Everyday Joe's");
                break;
            case 28:
                setName("Utilities");
                break;
            case 29:
                setName("Harbinger Coffee");
                break;
            case 30:
                setName("Go to Jail");
                break;
            case 31:
                setName("Crooked Cup");
                break;
            case 32:
                setName("Starry Night");
                break;
            case 33:
                setName("Community Chest");
                break;
            case 34:
                setName("Mugs");
                break;
            case 35:
                setName("Mountain Station");
                break;
            case 36:
                setName("Chance");
                break;
            case 37:
                setName("Dunkin Donuts");
                break;
            case 38:
                setName("Luxury Tax");
                break;
            case 39:
                setName("Starbucks");
                break;
            default:
                System.out.println("You're using the Deed constructor wrong. This is from the name init.");

        }
    }

    // Sets the price based on position
    protected void calcPriceAndPropertyGroup() {
        if(position == 1 || position == 3) {
            price = 60;
            propertyGroup = BROWN;
        }
        else if(position == 6 || position == 8) {
            price = 100;
            propertyGroup = LIGHTBLUE;
        }
        else if(position == 9) {
            price = 120;
            propertyGroup = LIGHTBLUE;
        }
        else if(position == 11 || position == 13) {
            price = 140;
            propertyGroup = PURPLE;
        }
        else if(position == 14) {
            price = 160;
            propertyGroup = PURPLE;
        }
        else if(position == 16 || position == 18) {
            price = 180;
            propertyGroup = ORANGE;
        }
        else if(position == 19) {
            price = 200;
            propertyGroup = ORANGE;
        }
        else if(position == 21 || position == 23) {
            price = 220;
            propertyGroup = RED;
        }
        else if(position == 24) {
            price = 240;
            propertyGroup = RED;
        }
        else if(position == 26 || position == 27) {
            price = 260;
            propertyGroup = YELLOW;
        }
        else if(position == 29) {
            price = 280;
            propertyGroup = YELLOW;
        }
        else if(position == 31 || position == 32) {
            price = 300;
            propertyGroup = GREEN;
        }
        else if(position == 34) {
            price = 320;
            propertyGroup = GREEN;
        }
        else if(position == 37) {
            price = 350;
            propertyGroup = BLUE;
        }
        else if(position == 39){
            price = 400;
            propertyGroup = BLUE;
        }
    }

    // This is messy, but there isn't really a method to the pricing
    protected void calcRent() {
        switch(position) {
            // start brown
            case 1:
                if(hasHotel)
                    houseRent = 250;
                else if(numHouses == 0)
                    houseRent = 2;
                else if(numHouses == 1)
                    houseRent = 10;
                else if(numHouses == 2)
                    houseRent = 30;
                else if(numHouses == 3)
                    houseRent = 90;
                else if(numHouses == 4)
                    houseRent = 160;
                break;
            case 3:
                if(hasHotel)
                    houseRent = 450;
                else if(numHouses == 0)
                    houseRent = 4;
                else if(numHouses == 1)
                    houseRent = 20;
                else if(numHouses == 2)
                    houseRent = 60;
                else if(numHouses == 3)
                    houseRent = 180;
                else if(numHouses == 4)
                    houseRent = 320;
                break;
            // start lightblue
            case 6:
                if(hasHotel)
                    houseRent = 550;
                else if(numHouses == 0)
                    houseRent = 6;
                else if(numHouses == 1)
                    houseRent = 30;
                else if(numHouses == 2)
                    houseRent = 90;
                else if(numHouses == 3)
                    houseRent = 270;
                else if(numHouses == 4)
                    houseRent = 400;
                break;
            case 8:
                if(hasHotel)
                    houseRent = 550;
                else if(numHouses == 0)
                    houseRent = 6;
                else if(numHouses == 1)
                    houseRent = 30;
                else if(numHouses == 2)
                    houseRent = 90;
                else if(numHouses == 3)
                    houseRent = 270;
                else if(numHouses == 4)
                    houseRent = 400;
                break;
            case 9:
                if(hasHotel)
                    houseRent = 600;
                else if(numHouses == 0)
                    houseRent = 8;
                else if(numHouses == 1)
                    houseRent = 40;
                else if(numHouses == 2)
                    houseRent = 100;
                else if(numHouses == 3)
                    houseRent = 300;
                else if(numHouses == 4)
                    houseRent = 450;
                break;
            // start purple
            case 11:
                if(hasHotel)
                    houseRent = 750;
                else if(numHouses == 0)
                    houseRent = 10;
                else if(numHouses == 1)
                    houseRent = 50;
                else if(numHouses == 2)
                    houseRent = 150;
                else if(numHouses == 3)
                    houseRent = 450;
                else if(numHouses == 4)
                    houseRent = 625;
                break;
            case 13:
                if(hasHotel)
                    houseRent = 750;
                else if(numHouses == 0)
                    houseRent = 10;
                else if(numHouses == 1)
                    houseRent = 50;
                else if(numHouses == 2)
                    houseRent = 150;
                else if(numHouses == 3)
                    houseRent = 450;
                else if(numHouses == 4)
                    houseRent = 625;
                break;
            case 14:
                if(hasHotel)
                    houseRent = 900;
                else if(numHouses == 0)
                    houseRent = 12;
                else if(numHouses == 1)
                    houseRent = 60;
                else if(numHouses == 2)
                    houseRent = 180;
                else if(numHouses == 3)
                    houseRent = 500;
                else if(numHouses == 4)
                    houseRent = 700;
                break;
            // start orange
            case 16:
                if(hasHotel)
                    houseRent = 950;
                else if(numHouses == 0)
                    houseRent = 14;
                else if(numHouses == 1)
                    houseRent = 70;
                else if(numHouses == 2)
                    houseRent = 200;
                else if(numHouses == 3)
                    houseRent = 550;
                else if(numHouses == 4)
                    houseRent = 750;
                break;
            case 18:
                if(hasHotel)
                    houseRent = 950;
                else if(numHouses == 0)
                    houseRent = 14;
                else if(numHouses == 1)
                    houseRent = 70;
                else if(numHouses == 2)
                    houseRent = 200;
                else if(numHouses == 3)
                    houseRent = 550;
                else if(numHouses == 4)
                    houseRent = 750;
                break;
            case 19:
                if(hasHotel)
                    houseRent = 1000;
                else if(numHouses == 0)
                    houseRent = 16;
                else if(numHouses == 1)
                    houseRent = 80;
                else if(numHouses == 2)
                    houseRent = 220;
                else if(numHouses == 3)
                    houseRent = 600;
                else if(numHouses == 4)
                    houseRent = 800;
                break;
            // start red
            case 21:
                if(hasHotel)
                    houseRent = 1050;
                else if(numHouses == 0)
                    houseRent = 18;
                else if(numHouses == 1)
                    houseRent = 90;
                else if(numHouses == 2)
                    houseRent = 250;
                else if(numHouses == 3)
                    houseRent = 700;
                else if(numHouses == 4)
                    houseRent = 875;
                break;
            case 23:
                if(hasHotel)
                    houseRent = 1050;
                else if(numHouses == 0)
                    houseRent = 18;
                else if(numHouses == 1)
                    houseRent = 90;
                else if(numHouses == 2)
                    houseRent = 250;
                else if(numHouses == 3)
                    houseRent = 700;
                else if(numHouses == 4)
                    houseRent = 875;
                break;
            case 24:
                if(hasHotel)
                    houseRent = 1100;
                else if(numHouses == 0)
                    houseRent = 20;
                else if(numHouses == 1)
                    houseRent = 100;
                else if(numHouses == 2)
                    houseRent = 300;
                else if(numHouses == 3)
                    houseRent = 750;
                else if(numHouses == 4)
                    houseRent = 925;
                break;
            // start yellow
            case 26:
                if(hasHotel)
                    houseRent = 1150;
                else if(numHouses == 0)
                    houseRent = 22;
                else if(numHouses == 1)
                    houseRent = 110;
                else if(numHouses == 2)
                    houseRent = 330;
                else if(numHouses == 3)
                    houseRent = 800;
                else if(numHouses == 4)
                    houseRent = 975;
                break;
            case 27:
                if(hasHotel)
                    houseRent = 1150;
                else if(numHouses == 0)
                    houseRent = 22;
                else if(numHouses == 1)
                    houseRent = 110;
                else if(numHouses == 2)
                    houseRent = 330;
                else if(numHouses == 3)
                    houseRent = 800;
                else if(numHouses == 4)
                    houseRent = 975;
                break;
            case 29:
                if(hasHotel)
                    houseRent = 1200;
                else if(numHouses == 0)
                    houseRent = 24;
                else if(numHouses == 1)
                    houseRent = 120;
                else if(numHouses == 2)
                    houseRent = 360;
                else if(numHouses == 3)
                    houseRent = 850;
                else if(numHouses == 4)
                    houseRent = 1025;
                break;
            // start green
            case 31:
                if(hasHotel)
                    houseRent = 1275;
                else if(numHouses == 0)
                    houseRent = 26;
                else if(numHouses == 1)
                    houseRent = 130;
                else if(numHouses == 2)
                    houseRent = 390;
                else if(numHouses == 3)
                    houseRent = 900;
                else if(numHouses == 4)
                    houseRent = 1100;
                break;
            case 32:
                if(hasHotel)
                    houseRent = 1275;
                else if(numHouses == 0)
                    houseRent = 26;
                else if(numHouses == 1)
                    houseRent = 130;
                else if(numHouses == 2)
                    houseRent = 390;
                else if(numHouses == 3)
                    houseRent = 900;
                else if(numHouses == 4)
                    houseRent = 1100;
                break;
            case 34:
                if(hasHotel)
                    houseRent = 1400;
                else if(numHouses == 0)
                    houseRent = 28;
                else if(numHouses == 1)
                    houseRent = 150;
                else if(numHouses == 2)
                    houseRent = 450;
                else if(numHouses == 3)
                    houseRent = 1000;
                else if(numHouses == 4)
                    houseRent = 1200;
                break;
            // start blue
            case 37:
                if(hasHotel)
                    houseRent = 1500;
                else if(numHouses == 0)
                    houseRent = 35;
                else if(numHouses == 1)
                    houseRent = 175;
                else if(numHouses == 2)
                    houseRent = 500;
                else if(numHouses == 3)
                    houseRent = 1100;
                else if(numHouses == 4)
                    houseRent = 1300;
                break;
            case 39:
                if(hasHotel)
                    houseRent = 2000;
                else if(numHouses == 0)
                    houseRent = 50;
                else if(numHouses == 1)
                    houseRent = 200;
                else if(numHouses == 2)
                    houseRent = 600;
                else if(numHouses == 3)
                    houseRent = 1400;
                else if(numHouses == 4)
                    houseRent = 1700;
                break;
            default:
//                System.err.println("This space shouldn't be generating rent here! Position: " + position );
                break;
        }

        //TODO: This is kind of impossible / convoluted now, we can do it later.
//        if(hasPropertyGroupMonopoly())
//            houseRent *= 2;
    }

//    @Override
//    void action(Token target) {
//
//    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getColorHex() {
		String hexColor = null;
		switch(propertyGroup) {
		case BROWN:
			hexColor = "#955436";
			break;
		case LIGHTBLUE:
			hexColor = "#a9e1fc";
			break;
		case PURPLE:
			hexColor = "#d93c97";
			break;
		case ORANGE:
			hexColor = "#f7921c";
			break;
		case RED:
			hexColor = "#ed1b24";
			break;
		case YELLOW:
			hexColor = "#fff000";
			break;
		case GREEN:
			hexColor = "#00a650";
			break;
		case BLUE:
			hexColor = "#0071bd";
			break;
		}
		return hexColor;
	}

}
