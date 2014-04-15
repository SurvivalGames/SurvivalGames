/**
 * Name: Game.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames.objects;

import lombok.Data;

import java.util.List;

/**
 * Game Instance of a Game Used to manage the game in real time The Object on
 * last the duration of a single game
 */
@Data
public class Game {
    /**
     * The ID of the game
     */
    private int id;

    /**
     * The Min players the game requires to start
     */
    private int minPlayers;

    /**
     * The Max players the game requires to start
     */
    private int maxPlayers;

    /**
     * The Current number of players in game
     */
    private int currentPlayers;

    /**
     * The Broadcast perm each player will have this.
     */
    private String broadcastPerm;

    /**
     * The Player keys. The players UUID which linked to
     * {@link co.q64.paradisesurvivalgames.objects.Gamer}
     */
    private List<String> playerKeys;
}
