package cs414.a5.groupA.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

import cs414.a5.groupA.monopoly.server.Token;

public class PickNumPlayersPanel extends BasePanel {
	
	PlayerPiece p1;
	PlayerPiece p2;
	PlayerPiece p3;
	PlayerPiece p4;
	int numMinutes;
	
	public PickNumPlayersPanel() {
		init();
	}
	
	public void init() {
		Label lblPlayers = new Label("Please select how many people are playing");
		
		Button players2 = new Button("2 Players");
		Button players3 = new Button("3 Players");
		Button players4 = new Button("4 Players");
		
		Label lblTime = new Label("How many minutes do we want to play for?");
		final TextBox timeBox = new TextBox();
		timeBox.setText("30");
		
		players2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setupPlayers(2);
				setTime(Integer.parseInt(timeBox.getText()));
			}
		});
		players3.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setupPlayers(3);
				setTime(Integer.parseInt(timeBox.getText()));
			}
		});
		players4.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setupPlayers(4);
				setTime(Integer.parseInt(timeBox.getText()));
			}
		});
		
		
		add(lblPlayers);
		add(players2);
		add(players3);
		add(players4);
		add(lblTime);
		add(timeBox);
		
	}
	
	private void setTime(int mins) {
		this.numMinutes = mins;
	}
	
	private void setupPlayers(final int numOfPlayers) {
		clear();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		
		final PlayerTokenPickerPanel p1Picker = new PlayerTokenPickerPanel(1);
		final PlayerTokenPickerPanel p2Picker = new PlayerTokenPickerPanel(2);
		final PlayerTokenPickerPanel p3Picker = new PlayerTokenPickerPanel(3);
		final PlayerTokenPickerPanel p4Picker = new PlayerTokenPickerPanel(4);
		
		horizontalPanel.add(p1Picker);
		horizontalPanel.add(p2Picker);
		horizontalPanel.add(p3Picker);
		horizontalPanel.add(p4Picker);
		
		Button startGameButton = new Button("Start Game");
		startGameButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if((p1Picker.isActive() && !p1Picker.isValidToken()) ||
						(p2Picker.isActive() && !p2Picker.isValidToken()) ||
						(p3Picker.isActive() && !p3Picker.isValidToken()) ||
						(p4Picker.isActive() && !p4Picker.isValidToken())) {
					AlertPopup alertPopup = new AlertPopup("Please make sure every user has a name and game piece");
					alertPopup.show();
				} else {
					p1 = p1Picker.getPlayerPiece();
					p2 = p2Picker.getPlayerPiece();
					if(numOfPlayers >= 3) {
						p3 = p3Picker.getPlayerPiece();
						if(numOfPlayers == 4) {
							p4 = p4Picker.getPlayerPiece();
						}
					}
					startGame(numOfPlayers);
				}
			}
		});
		
		if(numOfPlayers < 4) {
			p4Picker.setActive(false);
			if(numOfPlayers < 3) {
				p3Picker.setActive(false);
			}
		}
		
		add(horizontalPanel);
		add(startGameButton);

	}
	
	private void startGame(int numOfPlayers) {
		clear();
		GamePanel gamePanel = new GamePanel(numOfPlayers, p1, p2, p3, p4, numMinutes);
	}
	
	
}
