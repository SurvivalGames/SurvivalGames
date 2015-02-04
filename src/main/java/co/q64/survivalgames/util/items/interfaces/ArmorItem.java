/**
 * Name: ArmorItem.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.util.items.interfaces;

public enum ArmorItem {
	CHAINMAIL_BOOTS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET, CHAINMAIL_LEGGINGS, DIAMOND_BOOTS, DIAMOND_CHESTPLATE, DIAMOND_HELMET, DIAMOND_LEGGINGS, GOLD_BOOTS, GOLD_CHESTPLATE, GOLD_HELMET, GOLD_LEGGINGS, IRON_BOOTS, IRON_CHESTPLATE, IRON_HELMET, IRON_LEGGINGS, LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS;

	/**
	 * Search the enum to see if a given item is on the list
	 * <p>
	 * Return true if it is false if it's not
	 *
	 * @param item the item {@link org.bukkit.Material} in it's String format
	 * @return the boolean
	 */
	public static boolean find(String item) {
		for (ArmorItem v : values()) {
			if (v.name().equalsIgnoreCase(item)) {
				return true;
			}
		}
		return false;
	}
}
