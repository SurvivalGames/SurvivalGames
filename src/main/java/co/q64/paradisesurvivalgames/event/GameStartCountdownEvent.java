package co.q64.paradisesurvivalgames.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartCountdownEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private int timeRemainingUntilStart = 0;

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
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public GameStartCountdownEvent(int timeLeftTillStart){
		timeRemainingUntilStart = timeLeftTillStart;
	}
	
	public int getTimeRemaining(){
		return timeRemainingUntilStart;
	}
	
	public boolean hasCountdownFinised(){
		if (timeRemainingUntilStart == 0){
			return true;
		}
		else{
			return false;
		}
	}

}
