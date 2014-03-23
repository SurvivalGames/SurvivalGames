/*package com.communitysurvivalgames.thesurvivalgames.net;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServer {
	public static void load() {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.addConnector(connector);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		ServletHolder holderEvents = new ServletHolder("ws-plugin", SGWebSocketServlet.class);
		context.addServlet(holderEvents, "/plugin/*");

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}*/