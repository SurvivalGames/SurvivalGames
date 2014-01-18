package com.communitysurvivalgames.thesurvivalgames.enchantment;

import org.bukkit.ChatColor;

public class ShockingEnchantment extends SGEnchantment {
	public ShockingEnchantment(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "Shocking";
	}

	public String getLore(int lvl) {
		return ChatColor.GRAY + "Shocking " + RomanNumeral.convert(lvl);
	}
}
