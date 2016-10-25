package com.cs414.monopoly.client;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cs414.monopoly.shared.Token;
import com.google.gwt.user.client.ui.FlexTable;

public class Board extends FlexTable {
	
	public static final String STYLE_CORNER = "corner";
	public static final String STYLE_WE = "leftRight";
	public static final String STYLE_NS = "topBottom";
	
	LinkedHashMap<Integer, ViewSpace> mappings = new LinkedHashMap<Integer, ViewSpace>() {{
		put(0, new ViewSpace(10,10, "GO", STYLE_CORNER));
		put(1, new ViewSpace(10,9, "Coffee Shop", STYLE_NS));
		put(2, new ViewSpace(10,8, "Community Chest", STYLE_NS));
		put(3, new ViewSpace(10,7, "Coffee Shop", STYLE_NS));
		put(4, new ViewSpace(10,6, "CSU Stadium Tax", STYLE_NS));
		put(5, new ViewSpace(10,5, "Max Station", STYLE_NS));
		put(6, new ViewSpace(10,4, "Coffee Shop", STYLE_NS));
		put(7, new ViewSpace(10,3, "Chance", STYLE_NS));
		put(8, new ViewSpace(10,2, "Coffee Shop", STYLE_NS));
		put(9, new ViewSpace(10,1, "Coffee Shop", STYLE_NS));
		put(10, new ViewSpace(10,0, "JAIL", STYLE_CORNER));
		put(11, new ViewSpace(9,0, "Coffee Shop", STYLE_WE));
		put(12, new ViewSpace(8,0, "Electric", STYLE_WE));
		put(13, new ViewSpace(7,0, "Coffee Shop", STYLE_WE));
		put(14, new ViewSpace(6,0, "Coffee Shop", STYLE_WE));
		put(15, new ViewSpace(5,0, "Max Station", STYLE_WE));
		put(16, new ViewSpace(4,0, "Coffee Shop", STYLE_WE));
		put(17, new ViewSpace(3,0, "Community Chest", STYLE_WE));
		put(18, new ViewSpace(2,0, "Coffee Shop", STYLE_WE));
		put(19, new ViewSpace(1,0, "Coffee Shop", STYLE_WE));
		put(20, new ViewSpace(0,0, "FREE PARKING", STYLE_CORNER));
		put(21, new ViewSpace(0,1, "Coffee Shop", STYLE_NS));
		put(22, new ViewSpace(0,2, "Chance", STYLE_NS));
		put(23, new ViewSpace(0,3, "Coffee Shop", STYLE_NS));
		put(24, new ViewSpace(0,4, "Coffee Shop", STYLE_NS));
		put(25, new ViewSpace(0,5, "Max Station", STYLE_NS));
		put(26, new ViewSpace(0,6, "Coffee Shop", STYLE_NS));
		put(27, new ViewSpace(0,7, "Coffee Shop", STYLE_NS));
		put(28, new ViewSpace(0,8, "Utility?", STYLE_NS));
		put(29, new ViewSpace(0,9, "Coffee Shop", STYLE_NS));
		put(30, new ViewSpace(0,10, "GOTO JAIL", STYLE_CORNER));
		put(31, new ViewSpace(1,10, "Coffee Shop", STYLE_WE));
		put(32, new ViewSpace(2,10, "Coffee Shop", STYLE_WE));
		put(33, new ViewSpace(3,10, "Community Chest", STYLE_WE));
		put(34, new ViewSpace(4,10, "Coffee Shop", STYLE_WE));
		put(35, new ViewSpace(5,10, "Max Station", STYLE_WE));
		put(36, new ViewSpace(6,10, "Coffee Shop", STYLE_WE));
		put(37, new ViewSpace(7,10, "Coffee Shop", STYLE_WE));
		put(38, new ViewSpace(8,10, "Luxury Tax", STYLE_WE));
		put(39, new ViewSpace(9,10, "Coffee Shop", STYLE_WE));
		
	}};
	
	public Board() {
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
		    if(P1 != null && P1.getLocation() == key) {
		    	space.add(P1);
		    }
		    if(P2 != null && P2.getLocation() == key) {
		    	space.add(P2);
		    }
		    if(P3 != null && P3.getLocation() == key) {
		    	space.add(P3);
		    }
		    if(P4 != null && P4.getLocation() == key) {
		    	space.add(P4);
		    }
		    setWidget(space.getY(), space.getX(), space);
		}
	}
	
}
