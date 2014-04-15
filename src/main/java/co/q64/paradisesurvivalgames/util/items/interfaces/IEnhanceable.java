/**
 * Name: IEnhanceable.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */

package co.q64.paradisesurvivalgames.util.items.interfaces;

import org.bukkit.enchantments.Enchantment;

import java.util.List;

/**
 * The interface I enhanceable.
 */
public interface IEnhanceable extends IMetable {
    /**
     * Gets enchants.
     *
     * @return the enchants
     */
    List<Enchantment> getEnchants();
}
