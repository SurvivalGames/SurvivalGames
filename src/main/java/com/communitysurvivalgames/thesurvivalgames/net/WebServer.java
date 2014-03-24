package com.communitysurvivalgames.thesurvivalgames.net;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class WebServer {
	public static void load() {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.addConnector(connector);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}