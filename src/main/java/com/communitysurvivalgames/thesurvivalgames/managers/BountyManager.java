package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class BountyManager {

	private SGArena arena;
	private Map<String, HashMap<String, Integer>> bounties = new HashMap<>(); 
	HashMap<String, Integer> amount = new HashMap<>();
	private Player allPlayers = null;
	private PlayerData send = null;
	private PlayerData reciever = null;
	
	
	public BountyManager(SGArena a){
		this.arena = a;
		bounties = new HashMap<String, HashMap<String, Integer>>();
		amount = new HashMap<String, Integer>();
	}

	public void addBounty(int bounty, Player reciever, Player sender){
		send = new PlayerData(sender);
		if(arena.getPlayers().contains(reciever.getName())){
			try {
				send.removePoints(bounty);
				amount.put(sender.getName(), bounty);
				bounties.put(reciever.getName(), amount);
				reciever.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("BOUNTY_SUCCESS_RECEIVED").replace("%amount", amount.toString()).replace("%sender", sender.getName()));
				sender.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("BOUNTY_SUCCESS_SENT").replace("%amount", amount.toString()).replace("%receiver", reciever.getName()));
			} catch (Exception e){
				sender.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("BOUNTY_FAIL"));
			}
		} else {
			sender.sendMessage(SGApi.getArenaManager().error + "Player is not in game.");
		}
	}

	public void removceBounty(Player reciever, Player sender){
		if(arena.getPlayers().contains(reciever.getName())){
			try {
				bounties.remove(reciever.getName());
				sender.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("BOUNTY_SUCCESS"));
			} catch (Exception e){
				sender.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("BOUNTY_FAIL"));
			}
		}
	}

	public int getBountied(Player reciever, Player sender){
		HashMap<String, Integer> name = bounties.get(reciever.getName());
		int amount = name.get(sender.getName());
		try {
			return amount;
		} catch (Exception e){
			return 0;
		}
	}
}