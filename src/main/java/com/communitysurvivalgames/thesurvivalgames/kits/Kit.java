package com.communitysurvivalgames.thesurvivalgames.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Kit {

	private String kitName;

	private ItemStack kitIcon;
	private List<KitItem> items = new ArrayList<KitItem>();

	public Kit(String kitName, List<KitItem> items, ItemStack kitIcon) {
		this.kitName = kitName;
		this.kitIcon = kitIcon;
		this.items = items;
	}

	public String getName() {
		return kitName;
	}
	
	public ItemStack getIcon(){
		return kitIcon;
	}
	
	public List<KitItem> getItems(){
		return items;
	}
}
