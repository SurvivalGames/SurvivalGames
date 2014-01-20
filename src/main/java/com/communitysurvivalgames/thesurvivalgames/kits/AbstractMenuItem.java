/**
 * Name: AbstractMenuItem.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.kits;

import java.util.List;
import com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The type Abstract icon menu item
 */
public class AbstractMenuItem implements ILore, IDisplayName, Selectable, SingleAction, IPermissible {
    private ItemStack item;
    private Material material;
    private ItemMeta meta;
    private List<String> lore;
    private String displayName;
    private Integer Id;
    private String permission;
    private List<ActionActivator> activators;

    /**
     * Gets display name.
     * 
     * @return the display name
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets display name.
     * 
     * @param name the name
     */
    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    /**
     * Gets lore.
     * 
     * @return the lore
     */
    @Override
    public List<String> getLore() {
        return this.lore;
    }

    /**
     * Add line.
     * 
     * @param line the line
     */
    @Override
    public void addLoreLine(String line) {
        this.lore.add(line);
    }

    /**
     * Gets item meta.
     * 
     * @param material the material
     * @return the ItemMeta related to the material
     */
    @Override
    public ItemMeta getItemMeta(Material material) {
        this.meta = Bukkit.getItemFactory().getItemMeta(material);
        this.item = new ItemStack(material, 1);
        return this.meta;
    }

    /**
     * Gets material. to use for the icon. {@link org.bukkit.Material}
     * 
     * @return the material
     */
    @Override
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Sets material to be used as the icon
     * <p>
     * {@link org.bukkit.Material}
     * 
     * @param material the material
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Get item. To use in the icon menu
     * <p>
     * {@link org.bukkit.inventory.ItemStack}
     * 
     * @return the item stack
     */
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Sets item.
     * <p>
     * Sets the {@link org.bukkit.inventory.ItemStack} passing it a <br>
     * {@link org.bukkit.Material} to convert
     * 
     * @param material the material
     */
    public void setItem(Material material) {
        this.item = new ItemStack(material, 1);
    }

    /**
     * Has permission. Returns True if the permission has been set
     * 
     * @return the boolean
     */
    @Override
    public boolean hasPermission() {
        if (this.permission != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets permission to use the item in a string format
     * 
     * @return the permission
     */
    @Override
    public String getPermission() {
        return this.permission;
    }

    /**
     * Sets permission. The permission required for a player to <br>
     * interact with. The permission is {@link java.lang.String} based
     * 
     * @param permission the permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Gets activators.
     * 
     * @return the activators
     */
    @Override
    public List<ActionActivator> getActivators() {
        return this.activators;
    }

    /**
     * Sets activator.
     * 
     * @param activator the activator
     */
    public void setActivator(ActionActivator activator) {
        this.activators.add(activator);
    }

    public void setMeta() {
        this.item.setItemMeta(this.meta);
    }

}
