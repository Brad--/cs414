package cs414.a5.groupA.monopoly.client;

import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cs414.a5.groupA.monopoly.shared.Token;
import cs414.a5.groupA.monopoly.shared.TokenActionWrapper;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	Boolean testAsyncCall();

	Map<String, Integer> getTokenPositions();
	
	void initializeTokens(Set<String> names);

	TokenActionWrapper roll(Token token);
}
