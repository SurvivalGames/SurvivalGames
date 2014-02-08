package com.communitysurvivalgames.thesurvivalgames.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {

	private String kitName;
	private String kitIconLore;

	private ItemStack kitIcon;
	private List<KitItem> items = new ArrayList<KitItem>();

	private List<Integer> ability = new ArrayList<Integer>();

	public Kit(String kitName, List<KitItem> items, Material kitIcon, String kitIconLore, List<Integer> abilityIds) {
		this.kitName = kitName;
		this.kitIcon = new ItemStack(kitIcon);
		this.items = items;
		this.kitIconLore = kitIconLore;
		this.ability = abilityIds;
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

	public List<Integer> getAbilityIds() {
		return ability;
	}
}
