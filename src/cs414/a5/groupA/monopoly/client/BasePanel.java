package cs414.a5.groupA.monopoly.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BasePanel {

	private final GameServiceAsync gameService = GWT.create(GameService.class);
	private final VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	public BasePanel() {
		RootPanel.get("rootPanel").add(getMainVerticalPanel());
		getMainVerticalPanel().addStyleName("mainVerticalPanel");
	}
	
	protected GameServiceAsync getGameService() {
		return gameService;
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
