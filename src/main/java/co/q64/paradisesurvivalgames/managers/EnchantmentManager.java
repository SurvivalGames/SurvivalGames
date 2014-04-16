package co.q64.paradisesurvivalgames.managers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import co.q64.paradisesurvivalgames.enchantment.DedicationEnchantment;
import co.q64.paradisesurvivalgames.enchantment.SGEnchantment;
import co.q64.paradisesurvivalgames.enchantment.ShockingEnchantment;
import co.q64.paradisesurvivalgames.enchantment.UndroppableEnchantment;
import co.q64.paradisesurvivalgames.enchantment.UnenchantableEnchantment;

public class EnchantmentManager {

	private static DedicationEnchantment dedication = new DedicationEnchantment(122);
	private static ShockingEnchantment shocking = new ShockingEnchantment(120);
	private static UndroppableEnchantment undroppable = new UndroppableEnchantment(123);
	private static UnenchantableEnchantment unenchantable = new UnenchantableEnchantment(121);

	public static DedicationEnchantment getDedication() {
		return dedication;
	}

	public static ShockingEnchantment getShocking() {
		return shocking;
	}

	public static UndroppableEnchantment getUndroppable() {
		return undroppable;
	}

	public static UnenchantableEnchantment getUnenchantable() {
		return unenchantable;
	}

	public static void setDedication(final DedicationEnchantment dedication) {
		EnchantmentManager.dedication = dedication;
	}

	public static void setShocking(final ShockingEnchantment shocking) {
		EnchantmentManager.shocking = shocking;
	}

	public static void setUndroppable(final UndroppableEnchantment undroppable) {
		EnchantmentManager.undroppable = undroppable;
	}

	public static void setUnenchantable(final UnenchantableEnchantment unenchantable) {
		EnchantmentManager.unenchantable = unenchantable;
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

	public void registerAll() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Enchantment.getByName("SHOCKING") != null)
			return;
		Enchantment.registerEnchantment(getShocking());
		Enchantment.registerEnchantment(getUnenchantable());
		Enchantment.registerEnchantment(getDedication());
		Enchantment.registerEnchantment(getUndroppable());
	}
}
