package co.q64.paradisesurvivalgames.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import co.q64.paradisesurvivalgames.objects.SGArena;

public class GameEndEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	SGArena arena = null;

	/**
	 * Constructs a new GameStartEvent
	 *
	 * @param arena the {@link SGArena} that started
	 */
	public GameEndEvent(SGArena arena) {
		this.arena = arena;
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
	 * Gets the {@link SGArena} that started
	 *
	 * @return the {@link SGArena} that stared
	 */
	public SGArena getArena() {
		return arena;
	}

	/**
	 * Gets the handlers for the event
	 *
	 * @return the handlers
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
