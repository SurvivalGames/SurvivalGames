/**
 * Name: WeaponItem.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.util.items.interfaces;

/**
 * The enum Weapon item. This is a list of all craftable tools. All can be used
 * as a weapon special item
 */
public enum WeaponItem {
	DIAMOND_AXE, DIAMOND_HOE, DIAMOND_PICKAXE, DIAMOND_SPADE, DIAMOND_SWORD, GOLD_AXE, GOLD_HOE, GOLD_PICKAXE, GOLD_SPADE, GOLD_SWORD, IRON_AXE, IRON_HOE, IRON_PICKAXE, IRON_SPADE, IRON_SWORD, STONE_AXE, STONE_HOE, STONE_PICKAXE, STONE_SPADE, STONE_SWORD, WOOD_AXE, WOOD_HOE, WOOD_PICKAXE, WOOD_SPADE, WOOD_SWORD;

	/**
	 * Search the enum to see if a given item is on the list
	 * <p>
	 * Return true if it is false if it's not
	 *
	 * @param item the item {@link org.bukkit.Material} in it's String format
	 * @return the boolean
	 */
	public static boolean find(String item) {
		for (WeaponItem v : values()) {
			if (v.name().equalsIgnoreCase(item)) {
				return true;
			}
		}
		return false;
	}
}
