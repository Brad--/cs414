package com.cs414.monopoly.client;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.FlexTable;

public class ViewBoard extends FlexTable {
	
	public static final String STYLE_CORNER = "corner";
	public static final String STYLE_WE = "leftRight";
	public static final String STYLE_NS = "topBottom";
	
	LinkedHashMap<Integer, ViewSpace> mappings = new LinkedHashMap<Integer, ViewSpace>() {{
		put(1, new ViewSpace(10,10, "go", STYLE_CORNER));
		put(2, new ViewSpace(10,9, "beantrees", STYLE_NS));
		put(3, new ViewSpace(10,8, "communityChestSouth", STYLE_NS));
		put(4, new ViewSpace(10,7, "blackbirdCoffeeHouse", STYLE_NS));
		put(5, new ViewSpace(10,6, "csuTax", STYLE_NS));
		put(6, new ViewSpace(10,5, "laurelStation", STYLE_NS));
		put(7, new ViewSpace(10,4, "cuppys", STYLE_NS));
		put(8, new ViewSpace(10,3, "chanceNorthSouth", STYLE_NS));
		put(9, new ViewSpace(10,2, "littleBirdBakeshop", STYLE_NS));
		put(10, new ViewSpace(10,1, "bindleCoffee", STYLE_NS));
		put(11, new ViewSpace(10,0, "jail", STYLE_CORNER));
		put(12, new ViewSpace(9,0, "momoLolos", STYLE_WE));
		put(13, new ViewSpace(8,0, "electric", STYLE_WE));
		put(14, new ViewSpace(7,0, "morgansGrind", STYLE_WE));
		put(15, new ViewSpace(6,0, "sweetSinsations", STYLE_WE));
		put(16, new ViewSpace(5,0, "mulberryStation", STYLE_WE));
		put(17, new ViewSpace(4,0, "fortCollinsCoffeeHouse", STYLE_WE));
		put(18, new ViewSpace(3,0, "communityChestWest", STYLE_WE));
		put(19, new ViewSpace(2,0, "wildBoar", STYLE_WE));
		put(20, new ViewSpace(1,0, "alleyCat", STYLE_WE));
		put(21, new ViewSpace(0,0, "freeParking", STYLE_CORNER));
		put(22, new ViewSpace(0,1, "beanCycle", STYLE_NS));
		put(23, new ViewSpace(0,2, "chanceNorthSouth", STYLE_NS));
		put(24, new ViewSpace(0,3, "humanBean", STYLE_NS));
		put(25, new ViewSpace(0,4, "dazBog", STYLE_NS));
		put(26, new ViewSpace(0,5, "oliveStation", STYLE_NS));
		put(27, new ViewSpace(0,6, "cupsCoffee", STYLE_NS));
		put(28, new ViewSpace(0,7, "everydayJoes", STYLE_NS));
		put(29, new ViewSpace(0,8, "utility", STYLE_NS));
		put(30, new ViewSpace(0,9, "harbingerCoffee", STYLE_NS));
		put(31, new ViewSpace(0,10, "goToJail", STYLE_CORNER));
		put(32, new ViewSpace(1,10, "crookedCup", STYLE_WE));
		put(33, new ViewSpace(2,10, "starryNight", STYLE_WE));
		put(34, new ViewSpace(3,10, "communityChestEast", STYLE_WE));
		put(35, new ViewSpace(4,10, "mugs", STYLE_WE));
		put(36, new ViewSpace(5,10, "mountainStation", STYLE_WE));
		put(37, new ViewSpace(6,10, "chanceEast", STYLE_WE));
		put(38, new ViewSpace(7,10, "dunkinDonuts", STYLE_WE));
		put(39, new ViewSpace(8,10, "luxuryTax", STYLE_WE));
		put(40, new ViewSpace(9,10, "starbucks", STYLE_WE));
		
	}};
	
	public ViewBoard() {
		addStyleName("board");
		setBorderWidth(0);
		setCellPadding(0);
		setCellSpacing(0);
	}
	
	public void drawBoard(Token P1, Token P2, Token P3, Token P4) {
		for (Map.Entry<Integer, ViewSpace> entry : mappings.entrySet()) {
		    Integer key = entry.getKey();
		    ViewSpace space = entry.getValue();
		    space.clear();
		    if(P1 != null && P1.getCurrentPosition() == key) {
		    	space.add(P1);
		    }
		    if(P2 != null && P2.getCurrentPosition() == key) {
		    	space.add(P2);
		    }
		    if(P3 != null && P3.getCurrentPosition() == key) {
		    	space.add(P3);
		    }
		    if(P4 != null && P4.getCurrentPosition() == key) {
		    	space.add(P4);
		    }
		    setWidget(space.getY(), space.getX(), space);
		}
	}
	
}
