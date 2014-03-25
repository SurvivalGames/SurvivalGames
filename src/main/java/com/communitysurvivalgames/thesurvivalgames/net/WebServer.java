package com.communitysurvivalgames.thesurvivalgames.net;

import java.io.File;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class WebServer {
	public static void runServer() {
		Server server = new Server(8080);

		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });

		new File(SGApi.getPlugin().getDataFolder(), "httdocs").mkdirs();
		
		TheSurvivalGames pl = SGApi.getPlugin();
		pl.saveResource("httdocs/index.html", false);
		pl.saveResource("httdocs/howler.js", false);
		pl.saveResource("httdocs/websocket.js", false);
		pl.saveResource("httdocs/sounds/1.ogg", false);
		pl.saveResource("httdocs/sounds/2.ogg", false);
		pl.saveResource("httdocs/sounds/3.ogg", false);
		pl.saveResource("httdocs/sounds/4.ogg", false);
		pl.saveResource("httdocs/sounds/5.ogg", false);
		pl.saveResource("httdocs/sounds/6.ogg", false);
		pl.saveResource("httdocs/sounds/7.ogg", false);
		pl.saveResource("httdocs/sounds/8.ogg", false);
		pl.saveResource("httdocs/sounds/9.ogg", false);
		pl.saveResource("httdocs/sounds/10.ogg", false);
		pl.saveResource("httdocs/sounds/play.ogg", false);
		pl.saveResource("httdocs/sounds/headshot.ogg", false);
		
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