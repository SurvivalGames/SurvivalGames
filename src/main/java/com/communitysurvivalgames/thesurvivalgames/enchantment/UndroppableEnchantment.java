package com.communitysurvivalgames.thesurvivalgames.enchantment;


public class UndroppableEnchantment extends SGEnchantment {

	public UndroppableEnchantment(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "UNDROPPABLE";
	}

	@Override
	public String getLore(int lvl) {
		return ChatColor.GRAY + "Undroppable " + RomanNumeral.convert(lvl);
	}
}
