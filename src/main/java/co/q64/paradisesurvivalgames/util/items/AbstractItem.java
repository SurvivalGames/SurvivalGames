/**
 * Name: AbstractItem.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames.util.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import co.q64.paradisesurvivalgames.util.items.interfaces.IDisplayName;
import co.q64.paradisesurvivalgames.util.items.interfaces.IEnhanceable;
import co.q64.paradisesurvivalgames.util.items.interfaces.ILore;

public abstract class AbstractItem implements IDisplayName, ILore, IEnhanceable {

	private String displayName;
	private List<Enchantment> enchantments;
	private Integer Id;
	private ItemStack item;
	private Material material;
	private ItemMeta meta;
	private String permission;

	/**
	 * Add line.
	 *
	 * @param line the line
	 */
	@Override
	public void addLoreLine(String line) {

	}

	/**
	 * Gets display name.
	 *
	 * @return the display name
	 */
	@Override
	public String getDisplayName() {
		return null;
	}

	/**
	 * Gets enchants.
	 *
	 * @return the enchants
	 */
	@Override
	public List<Enchantment> getEnchants() {
		return null;
	}

	/**
	 * Gets item meta.
	 *
	 * @param material the material
	 * @return the ItemMeta related to the material
	 */
	@Override
	public ItemMeta getItemMeta(Material material) {
		return null;
	}

	/**
	 * Gets lore.
	 *
	 * @return the lore
	 */
	@Override
	public List<String> getLore() {
		return null;
	}

	public abstract String getPermission();

	/**
	 * Sets display name.
	 *
	 * @param name the name
	 */
	@Override
	public void setDisplayName(String name) {

	}

	public abstract void setPermission(String s);
}
