package co.q64.paradisesurvivalgames.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WebsocketSessionManager {
	private static WebsocketSessionManager sessionManager;

	private List<WebsocketSession> sessions = new ArrayList<WebsocketSession>();
	private Map<UUID, Integer> sessionId = new HashMap<UUID, Integer>();
	private Random rnd = new Random();

	public static WebsocketSessionManager getSessionManager() {
		if (sessionManager == null) {
			sessionManager = new WebsocketSessionManager();
		}
		return sessionManager;
	}

	@SuppressWarnings("deprecation")
	public void addSessionUsername(String host, String name) {
		for (int i = 0; i < sessions.size(); i++) {
			if (sessions.get(i).getHost().equalsIgnoreCase(host)) {
				sessions.get(i).setName(name);
				if (Bukkit.getPlayer(name) == null) {
					WebsocketServer.s.sendData(sessions.get(i), "nullPlayer");
				}
			}
		}
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
			if (sessions.get(i).getName().equalsIgnoreCase(name))
				return sessions.get(i);
		}
		return null;
	}

	public List<WebsocketSession> getSessions() {
		return sessions;
	}

	public void openSession(String host) {
		sessions.add(new WebsocketSession(host));
		Bukkit.getLogger().info("Opened Websocket session: " + getSessionByHost(host));
	}

	public void revokeSession(WebsocketSession session) {
		sessions.remove(session);
	}

	public int getSessionId(Player p) {
		if (sessionId.get(p.getUniqueId()) == null) {
			sessionId.put(p.getUniqueId(), rnd.nextInt(100000));
		}
		return sessionId.get(p.getUniqueId());
	}
}
