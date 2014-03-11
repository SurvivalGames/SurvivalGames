package com.communitysurvivalgames.thesurvivalgames.enchantment;


public class DedicationEnchantment extends SGEnchantment {

    public DedicationEnchantment(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "DEDICATION";
    }

	@Override
	public String getLore(int lvl) {
		return ChatColor.GRAY + "Dedication " + RomanNumeral.convert(lvl);
	}
}