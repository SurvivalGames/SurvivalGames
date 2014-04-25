package co.q64.paradisesurvivalgames.util.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.multiworld.SGWorld;
import co.q64.paradisesurvivalgames.objects.SGArena;
import co.q64.paradisesurvivalgames.util.gui.IconMenu.OptionClickEvent;
import co.q64.paradisesurvivalgames.util.gui.anvil.AnvilGUI;

public class AdminMenu {
	private SGArena arena;
	private SGWorld map;
	private Player p;

	private IconMenu mainMenu;
	private IconMenu downloadMenu;
	private IconMenu arenaMenu;
	private IconMenu mapMenu;
	private IconMenu manageArena;
	private IconMenu manageMap;

	public AdminMenu() {
		mainMenu = new IconMenu("Select a task", 9, false, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.COMPASS) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							arenaMenu.open(p);
						}
					}, 10L);

				}
				
				if (event.getItem().getType() == Material.PAINTING){
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							mapMenu.open(p);
						}
					}, 10L);
				}

				if (event.getItem().getType() == Material.MAP) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							downloadMenu.open(p);
						}
					}, 10L);
				}

				if (event.getItem().getType() == Material.WATCH) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							SGArena a = SGApi.getArenaManager().createLobby(p);
							p.sendMessage(SGApi.getArenaManager().getPrefix() + I18N.getLocaleString("CREATING_LOBBY") + " " + a.getId());
						}
					}, 10L);

				}

				if (event.getItem().getType() == Material.EMPTY_MAP) {

					final AnvilGUI gui = new AnvilGUI(event.getPlayer(), new AnvilGUI.AnvilClickEventHandler() {
						@Override
						public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
							if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
								event.setWillClose(true);
								event.setWillDestroy(true);
								SGApi.getArenaManager().createWorldFromImport(p, event.getName(), event.getName());

							} else {
								event.setWillClose(false);
								event.setWillDestroy(false);
							}
						}
					});
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							ItemStack itemStack = new ItemStack(Material.NAME_TAG);
							ItemMeta im = itemStack.getItemMeta();
							im.setDisplayName(String.valueOf("Map world name"));
							itemStack.setItemMeta(im);
							gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
							gui.open();
						}
					}, 10L);
					event.setWillClose(true);

				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, SGApi.getPlugin());

		mainMenu.setOption(0, new ItemStack(Material.COMPASS), "Manage Arenas", "Click to manage arenas");
		mainMenu.setOption(1, new ItemStack(Material.WATCH), "Create Arena", "Click to create an arena with the spawn point where you are standing");
		mainMenu.setOption(2, new ItemStack(Material.MAP), "Download Maps", "Click to download game ready maps");
		mainMenu.setOption(3, new ItemStack(Material.EMPTY_MAP), "Import Maps", "Import a custom map and set it up yourself!");
		mainMenu.setOption(4, new ItemStack(Material.PAINTING), "Manage Maps", "Click to manage imported/downloaded maps");

		arenaMenu = new IconMenu("Select an arena to manage", 54, false, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.CHEST) {
					try {

						arena = SGApi.getArenaManager().getArena(Integer.parseInt(event.getName().replaceAll("[^0-9]", "")));
					} catch (NumberFormatException | ArenaNotFoundException e) {
						p.sendMessage("Error: Arena " + event.getName().replaceAll("[^0-9]", "") + "is invalid");
						return;
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							manageArena.open(event.getPlayer());
						}
					}, 10L);

				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, SGApi.getPlugin());

		int i = 0;
		for (SGArena a : SGApi.getArenaManager().getArenas()) {
			arenaMenu.setOption(i, new ItemStack(Material.CHEST), "Arena " + a.getId(), "Click for options!");
			i++;
		}

		manageArena = new IconMenu("Select an action for arena", 18, false, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.GOLD_INGOT) {
					arena.forceStart();
				}
				if (event.getItem().getType() == Material.REDSTONE) {
					arena.end();
				}
				if (event.getItem().getType() == Material.DIAMOND_BARDING) {
					final AnvilGUI gui = new AnvilGUI(event.getPlayer(), new AnvilGUI.AnvilClickEventHandler() {
						@Override
						public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
							if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
								event.setWillClose(true);
								event.setWillDestroy(true);
								int max;
								try {
									max = Integer.parseInt(event.getName());
								} catch (NumberFormatException e) {
									p.sendMessage("That's not a number :/");
									return;
								}
								p.sendMessage("Max players set for arena " + arena.getId() + ": " + max);
								arena.setMaxPlayers(max);
							} else {
								event.setWillClose(false);
								event.setWillDestroy(false);
							}
						}
					});

					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							ItemStack itemStack = new ItemStack(Material.NAME_TAG);
							ItemMeta im = itemStack.getItemMeta();
							itemStack.setAmount(arena.getMaxPlayers());
							im.setDisplayName(String.valueOf(arena.getMaxPlayers()));
							im.setLore(Arrays.asList("Current Max Players"));
							itemStack.setItemMeta(im);
							gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
							gui.open();
						}
					}, 10L);

				}

				if (event.getItem().getType() == Material.GOLD_BARDING) {
					final AnvilGUI gui = new AnvilGUI(event.getPlayer(), new AnvilGUI.AnvilClickEventHandler() {
						@Override
						public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
							if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
								event.setWillClose(true);
								event.setWillDestroy(true);
								int min;
								try {
									min = Integer.parseInt(event.getName());
								} catch (NumberFormatException e) {
									p.sendMessage("That's not a number :/");
									return;
								}
								p.sendMessage("Min players set for arena " + arena.getId() + ": " + min);
								arena.setMinPlayers(min);
							} else {
								event.setWillClose(false);
								event.setWillDestroy(false);
							}
						}
					});

					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							ItemStack itemStack = new ItemStack(Material.NAME_TAG);
							itemStack.setAmount(arena.getMinPlayers());
							ItemMeta im = itemStack.getItemMeta();
							im.setDisplayName(String.valueOf(arena.getMinPlayers()));
							im.setLore(Arrays.asList("Current Min Players"));
							itemStack.setItemMeta(im);
							gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
							gui.open();
						}
					}, 10L);

				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, SGApi.getPlugin());

		manageArena.setOption(0, new ItemStack(Material.GOLD_INGOT), "Force Start", "Click here to force start the arena");
		manageArena.setOption(1, new ItemStack(Material.REDSTONE), "Force Stop", "Click here to force end the arena");
		manageArena.setOption(9, new ItemStack(Material.DIAMOND_BARDING), "Set max players", "Click here, then input the new value in the Anvil GUI");
		manageArena.setOption(10, new ItemStack(Material.GOLD_BARDING), "Set min players", "Click here, then input the new value in the Anvil GUI");

		mapMenu = new IconMenu("Select a map to manage", 54, false, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.MAP) {
					map = SGApi.getMultiWorldManager().worldForName(event.getName());
					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							manageMap.open(event.getPlayer());
						}
					}, 10L);

				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, SGApi.getPlugin());

		i = 0;
		for (SGWorld w : SGApi.getMultiWorldManager().getWorlds()) {
			mapMenu.setOption(i, new ItemStack(Material.MAP), w.getDisplayName(), "Click for options!");
			i++;
		}
		
		manageMap = new IconMenu("Select an action for map", 18, false, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.COMPASS) {
					final AnvilGUI gui = new AnvilGUI(event.getPlayer(), new AnvilGUI.AnvilClickEventHandler() {
						@Override
						public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
							if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
								event.setWillClose(true);
								event.setWillDestroy(true);
								int gracePeriod;
								try {
									gracePeriod = Integer.parseInt(event.getName());
								} catch (NumberFormatException e) {
									p.sendMessage("That's not a number :/");
									return;
								}
								p.sendMessage("Grace Period for Map: " + map.getDisplayName() + ": " + gracePeriod);
								map.setGracePeriod(gracePeriod);
							} else {
								event.setWillClose(false);
								event.setWillDestroy(false);
							}
						}
					});

					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							ItemStack itemStack = new ItemStack(Material.NAME_TAG);
							ItemMeta im = itemStack.getItemMeta();
							itemStack.setAmount(map.getGracePeriod());
							im.setDisplayName(String.valueOf(map.getGracePeriod()));
							im.setLore(Arrays.asList("Current Grace Period"));
							itemStack.setItemMeta(im);
							gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
							gui.open();
						}
					}, 10L);

				}
				if (event.getItem().getType() == Material.NAME_TAG) {
					final AnvilGUI gui = new AnvilGUI(event.getPlayer(), new AnvilGUI.AnvilClickEventHandler() {
						@Override
						public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
							if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
								event.setWillClose(true);
								event.setWillDestroy(true);
								String displayName = event.getName();
								p.sendMessage("Map Display Name changed from: " + map.getDisplayName() + " to: " + displayName);
								map.setDisplayName(displayName);
							} else {
								event.setWillClose(false);
								event.setWillDestroy(false);
							}
						}
					});

					Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

						@Override
						public void run() {
							ItemStack itemStack = new ItemStack(Material.NAME_TAG);
							ItemMeta im = itemStack.getItemMeta();
							itemStack.setAmount(1);
							im.setDisplayName(String.valueOf(map.getDisplayName()));
							im.setLore(Arrays.asList("Current Display Name"));
							itemStack.setItemMeta(im);
							gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, itemStack);
							gui.open();
						}
					}, 10L);

				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}
		}, SGApi.getPlugin());

		manageMap.setOption(0, new ItemStack(Material.COMPASS), "Set Grace Period", "Click here, then input the new value in the Anvil GUI");
		manageMap.setOption(1, new ItemStack(Material.NAME_TAG), "Set Map Display Name", "Click here, then input the new value in the Anvil GUI");
		
		downloadMenu = new IconMenu("Select an map to download", 54, false, new IconMenu.OptionClickEventHandler() {

			@Override
			public void onOptionClick(final OptionClickEvent event) {
				if (event.getItem().getType() == Material.EMPTY_MAP) {
					String mapName = event.getName().substring(5);
					SGApi.getPlugin().getTracker().trackEvent("Map Download", mapName);
					SGApi.getArenaManager().createWorldFromDownload(p, mapName, mapName);
				}
				event.setWillClose(true);
				event.setWillDestroy(true);
			}

		}, SGApi.getPlugin());

		try {
			FileUtils.copyURLToFile(new URL("http://sg.q64.co/maplist.txt"), new File(SGApi.getPlugin().getDataFolder(), "maplist.txt"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scanner scanner;
		try {
			scanner = new Scanner(new File(SGApi.getPlugin().getDataFolder(), "maplist.txt"));
			i = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				downloadMenu.setOption(i, new ItemStack(Material.EMPTY_MAP), "Map: " + line, "Click to download and install map!");
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

	}

	public void display(Player p) {
		if (p.isOp() || p.hasPermission("sg.admin")) {
			this.p = p;
			mainMenu.open(p);
		} else {
			p.sendMessage(ChatColor.RED + "You don't have the permission [SG.ADMIN]");
		}
	}
}
