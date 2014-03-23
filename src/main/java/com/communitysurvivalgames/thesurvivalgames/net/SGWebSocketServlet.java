package com.communitysurvivalgames.thesurvivalgames.net;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@WebServlet("/SGWebSocketServlet")
public class SGWebSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(SGWebSocketHandler.class);
    }
}