/**
 * Name: Game.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.objects;

import java.util.List;

import lombok.Data;

/**
 * Game Instance of a Game Used to manage the game in real time The Object on
 * last the duration of a single game
 */
@Data
public class Game {
	/**
	 * The Broadcast perm each player will have this.
	 */
	private String broadcastPerm;

	/**
	 * The Current number of players in game
	 */
	private int currentPlayers;

	/**
	 * The ID of the game
	 */
	private int id;

	/**
	 * The Max players the game requires to start
	 */
	private int maxPlayers;

	/**
	 * The Min players the game requires to start
	 */
	private int minPlayers;

	/**
	 * The Player keys. The players UUID which linked to
	 * {@link co.q64.survivalgames.objects.Gamer}
	 */
	private List<String> playerKeys;
}
