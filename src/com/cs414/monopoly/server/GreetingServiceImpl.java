package com.cs414.monopoly.server;

import com.cs414.monopoly.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	private static final long serialVersionUID = 1L;

	@Override
	public Boolean testAsyncCall() {
		return true;
	}
}
