package me.theepicbutterstudios.thesurvivalgames;

import java.util.UUID;

import me.theepicbutterstudios.thesurvivalgames.managers.PartyManager;


public class Party {
	private String leader;
	private String[] members;
	private UUID id;
	
	public Party(String leader) {
		this.leader = leader;
		this.members = new String[PartyManager.getMaxPartySize() - 1];
		this.id = UUID.randomUUID();
	}
	
	public String getLeader() {
		return this.leader;
	}
	
	public void setLeader(String player) {
		this.leader = player;
	}
	
	public String[] getMembers() {
		return this.members;
	}
	
	public boolean addMember(String player) {
		for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
			if (this.members[i] == null) {
				this.members[i] = player;
				return true;
			}
		}
		return false;
	}
	
	public boolean hasMember(String player) {
		for (String member : this.members) {
			if (player.equalsIgnoreCase(member)) {
				return true;
			}
		}
		if (this.leader.equalsIgnoreCase(player)) {
			return true;
		}
		return false;
	}
	
	public boolean removeMember(String player) {
		for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
			if ((this.members[i] != null) && (this.members[i].equalsIgnoreCase(player))) {
				this.members[i] = null;
				return true;
			}
		}
		
		return false;
	}
	
	public void removeAll() {
		for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
			this.members[i] = null;
		}
	}
	
	public UUID getID() {
		return this.id;
	}
	
	public boolean hasNoMembers() {
		for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
			if (this.members[i] != null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean hasRoom() {
		for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
			if (this.members[i] == null) {
				return true;
			}
		}
		return false;
	}
}
