package co.q64.survivalgames.tracking.dispatch;

import java.net.URI;

import org.bukkit.Bukkit;

public abstract class AnalyticsDispatcher {

	private String host;
	private int port;
	private String userAgent;

	public AnalyticsDispatcher(String userAgent, String host, int port) {
		this.userAgent = userAgent;
		this.host = host;
		this.port = port;
	}

	protected static String getQueryParameter(String query, String parameter) {
		String[] params = query.split("&");
		for (String param : params) {
			String[] nameValue = param.split("=");
			if (nameValue[0].equals(parameter)) {
				return nameValue[1];
			}
		}
		return null;
	}

	public void dispatch(String analyticsString) {
		URI uri = URI.create(analyticsString);

		String timeDispatched = getQueryParameter(uri.getQuery(), "utmht");
		if (timeDispatched != null) {
			try {
				Long time = Long.valueOf(Long.parseLong(timeDispatched));
				analyticsString = analyticsString + "&utmqt=" + (System.currentTimeMillis() - time.longValue());
			} catch (NumberFormatException e) {
				Bukkit.getLogger().severe("Error parsing utmht parameter: " + e.toString());
			}
		} else {
			Bukkit.getLogger().severe("Unable to find utmht parameter: " + analyticsString);
		}

		dispatchToNetwork(analyticsString);
	}

	protected abstract void dispatchToNetwork(String analyticsString);
}
