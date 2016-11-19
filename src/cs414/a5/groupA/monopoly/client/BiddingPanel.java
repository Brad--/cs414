package cs414.a5.groupA.monopoly.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class BiddingPanel extends PopupPanel {
	
	private int bidAmount = 0;

	public BiddingPanel(int position) {
		Label promptLabel = new Label("Property at position " + position + " is up for bidding.");
		Label priceLabel = new Label("What would you like to pay for it?");
		final TextBox bidTextBox = new TextBox();
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		Button bidButton = new Button("Bid");
		bidButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String stringBid = bidTextBox.getValue();
				if(stringBid != null && !stringBid.isEmpty()) {
					try {
						int intBid = Integer.parseInt(stringBid);
						setBidAmount(intBid);
						handleClick();
					} catch (Exception e) {
						AlertPopup alert = new AlertPopup("Please only enter a number for your bid.");
					}
				} else {
					AlertPopup alert = new AlertPopup("Please do not leave your bid blank.");
				}
			}
		});
		
		Button noBidButton = new Button("Don't Bid");
		noBidButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				handleClick();
			}
		});
		buttonPanel.add(bidButton);
		buttonPanel.add(noBidButton);
		
		FlexTable flexTable = new FlexTable();
		
		flexTable.setWidget(0, 0, promptLabel);
		flexTable.setWidget(1, 0, priceLabel);
		flexTable.setWidget(1, 1, bidTextBox);
		flexTable.setWidget(2, 1, buttonPanel);
		
		setGlassEnabled(true);
		center();
		setWidget(flexTable);
	}

	public int getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}
	
	public void handleClick() {
		// For overridding
	}
}
