package com.communitysurvivalgames.thesurvivalgames.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Kit {

	private String kitName;
	private String kitIconLore;

	private ItemStack kitIcon;
	private List<KitItem> items = new ArrayList<KitItem>();

	public Kit(String kitName, List<KitItem> items, Material kitIcon, String kitIconLore) {
		this.kitName = kitName;
		this.kitIcon = new ItemStack(kitIcon);
		this.items = items;
		this.kitIconLore = kitIconLore;

	}

	public String getName() {
		return kitName;
	}

	public ItemStack getIcon() {
		return kitIcon;
	}

	public List<KitItem> getItems() {
		return items;
	}

	public String getIconLore() {
		return kitIconLore;
	}
}
