package com.cs414.monopoly.shared;

import static com.cs414.monopoly.shared.PropertyGroup.*;

import com.cs414.monopoly.shared.exception.HotelException;
import com.cs414.monopoly.shared.exception.HouseException;

public class Deed extends Space {
    protected int price;
    protected int houseRent;
    private int numHouses;
    private boolean hasHotel;
    private Token owner; // Change this to Token when that exists
    private PropertyGroup propertyGroup;
    private String name;
    
    public Deed(Board board, int position) {
        super(board, position);
        calcPriceAndPropertyGroup();
        calcRent();
        hasHotel  = false;
        numHouses = 0;
        owner = null;
    }

    public Token getOwner() {
        return owner;
    }


    public int getRent() {
        return houseRent;
    }
    public PropertyGroup getPropertyGroup() {
        return propertyGroup;
    }

    public void addHouse() throws HouseException {
        if(numHouses == 4)
            throw new HouseException("You can't add another house");
        if(numHouses < 4) {
            numHouses++;
            calcRent();
        }
    }

    public void addHotel() throws HotelException {
        if(hasHotel)
            throw new HotelException("You can't add another hotel");
        if(numHouses != 4)
            throw new HotelException("You can't add a hotel until you have 4 houses");

        hasHotel  = true;
        numHouses = 0;
        calcRent();
    }

    public void changeOwnership(Token newOwner) {
        owner = newOwner;
        calcRent();
    }

    private boolean hasPropertyGroupMonopoly() {
        // This will check against itself, so start at 0
        int numProperties = 0;
        for(Deed d : board.getOwnedDeeds(owner))
            if(propertyGroup == d.getPropertyGroup())
                numProperties++;

        int totalProperties;
        if(propertyGroup == BROWN || propertyGroup == BLUE)
            totalProperties = 2;
        else if(propertyGroup == RAILROAD)
            totalProperties = 4;
        else
            totalProperties = 3;

        if(numProperties == totalProperties)
            return true;
        else
            return false;
    }

    // Returns the mortgage value of the property
    public int mortgageProperty() {
        owner = null;
        return price / 2;
    }

    @Override
    public void action(Token target) {
        target.payRent(houseRent);
        owner.earnRent(houseRent);
    }

    // Sets the price based on position
    private void calcPriceAndPropertyGroup() {
        if(position == 2 || position == 4) {
            price = 60;
            propertyGroup = BROWN;
        }
        else if(position == 7 || position == 9) {
            price = 100;
            propertyGroup = LIGHTBLUE;
        }
        else if(position == 10) {
            price = 120;
            propertyGroup = LIGHTBLUE;
        }
        else if(position == 12 || position == 14) {
            price = 140;
            propertyGroup = PURPLE;
        }
        else if(position == 15) {
            price = 160;
            propertyGroup = PURPLE;
        }
        else if(position == 17 || position == 19) {
            price = 180;
            propertyGroup = ORANGE;
        }
        else if(position == 20) {
            price = 200;
            propertyGroup = ORANGE;
        }
        else if(position == 22 || position == 24) {
            price = 220;
            propertyGroup = RED;
        }
        else if(position == 25) {
            price = 240;
            propertyGroup = RED;
        }
        else if(position == 27 || position == 28) {
            price = 260;
            propertyGroup = YELLOW;
        }
        else if(position == 30) {
            price = 280;
            propertyGroup = YELLOW;
        }
        else if(position == 32 || position == 33) {
            price = 300;
            propertyGroup = GREEN;
        }
        else if(position == 35) {
            price = 320;
            propertyGroup = GREEN;
        }
        else if(position == 38) {
            price = 350;
            propertyGroup = BLUE;
        }
        else if(position == 40){
            price = 400;
            propertyGroup = BLUE;
        }
    }

    // This is messy, but there isn't really a method to the pricing
    private void calcRent() {
        switch(position) {
            // start brown
            case 2:
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
            case 4:
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
            case 7:
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
            case 10:
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
            case 12:
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
            case 15:
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
            case 17:
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
            case 20:
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
            case 22:
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
            case 25:
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
            case 28:
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
            case 30:
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
            case 33:
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
            case 35:
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
            case 38:
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
            case 40:
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
                System.err.println("This space shouldn't be generating rent here! Position: " + position );
                break;
        }

        if(hasPropertyGroupMonopoly())
            houseRent *= 2;
    }

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
