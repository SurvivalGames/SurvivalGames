package me.theepicbutterstudios.thesurvivalgames.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.Party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PartyManager {
	private static PartyManager pm = new PartyManager();
	private static java.util.Map<String, java.util.UUID> players = new HashMap<String, UUID>();
	private static java.util.Map<java.util.UUID, Party> parties = new HashMap<UUID, Party>();
	private static java.util.Map<String, java.util.UUID> invites = new HashMap<String, UUID>();
	private static Set<String> partyChat = new HashSet<String>();
	private static int partySize;

	public java.util.Map<String, java.util.UUID> getPlayers() {
		return players;
	}

	public java.util.Map<java.util.UUID, Party> getParties() {
		return parties;
	}

	public java.util.Map<String, java.util.UUID> getInvites() {
		return invites;
	}

	public Set<String> getPartyChat() {
		return partyChat;
	}

	public static java.util.UUID startParty(org.bukkit.entity.Player player) {
		Party party = new Party(player.getName());
		players.put(player.getName(), party.getID());
		parties.put(party.getID(), party);
		if (player != null) {
			player.sendMessage(ChatColor.YELLOW + "You have created a new party");
		}
		return party.getID();
	}

	public static void endParty(String name, java.util.UUID id) {
		Party party = (Party) parties.get(id);

		for (String members : party.getMembers()) {
			if (members != null) {
				org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(members);
				if (player != null) {
					player.sendMessage(ChatColor.YELLOW + "Your party has been disbanded");
				}
				players.remove(members);
			}
		}
		party.removeAll();
		org.bukkit.entity.Player player = Bukkit.getServer().getPlayer(name);
		if (player != null) {
			player.sendMessage(ChatColor.YELLOW + "Your party has been disbanded");
		}
		players.remove(name);
		parties.remove(id);
	}

	public static int getMaxPartySize() {
		return partySize;
	}

	public String getParty(org.bukkit.entity.Player p) {
		java.util.UUID id = (java.util.UUID) players.get(p.getName());
		if (id != null) {
			Party party = (Party) parties.get(id);
			if (party != null) {
				String partyMembers = party.getLeader();
				for (String member : party.getMembers()) {
					if (member != null) {
						partyMembers = partyMembers + "," + member;
					}
				}
				return partyMembers;
			}

			return null;
		}

		return null;
	}

	public boolean isPartyLeader(org.bukkit.entity.Player p) {
		java.util.UUID id = (java.util.UUID) players.get(p.getName());
		if (id != null) {
			Party party = (Party) parties.get(id);
			if (party != null) {
				if (party.getLeader().equalsIgnoreCase(p.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static PartyManager getPartyManager(){
		return pm;
	}
}
