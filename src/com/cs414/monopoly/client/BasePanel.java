package com.cs414.monopoly.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BasePanel {

	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	public BasePanel() {
		RootPanel.get("rootPanel").add(getMainVerticalPanel());
		getMainVerticalPanel().addStyleName("mainVerticalPanel");
	}
	
	protected GreetingServiceAsync getGreetingService() {
		return greetingService;
	}
	
	protected VerticalPanel getMainVerticalPanel() {
		return mainVerticalPanel;
	}
	
	protected void add(Widget widget) {
		getMainVerticalPanel().add(widget);
	}
	
	protected void clear() {
		getMainVerticalPanel().clear();
	}
}
