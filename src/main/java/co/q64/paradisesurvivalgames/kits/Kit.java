package co.q64.paradisesurvivalgames.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Kit {

	private List<Integer> ability = new ArrayList<Integer>();
	private List<KitItem> items = new ArrayList<KitItem>();
	private ItemStack kitIcon;

	private String kitIconLore;
	private String kitName;

	private String type;

	public Kit(String kitName, String type, List<KitItem> items, ItemStack kitIcon, String kitIconLore, List<Integer> abilityIds) {
		this.kitName = kitName;
		this.kitIcon = kitIcon;
		this.items = items;
		this.kitIconLore = kitIconLore;
		this.ability = abilityIds;
		this.type = type;
	}

	public List<Integer> getAbilityIds() {
		return ability;
	}

	public ItemStack getIcon() {
		return kitIcon;
	}

	public String getIconLore() {
		return kitIconLore;
	}

	public List<KitItem> getItems() {
		return items;
	}

	public String getName() {
		return kitName;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "[Kit - Name: " + kitName + " Type: " + type + "]";
	}
}
