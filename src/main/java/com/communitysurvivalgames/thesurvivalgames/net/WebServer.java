package com.communitysurvivalgames.thesurvivalgames.net;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

public class WebServer extends AbstractHandler {
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>Hello World</h1>");
	}

	public static void load() {
		Server server = new Server(8123);
		server.setHandler(new WebServer());

		int maxconnections = 5;
		if (maxconnections < 2)
			maxconnections = 2;
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(maxconnections);
		ExecutorThreadPool pool = new ExecutorThreadPool(2, maxconnections, 60, TimeUnit.SECONDS, queue);
		server.setThreadPool(pool);

		try {
			server.start();
		} catch (Exception e1) {}
	}
}