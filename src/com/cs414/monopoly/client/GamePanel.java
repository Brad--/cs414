package com.cs414.monopoly.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

public class GamePanel extends BasePanel {

	public void init() {
		getGreetingService().testAsyncCall(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				drawUI("Callback failed");
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Boolean result) {
				String message;
				if(result) {
					message = "Server returned true";
				} else {
					message = "Server returned false";
				}
				drawUI(message);
			}});
	}
	
	private void drawUI(String message) {
		Label label = new Label(message);
		getMainVerticalPanel().add(label);
	}
}
