package com.communitysurvivalgames.thesurvivalgames.proxy;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeecordListener implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
            return;
        }
 
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
			String subchannel = in.readUTF();
		} catch (IOException e) {
		}
	}

}
