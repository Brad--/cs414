package cs414.a5.groupA.monopoly.server;

import java.util.Map;
import java.util.Set;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cs414.a5.groupA.monopoly.client.GreetingService;
import cs414.a5.groupA.monopoly.shared.Die;
import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.TokenActionWrapper;
import cs414.a5.groupA.monopoly.shared.UnownedDeedAction;

public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	private static final long serialVersionUID = 1L;

	@Override
	public Map<String, Integer> getTokenPositions() {
		// TODO get the token positions for all tokens. Return them as Integers
		return null;
	}

	@Override
	public Boolean testAsyncCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeTokens(Set<String> names) {
		// TODO save to database
		
	}
	
	@Override
	public TokenActionWrapper roll(Token token) {
		Die die = new Die();
		Token returnToken = die.roll(token);
		return new TokenActionWrapper(token, new UnownedDeedAction());
	}

}
