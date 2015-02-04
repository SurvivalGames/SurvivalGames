/**
 * Name: PlayerType.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.util;

import org.bukkit.ChatColor;

public enum PlayerType {

	ADMIN(ChatColor.RED + "%player%"), DEV(ChatColor.DARK_RED + "%player%"), MOD(ChatColor.BLUE + "%player%"), MVP(ChatColor.AQUA + "%player%"), OWNER(ChatColor.DARK_PURPLE + "%player%"), PLAYER(ChatColor.GRAY + "%player%"), VIP(ChatColor.GREEN + "%player%"), YOUTUBER(ChatColor.GOLD + "%player%");
	/**
	 * The Type.
	 */
	private final String type;

	/**
	 * Instantiates a new Player Types
	 *
	 * @param type the node
	 */
	private PlayerType(String type) {
		this.type = type;
	}

	/**
	 * Gets node. For Lobby Status
	 *
	 * @return the node
	 */
	public String getType() {
		return type;
	}
}
