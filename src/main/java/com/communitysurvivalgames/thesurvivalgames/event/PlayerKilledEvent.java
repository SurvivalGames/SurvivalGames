package com.communitysurvivalgames.thesurvivalgames.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKilledEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	Player player;
	Player killer;

	/**
	 * Constructs a new KitGivenEvent
	 *
	 * @param player the player that recieved the kit
	 * @param kit the kit given to the player
	 */
	public PlayerKilledEvent(Player player, Player killer) {
		this.player = player;
	}

	/**
	 * Gets the player that recieved the kit
	 *
	 * @return the player that recieved the kit
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the killer
	 * 
	 * @return the player object that killed the player of the event
	 */
	public Player getKiller() {
		return killer;
	}

	/**
	 * Gets the {@link HandlerList} for the event
	 *
	 * @return the {@link HandlerList} for the event
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * Gets the handlers for the event
	 *
	 * @return the handlers
	 */
	public HandlerList getHandlers() {
		return handlers;
	}
}
