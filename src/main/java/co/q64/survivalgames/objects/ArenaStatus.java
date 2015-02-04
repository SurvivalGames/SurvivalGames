/**
 * Name: ArenaStatus.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.objects;

/**
 * The Arena status. This is different from Game status
 */
public enum ArenaStatus {
	/**
	 * ARENA DISABLED.
	 */
	DISABLED,
	/**
	 * UNKNOWN ERROR.
	 */
	ERROR,
	/**
	 * ARENA IS INGAME.
	 */
	INGAME,
	/**
	 * ARENA IS READY TO BE USED
	 */
	READY,
	/**
	 * GAME FINISHED.
	 */
	RELOAD
}
