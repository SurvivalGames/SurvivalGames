package com.communitysurvivalgames.thesurvivalgames.managers;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;

import com.communitysurvivalgames.thesurvivalgames.enchantment.DedicationEnchantment;
import com.communitysurvivalgames.thesurvivalgames.enchantment.ShockingEnchantment;
import com.communitysurvivalgames.thesurvivalgames.enchantment.UnenchantableEnchantment;

public class EnchantmentManager {
	
	ShockingEnchantment shocking = new ShockingEnchantment(120);
	UnenchantableEnchantment unenchantable = new UnenchantableEnchantment(121);
	DedicationEnchantment dedication = new DedicationEnchantment(122);
	
	
	public void registerAll() {
		try {
		    Field f = Enchantment.class.getDeclaredField("acceptingNew");
		    f.setAccessible(true);
		    f.set(null, true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		Enchantment.registerEnchantment(shocking);
		Enchantment.registerEnchantment(unenchantable);
		Enchantment.registerEnchantment(dedication);
	}
}
