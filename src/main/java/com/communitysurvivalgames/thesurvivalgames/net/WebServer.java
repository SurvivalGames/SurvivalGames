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
		//HTML FILES
		pl.saveResource("httdocs/index.html", true);
		
		//SCRIPTS
		pl.saveResource("httdocs/js/howler.js", false);
		pl.saveResource("httdocs/js/websocket.js", true);
		pl.saveResource("httdocs/js/bootstrap.js", false);
		pl.saveResource("httdocs/js/jquery.js", false);
		pl.saveResource("httdocs/js/jquery-ui.js", false);
		
		//STYLE SHEETS
		pl.saveResource("httdocs/css/style.css", false);
		
		//SFX
		pl.saveResource("httdocs/sounds/stinger0.ogg", false);
		pl.saveResource("httdocs/sounds/stinger1.ogg", false);
		pl.saveResource("httdocs/sounds/stinger2.ogg", false);
		pl.saveResource("httdocs/sounds/stinger3.ogg", false);
		pl.saveResource("httdocs/sounds/stinger4.ogg", false);
		pl.saveResource("httdocs/sounds/stinger5.ogg", false);
		pl.saveResource("httdocs/sounds/stinger6.ogg", false);
		
		//ANNOUNCERS
		pl.saveResource("httdocs/sounds/1.ogg", false);
		pl.saveResource("httdocs/sounds/2.ogg", false);
		pl.saveResource("httdocs/sounds/3.ogg", false);
		pl.saveResource("httdocs/sounds/4.ogg", false);
		pl.saveResource("httdocs/sounds/5.ogg", false);
		pl.saveResource("httdocs/sounds/6.ogg", false);
		pl.saveResource("httdocs/sounds/7.ogg", false);
		pl.saveResource("httdocs/sounds/8.ogg", false);
		pl.saveResource("httdocs/sounds/9.ogg", false);
		pl.saveResource("httdocs/sounds/play.ogg", false);
		pl.saveResource("httdocs/sounds/headshot.ogg", false);
		pl.saveResource("httdocs/sounds/firstblood.ogg", false);
		
		//MUSIC
		pl.saveResource("httdocs/sounds/godown.ogg", false);
		
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