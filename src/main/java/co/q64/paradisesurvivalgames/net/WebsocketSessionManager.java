package co.q64.paradisesurvivalgames.net;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class WebsocketSessionManager {
	private static WebsocketSessionManager sessionManager;

	private List<WebsocketSession> sessions = new ArrayList<WebsocketSession>();

	public static WebsocketSessionManager getSessionManager() {
		if (sessionManager == null) {
			sessionManager = new WebsocketSessionManager();
		}
		return sessionManager;
	}

	public List<WebsocketSession> getSessions() {
		return sessions;
	}

	public void openSession(String host) {
		sessions.add(new WebsocketSession(host));
		Bukkit.getLogger().info("Opened Websocket session: " + getSessionByHost(host));
	}

	public void endSession(String host) {
		sessions.remove(getSessionByHost(host));
	}

	public void endSessionByName(String name) {
		sessions.remove(getSessionByName(name));
	}

	public WebsocketSession getSessionByHost(String host) {
		for (WebsocketSession s : sessions) {
			if (s.getHost() == host)
				return s;
		}
		return null;
	}

	public WebsocketSession getSessionByName(String name) {
		for (int i = 0; i < sessions.size(); i++) {
			Bukkit.getLogger().info("Session gotten:" + sessions.get(i));
			if (sessions.get(i).getName().equalsIgnoreCase(name))
				return sessions.get(i);
		}
		return null;
	}

	public void addSessionUsername(String host, String name) {
		Bukkit.getLogger().info("Attemption to update session with data: " + name + " and a host of: " + host);
		for (int i = 0; i < sessions.size(); i++) {
			if (sessions.get(i).getHost().equalsIgnoreCase(host)) {
				sessions.get(i).setName(name);
				if (Bukkit.getPlayer(name) == null) {
					WebsocketServer.s.sendData(sessions.get(i), "nullPlayer");
				}
				Bukkit.getLogger().info("Updated Websocket session information: " + sessions.get(i));
			}
		}
	}
}
