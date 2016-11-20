package cs414.a5.groupA.monopoly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;

import cs414.a5.groupA.monopoly.shared.DatabaseDeed;
import cs414.a5.groupA.monopoly.shared.Token;


public class ViewBoard extends FlexTable {
	
	private final GameServiceAsync gameService = GWT.create(GameService.class);
	
	private static String gameId;
	
	public static final String STYLE_CORNER = "corner";
	public static final String STYLE_WE = "leftRight";
	public static final String STYLE_NS = "topBottom";
	
	PlayerStatsPanel p1StatsPanel = new PlayerStatsPanel();
	PlayerStatsPanel p2StatsPanel = new PlayerStatsPanel();
	PlayerStatsPanel p3StatsPanel = new PlayerStatsPanel();
	PlayerStatsPanel p4StatsPanel = new PlayerStatsPanel();
	
	@SuppressWarnings("serial")
	LinkedHashMap<Integer, ViewSpace> mappings = new LinkedHashMap<Integer, ViewSpace>() {{
		put(0, new ViewSpace(10,10, "go", STYLE_CORNER));
		put(1, new ViewSpace(10,9, "beantrees", STYLE_NS));
		put(2, new ViewSpace(10,8, "communityChestSouth", STYLE_NS));
		put(3, new ViewSpace(10,7, "blackbirdCoffeeHouse", STYLE_NS));
		put(4, new ViewSpace(10,6, "csuTax", STYLE_NS));
		put(5, new ViewSpace(10,5, "laurelStation", STYLE_NS));
		put(6, new ViewSpace(10,4, "cuppys", STYLE_NS));
		put(7, new ViewSpace(10,3, "chanceNorthSouth", STYLE_NS));
		put(8, new ViewSpace(10,2, "littleBirdBakeshop", STYLE_NS));
		put(9, new ViewSpace(10,1, "bindleCoffee", STYLE_NS));
		put(10, new ViewSpace(10,0, "jail", STYLE_CORNER));
		put(11, new ViewSpace(9,0, "momoLolos", STYLE_WE));
		put(12, new ViewSpace(8,0, "electric", STYLE_WE));
		put(13, new ViewSpace(7,0, "morgansGrind", STYLE_WE));
		put(14, new ViewSpace(6,0, "sweetSinsations", STYLE_WE));
		put(15, new ViewSpace(5,0, "mulberryStation", STYLE_WE));
		put(16, new ViewSpace(4,0, "fortCollinsCoffeeHouse", STYLE_WE));
		put(17, new ViewSpace(3,0, "communityChestWest", STYLE_WE));
		put(18, new ViewSpace(2,0, "wildBoar", STYLE_WE));
		put(19, new ViewSpace(1,0, "alleyCat", STYLE_WE));
		put(20, new ViewSpace(0,0, "freeParking", STYLE_CORNER));
		put(21, new ViewSpace(0,1, "beanCycle", STYLE_NS));
		put(22, new ViewSpace(0,2, "chanceNorthSouth", STYLE_NS));
		put(23, new ViewSpace(0,3, "humanBean", STYLE_NS));
		put(24, new ViewSpace(0,4, "dazBog", STYLE_NS));
		put(25, new ViewSpace(0,5, "oliveStation", STYLE_NS));
		put(26, new ViewSpace(0,6, "cupsCoffee", STYLE_NS));
		put(27, new ViewSpace(0,7, "everydayJoes", STYLE_NS));
		put(28, new ViewSpace(0,8, "utility", STYLE_NS));
		put(29, new ViewSpace(0,9, "harbingerCoffee", STYLE_NS));
		put(30, new ViewSpace(0,10, "goToJail", STYLE_CORNER));
		put(31, new ViewSpace(1,10, "crookedCup", STYLE_WE));
		put(32, new ViewSpace(2,10, "starryNight", STYLE_WE));
		put(33, new ViewSpace(3,10, "communityChestEast", STYLE_WE));
		put(34, new ViewSpace(4,10, "mugs", STYLE_WE));
		put(35, new ViewSpace(5,10, "mountainStation", STYLE_WE));
		put(36, new ViewSpace(6,10, "chanceEast", STYLE_WE));
		put(37, new ViewSpace(7,10, "dunkinDonuts", STYLE_WE));
		put(38, new ViewSpace(8,10, "luxuryTax", STYLE_WE));
		put(39, new ViewSpace(9,10, "starbucks", STYLE_WE));	
	}};
	
	public ViewBoard() {
		addStyleName("board");
		setBorderWidth(0);
		setCellPadding(0);
		setCellSpacing(0);
		init();
	}
	
	public void init() {
		setWidget(9,5, p1StatsPanel);
		setWidget(5,1, p2StatsPanel);
		setWidget(1,5, p3StatsPanel);
		setWidget(5,9, p4StatsPanel);
	}
	
	
	public void renderBoard() {

		getGameService().getAllGameTokens(getGameId(), new AsyncCallback<ArrayList<Token>>() {

			@Override
			public void onFailure(Throwable caught) {
				AlertPopup alert = new AlertPopup(caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Token> result) {
				getOwnedDeeds(result);
			}});

		
	}
	
	public void getOwnedDeeds(final ArrayList<Token> tokens) {
		getGameService().getAllOwnedDeedsForGameId(getGameId(), new AsyncCallback<ArrayList<DatabaseDeed>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<DatabaseDeed> result) {
				HashMap<Integer, DatabaseDeed> deedMap = new HashMap<Integer, DatabaseDeed>();
				for(DatabaseDeed deed : result) {
					deedMap.put(deed.getPosition(), deed);
				}
				drawBoardAndStatsPanel(tokens, deedMap);
			}});
	}
	
	public void drawBoardAndStatsPanel(ArrayList<Token> tokens, HashMap<Integer, DatabaseDeed> deedMap) {
		
		for (Entry<Integer, ViewSpace> entry : mappings.entrySet()) {
			Integer key = entry.getKey();
		    ViewSpace space = entry.getValue();
		    space.clear();
		    DatabaseDeed deed = deedMap.get(key);
		    if(deed != null) {
		    	if(!deed.isMortgaged()) {
			    	if(deed.getHousingCount() == 5) {
			    		space.add(new Image("img/housing/hotel.png"));
			    	} else {
			    		for(int houseIndex = 0; houseIndex < deed.getHousingCount(); houseIndex++) {
			    			space.add(new Image("img/housing/house.png"));
			    		}
			    	}
		    	} else {
		    		space.add(new Image("img/housing/mortgaged.png"));
		    	}
		    }
			for (Token token : tokens) {
			    if(token.getPosition() == key) {
			    	space.add(new Image(token.getGamePiece()));
			    }
			}
			setWidget(space.getY(), space.getX(), space);
		}
		
		Token p1 = tokens.get(0);
		Token p2 = tokens.get(1);
		Token p3 = null;
		Token p4 = null;
		
		if(tokens.size() > 2) {
			p3 = tokens.get(2);
			if(tokens.size() > 3) {
				p4 = tokens.get(3);
			}
		}
		
		p1StatsPanel.setPlayer(p1);
		p2StatsPanel.setPlayer(p2);
		p3StatsPanel.setPlayer(p3);
		p4StatsPanel.setPlayer(p4);
	}
	
	protected GameServiceAsync getGameService() {
		return gameService;
	}

	public static String getGameId() {
		if(gameId == null) {
			setGameId(Window.Location.getParameter("gameId"));
		}
		return gameId;
	}

	public static void setGameId(String gameId) {
		ViewBoard.gameId = gameId;
	}
	
}
