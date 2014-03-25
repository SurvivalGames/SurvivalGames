package com.communitysurvivalgames.thesurvivalgames.net;

import java.io.File;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class WebServer {
	public static void load() {
		Server server = new Server(8080);

		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });

		new File(SGApi.getPlugin().getDataFolder(), "httdocs").mkdirs();
		resource_handler.setResourceBase(new File(SGApi.getPlugin().getDataFolder(), "httdocs").getAbsolutePath());

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
		server.setHandler(handlers);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}