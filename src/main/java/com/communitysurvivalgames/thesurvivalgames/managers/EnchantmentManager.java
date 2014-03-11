package com.communitysurvivalgames.thesurvivalgames.managers;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.enchantment.DedicationEnchantment;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.enchantment.SGEnchantment;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.enchantment.ShockingEnchantment;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.enchantment.UndroppableEnchantment;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.enchantment.UnenchantableEnchantment;

public class EnchantmentManager {

	public static ShockingEnchantment shocking = new ShockingEnchantment(120);
	public static UnenchantableEnchantment unenchantable = new UnenchantableEnchantment(121);
	public static DedicationEnchantment dedication = new DedicationEnchantment(122);
	public static UndroppableEnchantment undroppable = new UndroppableEnchantment(123);

	public void registerAll() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(Enchantment.getByName("SHOCKING") != null)
			return;
		Enchantment.registerEnchantment(shocking);
		Enchantment.registerEnchantment(unenchantable);
		Enchantment.registerEnchantment(dedication);
		Enchantment.registerEnchantment(undroppable);
	}

	public void enchantItemSG(EnchantItemEvent e) {
		Random random = new Random();
		e.getEnchantsToAdd().clear();
		if (e.getExpLevelCost() == 1 || e.getExpLevelCost() == 2) {
			int r = random.nextInt(1);
			if (r == 0) {
				e.getEnchantsToAdd().put(Enchantment.DAMAGE_ALL, 1);
				return;
			}

			if (r == 1) {
				e.getEnchantsToAdd().put(Enchantment.getByName("DEDICATION"), 1);
				ItemMeta itemMeta = e.getItem().getItemMeta();
				SGEnchantment enchantmentSG = (SGEnchantment) Enchantment.getByName("DEDICATION");
				List<String> currentLore = itemMeta.getLore();
				if (currentLore == null) {
					currentLore = new ArrayList<String>();
				}
				currentLore.add(enchantmentSG.getLore(1));
				itemMeta.setLore(currentLore);
				e.getItem().setItemMeta(itemMeta);
				return;
			}
			return;
		}
		if (e.getExpLevelCost() == 3) {
			e.getEnchantsToAdd().put(Enchantment.getByName("SHOCKING"), 1);
			ItemMeta itemMeta = e.getItem().getItemMeta();
			SGEnchantment enchantmentSG = (SGEnchantment) Enchantment.getByName("SHOCKING");
			List<String> currentLore = itemMeta.getLore();
			if (currentLore == null) {
				currentLore = new ArrayList<String>();
			}
			currentLore.add(enchantmentSG.getLore(1));
			itemMeta.setLore(currentLore);
			e.getItem().setItemMeta(itemMeta);
			return;
		}
	}

	public ItemStack enchantItemSG(String enchantmentName, ItemStack item, int lvl) {
		Enchantment enchantment = Enchantment.getByName(enchantmentName);
		ItemStack itemStack = item;
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (enchantment instanceof SGEnchantment) {
			SGEnchantment enchantmentSG = (SGEnchantment) enchantment;
			List<String> currentLore = itemMeta.getLore();
			if (currentLore == null) {
				currentLore = new ArrayList<String>();
			}
			currentLore.add(enchantmentSG.getLore(lvl));
			itemMeta.setLore(currentLore);
		}
		itemMeta.addEnchant(enchantment, 1, true);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
