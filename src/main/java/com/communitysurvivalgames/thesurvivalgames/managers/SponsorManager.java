package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.util.EconUtil;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkUtil;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu.OptionClickEvent;

public class SponsorManager {
	private final IconMenu players;
	private final IconMenu sponsor;
	private Map<String, String> inMenu = new HashMap<String, String>();
	SGArena a;

	public SponsorManager(final SGArena a) {
		this.a = a;
		sponsor = new IconMenu("Select an item to sponsor", 54, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (EconUtil.isHooked()) {
					if (!EconUtil.removePoints(event.getPlayer(), 10)) {
						event.getPlayer().sendMessage(ChatColor.RED + "You cannot afford to buy that!");
						return;
					}
				}
				Player sponsored = Bukkit.getPlayer(inMenu.get(event.getPlayer().getName()));
				inMenu.remove(event.getPlayer().getName());
				final Location loc = event.getPlayer().getLocation();
				final Location nloc = event.getPlayer().getLocation();
				a.broadcast(ChatColor.RED + "" + ChatColor.BOLD + "Look up, " + ChatColor.GRAY + ChatColor.BOLD + sponsored.getDisplayName() + ChatColor.RED + "" + ChatColor.BOLD + ", you have been sponsored!");
				FireworkEffect fEffect = FireworkEffect.builder().withColor(Color.SILVER).withFade(Color.WHITE).trail(true).flicker(false).with(Type.BALL).build();
				FireworkUtil.getCircleUtil().playFireworkLine(nloc.add(0, 50, 0), loc, fEffect, 50);
				Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

					@Override
					public void run() {
						Block block = loc.getWorld().getBlockAt(loc);
						block.setType(Material.CHEST);
						Chest chest = (Chest) block.getState();
						chest.getInventory().setItem(13, event.getItem());
						FireworkEffect fChestEffect = FireworkEffect.builder().withTrail().flicker(false).with(Type.STAR).withColor(Color.RED).withColor(Color.BLUE).withColor(Color.YELLOW).withColor(Color.GREEN).build();
						try {
							FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(loc.getWorld(), new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), fChestEffect);
						} catch (Exception e) {
							//If the firework dosen't work.... To bad
						}
					}
				}, 155L);
				event.setWillClose(true);
			}
		}, SGApi.getPlugin());
		ItemStack tmp;
		ItemMeta meta;

		/////////////////
		//     Axes    //
		/////////////////
		sponsor.setOption(0, new ItemStack(Material.WOOD_AXE), ChatColor.translateAlternateColorCodes('&', "Wooden Axe"), new String[] { ChatColor.translateAlternateColorCodes('&', "Basic, starter weapon"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$2") : ChatColor.translateAlternateColorCodes('&', "&e&l2 Points") });
		sponsor.setOption(1, new ItemStack(Material.GOLD_AXE), ChatColor.translateAlternateColorCodes('&', "Golden Axe"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eSuch Shiney"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$2") : ChatColor.translateAlternateColorCodes('&', "&e&l2 Points") });
		sponsor.setOption(2, new ItemStack(Material.STONE_AXE), ChatColor.translateAlternateColorCodes('&', "Stone Axe"), new String[] { ChatColor.translateAlternateColorCodes('&', "&bA good solid bit of damage there!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$7") : ChatColor.translateAlternateColorCodes('&', "&e&l7 Points") });
		tmp = new ItemStack(Material.WOOD_AXE);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sponsor.setOption(3, tmp, ChatColor.translateAlternateColorCodes('&', "Wooden Axe - Sharp 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dSo glowey!!!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$10") : ChatColor.translateAlternateColorCodes('&', "&e&l10 Points") });
		sponsor.setOption(4, new ItemStack(Material.IRON_AXE), ChatColor.translateAlternateColorCodes('&', "Iron Axe"), new String[] { ChatColor.translateAlternateColorCodes('&', "&aSmelted in the forges of Quantum64"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$16") : ChatColor.translateAlternateColorCodes('&', "&e&l16 Points") });
		tmp = new ItemStack(Material.STONE_AXE);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sponsor.setOption(5, tmp, ChatColor.translateAlternateColorCodes('&', "Stone Axe - Sharp 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&2Mre damage than an iron axe!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$22") : ChatColor.translateAlternateColorCodes('&', "&e&l22 Points") });
		tmp = new ItemStack(Material.STONE_AXE);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		sponsor.setOption(6, tmp, ChatColor.translateAlternateColorCodes('&', "Stone Axe - Sharp 2"), new String[] { ChatColor.translateAlternateColorCodes('&', "&3Mre damage than a diamond axe!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$35") : ChatColor.translateAlternateColorCodes('&', "&e&l35 Points") });
		tmp = new ItemStack(Material.IRON_AXE);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sponsor.setOption(7, tmp, ChatColor.translateAlternateColorCodes('&', "&6Rapture"), new String[] { ChatColor.translateAlternateColorCodes('&', "&5*Infused with magic*"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$40") : ChatColor.translateAlternateColorCodes('&', "&e&l40 Points") });
		tmp = new ItemStack(Material.DIAMOND_AXE);
		meta = tmp.getItemMeta();
		meta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
		tmp.setItemMeta(meta);
		sponsor.setOption(8, tmp, ChatColor.translateAlternateColorCodes('&', "&6Firestorm"), new String[] { ChatColor.translateAlternateColorCodes('&', "&0Plazma Hardened"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$70") : ChatColor.translateAlternateColorCodes('&', "&e&l70 Points") });

		/////////////////
		//    Swords   //
		/////////////////
		sponsor.setOption(9, new ItemStack(Material.WOOD_SWORD), ChatColor.translateAlternateColorCodes('&', "Wooden Sword"), new String[] { ChatColor.translateAlternateColorCodes('&', "Rough-cut wooden weapon"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$4") : ChatColor.translateAlternateColorCodes('&', "&e&l4 Points") });
		sponsor.setOption(10, new ItemStack(Material.STONE_SWORD), ChatColor.translateAlternateColorCodes('&', "Stone Sword"), new String[] { ChatColor.translateAlternateColorCodes('&', "&bHand cut stone!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$15") : ChatColor.translateAlternateColorCodes('&', "&e&l15 Points") });
		tmp = new ItemStack(Material.WOOD_SWORD);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sponsor.setOption(11, tmp, ChatColor.translateAlternateColorCodes('&', "Wooden Sword - Sharp 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dMagical Damage"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$25") : ChatColor.translateAlternateColorCodes('&', "&e&l25 Points") });
		sponsor.setOption(12, new ItemStack(Material.IRON_SWORD), ChatColor.translateAlternateColorCodes('&', "Iron Sword"), new String[] { ChatColor.translateAlternateColorCodes('&', "&aCast in the forges of Quantum64"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$16") : ChatColor.translateAlternateColorCodes('&', "&e&l16 Points") });
		tmp = new ItemStack(Material.STONE_SWORD);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sponsor.setOption(13, tmp, ChatColor.translateAlternateColorCodes('&', "Stone Sword - Sharp 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&2Mre damage than an iron sword!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$37") : ChatColor.translateAlternateColorCodes('&', "&e&l37 Points") });
		tmp = new ItemStack(Material.STONE_SWORD);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		sponsor.setOption(14, tmp, ChatColor.translateAlternateColorCodes('&', "Stone Sword - Sharp 2"), new String[] { ChatColor.translateAlternateColorCodes('&', "&3Mre damage than a diamond sword!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$44") : ChatColor.translateAlternateColorCodes('&', "&e&l44 Points") });
		tmp = new ItemStack(Material.IRON_SWORD);
		tmp.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		sponsor.setOption(15, tmp, ChatColor.translateAlternateColorCodes('&', "&6Eclipse"), new String[] { ChatColor.translateAlternateColorCodes('&', "&5Brings death and destruction"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$50") : ChatColor.translateAlternateColorCodes('&', "&e&l50 Points") });
		tmp = new ItemStack(Material.DIAMOND_SWORD);
		tmp.addEnchantment(EnchantmentManager.shocking, 1);
		sponsor.setOption(16, tmp, ChatColor.translateAlternateColorCodes('&', "&6Corruption"), new String[] { ChatColor.translateAlternateColorCodes('&', "&0Infused with the power of magical Flux"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$82") : ChatColor.translateAlternateColorCodes('&', "&e&l82 Points") });
		tmp = new ItemStack(Material.DIAMOND_SWORD);
		tmp.addEnchantment(EnchantmentManager.shocking, 1);
		sponsor.setOption(16, tmp, ChatColor.translateAlternateColorCodes('&', "&6Razor Sharp Dagger"), new String[] { ChatColor.translateAlternateColorCodes('&', "&0Yuu might get cut just by looking at this thing..."), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$90") : ChatColor.translateAlternateColorCodes('&', "&e&l90 Points") });

		/////////////////
		//   Helmets   //
		/////////////////
		sponsor.setOption(18, new ItemStack(Material.LEATHER_HELMET), ChatColor.translateAlternateColorCodes('&', "Leather Helmet"), new String[] { ChatColor.translateAlternateColorCodes('&', "&bBasic protection - Better than nothing"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$4") : ChatColor.translateAlternateColorCodes('&', "&e&l4 Points") });
		sponsor.setOption(19, new ItemStack(Material.IRON_HELMET), ChatColor.translateAlternateColorCodes('&', "Iron Helmet"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eIronclad protection - For the brave"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$15") : ChatColor.translateAlternateColorCodes('&', "&e&l15 Points") });
		tmp = new ItemStack(Material.LEATHER_HELMET);
		tmp.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sponsor.setOption(20, tmp, ChatColor.translateAlternateColorCodes('&', "Leather Helmet - Prot 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dInstilled Magic"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$25") : ChatColor.translateAlternateColorCodes('&', "&e&l25 Points") });
		tmp = new ItemStack(Material.IRON_HELMET);
		tmp.addEnchantment(Enchantment.THORNS, 1);
		sponsor.setOption(21, tmp, ChatColor.translateAlternateColorCodes('&', "&6Death's Deflection"), new String[] { ChatColor.translateAlternateColorCodes('&', "&2Caution - Spiky surface"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$37") : ChatColor.translateAlternateColorCodes('&', "&e&l37 Points") });

		/////////////////
		// Chestplates //
		/////////////////
		sponsor.setOption(22, new ItemStack(Material.LEATHER_CHESTPLATE), ChatColor.translateAlternateColorCodes('&', "Leather Chestplate"), new String[] { ChatColor.translateAlternateColorCodes('&', "&bBasic protection - Better than nothing"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$4") : ChatColor.translateAlternateColorCodes('&', "&e&l4 Points") });
		sponsor.setOption(23, new ItemStack(Material.IRON_CHESTPLATE), ChatColor.translateAlternateColorCodes('&', "Iron Chestplate"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eIronclad protection - For the brave"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$15") : ChatColor.translateAlternateColorCodes('&', "&e&l15 Points") });
		tmp = new ItemStack(Material.LEATHER_CHESTPLATE);
		tmp.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sponsor.setOption(24, tmp, ChatColor.translateAlternateColorCodes('&', "Leather Chestplate - Prot 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dInstilled Magic"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$25") : ChatColor.translateAlternateColorCodes('&', "&e&l25 Points") });
		tmp = new ItemStack(Material.IRON_CHESTPLATE);
		tmp.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
		sponsor.setOption(25, tmp, ChatColor.translateAlternateColorCodes('&', "&6Soul Keeper"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dBane of Bows"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$37") : ChatColor.translateAlternateColorCodes('&', "&e&l37 Points") });
		tmp = new ItemStack(Material.DIAMOND_CHESTPLATE);
		tmp.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sponsor.setOption(26, tmp, ChatColor.translateAlternateColorCodes('&', "&6Fusion Ward"), new String[] { ChatColor.translateAlternateColorCodes('&', "&2For the Godlike Warriors"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$97") : ChatColor.translateAlternateColorCodes('&', "&e&l97 Points") });

		/////////////////
		//    Boots    //
		/////////////////
		sponsor.setOption(27, new ItemStack(Material.LEATHER_BOOTS), ChatColor.translateAlternateColorCodes('&', "Leather Boots"), new String[] { ChatColor.translateAlternateColorCodes('&', "&bBasic protection - Better than nothing"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$4") : ChatColor.translateAlternateColorCodes('&', "&e&l4 Points") });
		sponsor.setOption(28, new ItemStack(Material.IRON_BOOTS), ChatColor.translateAlternateColorCodes('&', "Iron Boots"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eIronclad protection - For the brave"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$15") : ChatColor.translateAlternateColorCodes('&', "&e&l15 Points") });
		tmp = new ItemStack(Material.LEATHER_BOOTS);
		tmp.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sponsor.setOption(29, tmp, ChatColor.translateAlternateColorCodes('&', "Leather Boots - Prot 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dInstilled Magic"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$25") : ChatColor.translateAlternateColorCodes('&', "&e&l25 Points") });
		tmp = new ItemStack(Material.IRON_BOOTS);
		tmp.addEnchantment(Enchantment.PROTECTION_FALL, 2);
		sponsor.setOption(30, tmp, ChatColor.translateAlternateColorCodes('&', "&6Silverlight"), new String[] { ChatColor.translateAlternateColorCodes('&', "&aNow... You can fly!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$37") : ChatColor.translateAlternateColorCodes('&', "&e&l37 Points") });

		/////////////////
		//   Leggings  //
		/////////////////
		sponsor.setOption(31, new ItemStack(Material.LEATHER_LEGGINGS), ChatColor.translateAlternateColorCodes('&', "Leather Leggings"), new String[] { ChatColor.translateAlternateColorCodes('&', "&bBasic protection - Better than nothing"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$4") : ChatColor.translateAlternateColorCodes('&', "&e&l4 Points") });
		sponsor.setOption(32, new ItemStack(Material.IRON_LEGGINGS), ChatColor.translateAlternateColorCodes('&', "Iron Leggings"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eIronclad protection - For the brave"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$15") : ChatColor.translateAlternateColorCodes('&', "&e&l15 Points") });
		tmp = new ItemStack(Material.LEATHER_LEGGINGS);
		tmp.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sponsor.setOption(33, tmp, ChatColor.translateAlternateColorCodes('&', "Leather Leggings - Prot 1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dInstilled Magic"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$25") : ChatColor.translateAlternateColorCodes('&', "&e&l25 Points") });
		tmp = new ItemStack(Material.IRON_LEGGINGS);
		tmp.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
		sponsor.setOption(34, tmp, ChatColor.translateAlternateColorCodes('&', "&6Stormcaller"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dTNT got nothin' on me bro"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$37") : ChatColor.translateAlternateColorCodes('&', "&e&l37 Points") });
		tmp = new ItemStack(Material.DIAMOND_LEGGINGS);
		tmp.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		sponsor.setOption(35, tmp, ChatColor.translateAlternateColorCodes('&', "&6Desolation"), new String[] { ChatColor.translateAlternateColorCodes('&', "&2For the Godlike Warriors"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$97") : ChatColor.translateAlternateColorCodes('&', "&e&l97 Points") });
		
		/////////////////
		//     Food    //
		/////////////////
		sponsor.setOption(36, new ItemStack(Material.SPIDER_EYE), ChatColor.translateAlternateColorCodes('&', "Spider Eye"), new String[] { ChatColor.translateAlternateColorCodes('&', "&4If you have nothing else to eat..."), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$1") : ChatColor.translateAlternateColorCodes('&', "&e&l1 Point") });
		sponsor.setOption(37, new ItemStack(Material.BREAD, 2), ChatColor.translateAlternateColorCodes('&', "Bread"), new String[] { ChatColor.translateAlternateColorCodes('&', "&6Fresh baked bread - Just for you!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$4") : ChatColor.translateAlternateColorCodes('&', "&e&l4 Points") });
		sponsor.setOption(38, new ItemStack(Material.COOKIE, 6), ChatColor.translateAlternateColorCodes('&', "Cookie"), new String[] { ChatColor.translateAlternateColorCodes('&', "&fChocolate Chip!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$9") : ChatColor.translateAlternateColorCodes('&', "&e&l9 Points") });
		sponsor.setOption(39, new ItemStack(Material.PORK, 2), ChatColor.translateAlternateColorCodes('&', "Cooked Pork"), new String[] { ChatColor.translateAlternateColorCodes('&', "&dPiggie Noooooo"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$14") : ChatColor.translateAlternateColorCodes('&', "&e&l14 Point") });
		sponsor.setOption(40, new ItemStack(Material.COOKED_BEEF, 2), ChatColor.translateAlternateColorCodes('&', "Steak"), new String[] { ChatColor.translateAlternateColorCodes('&', "&cWe're gonna' eat well tonight!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$16") : ChatColor.translateAlternateColorCodes('&', "&e&l16 Points") });
		sponsor.setOption(41, new ItemStack(Material.MUSHROOM_SOUP, 4), ChatColor.translateAlternateColorCodes('&', "&6Mushroom Stew"), new String[] { ChatColor.translateAlternateColorCodes('&', "&aRegen Health"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$20") : ChatColor.translateAlternateColorCodes('&', "&e&l20 Points") });
		sponsor.setOption(42, new ItemStack(Material.GOLDEN_APPLE, 2), ChatColor.translateAlternateColorCodes('&', "&6Golden Apple"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eExtra hearts for days"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$60") : ChatColor.translateAlternateColorCodes('&', "&e&l60 Points") });
		sponsor.setOption(43, new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1), ChatColor.translateAlternateColorCodes('&', "&6Golden Apple - T2"), new String[] { ChatColor.translateAlternateColorCodes('&', "&eNOTCH AAAPPPLLLLEEEEE"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$150") : ChatColor.translateAlternateColorCodes('&', "&e&l150 Points") });
		
		/////////////////
		//   Potions   //
		/////////////////
		Potion p;
		ItemStack potionItem;
		p = new Potion(PotionType.REGEN, 1);
		p.extend();
		potionItem = new ItemStack(Material.POTION);
		p.apply(potionItem);
		sponsor.setOption(45, potionItem, ChatColor.translateAlternateColorCodes('&', "&6Regen Potion - T1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&6Fill up those hearts!"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$10") : ChatColor.translateAlternateColorCodes('&', "&e&l10 Points") });
		p = new Potion(PotionType.REGEN, 2);
		potionItem = new ItemStack(Material.POTION);
		p.apply(potionItem);
		sponsor.setOption(46, potionItem, ChatColor.translateAlternateColorCodes('&', "&6Regen Potion - T1"), new String[] { ChatColor.translateAlternateColorCodes('&', "&6Fill up those hearts! - Reloaded"), EconUtil.isHooked() ? ChatColor.translateAlternateColorCodes('&', "&e&l$20") : ChatColor.translateAlternateColorCodes('&', "&e&l20 Points") });
		
		players = new IconMenu("Select the player to sponsor", 27, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.REDSTONE_BLOCK) {
					event.setWillClose(true);
					return;
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

					@Override
					public void run() {
						inMenu.put(event.getPlayer().getName(), event.getItem().getItemMeta().getDisplayName());
						sponsor.open(event.getPlayer());

					}
				}, 2L);
				event.setWillClose(true);
			}
		}, SGApi.getPlugin());
	}

	public void sponsor(Player sender) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (String s : a.getPlayers()) {
			ItemStack item = new ItemStack(Material.EMERALD, (int) Bukkit.getPlayer(s).getHealth());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(s);
			item.setItemMeta(meta);
			items.add(item);
		}
		Collections.sort(items, new Comparator<ItemStack>() {
			public int compare(ItemStack o1, ItemStack o2) {
				return Integer.compare(o1.getAmount(), o2.getAmount());
			}
		});
		for (int i = 0; i < items.size(); i++) {
			players.setOption(i, items.get(i), items.get(i).getItemMeta().getDisplayName(), new String[] { ChatColor.translateAlternateColorCodes('&', "&e&lClick to sponsor this person!"), ChatColor.translateAlternateColorCodes('&', "&aNote: Health = Amount of emeralds") });
		}
		players.setOption(26, new ItemStack(Material.REDSTONE_BLOCK), ChatColor.RED + "Cancle", ChatColor.translateAlternateColorCodes('&', "&e&lExits out of this menu"));
		players.open(sender);
	}
}
