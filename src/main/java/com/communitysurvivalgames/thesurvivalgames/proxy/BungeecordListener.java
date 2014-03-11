package com.communitysurvivalgames.thesurvivalgames.proxy;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.ServerStatus;

public class BungeecordListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}

		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		try {
			String tag = in.readUTF();
			if (tag.equalsIgnoreCase("SGUpdate")) {
				if (SGApi.getPlugin().getPluginConfig().isHub()) {
					Bukkit.getLogger().severe("Welp... you configured this server to be a SG server, but Bungee thinks it's a hub -- nice job");
				}
				String realName = in.readUTF();
				ServerStatus status = ServerStatus.valueOf(in.readUTF());
				int playersOnline = in.readInt();
			}
		} catch (IOException e) {}
	}

}
