package com.communitysurvivalgames.thesurvivalgames.net;

import org.bukkit.Bukkit;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.factory.JWebSocketFactory;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.listener.WebSocketServerTokenEvent;
import org.jwebsocket.listener.WebSocketServerTokenListener;
import org.jwebsocket.server.TokenServer;
import org.jwebsocket.token.Token;

public class WebsocketServer implements WebSocketServerTokenListener {
	private TokenServer tokenServer;

	public TokenServer getTokenServer() {
		return tokenServer;
	}

	public void init() {
		try {
			JWebSocketFactory.start();
			tokenServer = JWebSocketFactory.getTokenServer();
			if (tokenServer != null) {
				Bukkit.getLogger().info("Websocket token server was found!  (thats good!");
			} else {
				Bukkit.getLogger().severe("Websocket token server was not found (awwww");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processClosed(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processOpened(WebSocketServerEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processPacket(WebSocketServerEvent arg0, WebSocketPacket arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processToken(WebSocketServerTokenEvent arg0, Token arg1) {
		// TODO Auto-generated method stub

	}
}