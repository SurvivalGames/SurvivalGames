package com.communitysurvivalgames.thesurvivalgames.enchantment;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class DedicationEnchantment extends SGEnchantment {

    public DedicationEnchantment(int id) {
        super(id);
    }

    @Override
    public String getName() {
        return "Dedication";
    }

	public String getLore(int lvl) {
		return ChatColor.GRAY + "Dedication " + RomanNumeral.convert(lvl);
	}
}