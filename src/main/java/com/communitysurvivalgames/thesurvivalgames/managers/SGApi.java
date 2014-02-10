/**
 * Name: SGApi.java Edited: 15 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

import java.util.Map;
import java.util.HashMap;

/**
 * SGApi The main class for getting references to other classes and objects
 * <p>
 * All Objects and classes and objects should no longer be using the singleton
 * patter. To many singletons in a project will result in large uses of memory
 * and is big cause of memory leaks if not used correctly.
 * </p>
 * <p>
 * To access another Manager just call it using the static methods provided. Eg
 * {@link com.communitysurvivalgames.thesurvivalgames.managers.KitManager}
 * SGApi.getKitManager() will return you the kit manager. DO NOT then store this
 * in a field in your class.(See relicum for the reasons)
 * </p>
 * 
 * @author TheCommunitySurvivalGames
 * @version 0.1
 */
public class SGApi {

	private static ArenaManager arenaManager;
	private static BonusManager bonusManager;
	private static KitManager kitManager;
	private static MultiWorldManager multiWorldManager;
	private static SignManager signManager;
	private static Map<SGArena, TimeManager> timeManager = new HashMap<>();
	private static PartyManager partyManager;
	private static TheSurvivalGames plugin;
	private static ScheduleManager scheduler;
	private static EnchantmentManager enchantmentManager;

	/**
	 * Gets arena manager.
	 * 
	 * @return the arena manager
	 */
	public static ArenaManager getArenaManager() {
		if (arenaManager == null)
			arenaManager = new ArenaManager();
		return arenaManager;
	}

	/**
	 * Gets bonus manager.
	 * 
	 * @return the bonus manager
	 */
	public static BonusManager getBonusManager() {
		if (bonusManager == null)
			bonusManager = new BonusManager();
		return bonusManager;
	}

	/**
	 * Gets kit manager.
	 * 
	 * @return the kit manager
	 */
	public static KitManager getKitManager() {
		if (kitManager == null)
			kitManager = new KitManager();
		return kitManager;
	}

	/**
	 * Gets multi world manager.
	 * 
	 * @return the multi world manager
	 */
	public static MultiWorldManager getMultiWorldManager() {
		if (multiWorldManager == null)
			multiWorldManager = new MultiWorldManager();
		return multiWorldManager;
	}

	/**
	 * Get MultiThread Manager
	 * 
	 * @return the schedule manager
	 */
	public static ScheduleManager getScheduler() {
		if (scheduler == null)
			scheduler = new ScheduleManager();
		return scheduler;
	}

	/**
	 * Gets sign manager.
	 * 
	 * @return the sign manager
	 */
	public static SignManager getSignManager() {
		return signManager;
	}

	/**
	 * Gets time manager.
	 * 
	 * @return the time manager
	 */
	public static TimeManager getTimeManager(SGArena a) {
                TimeManager manager = timeManager.get(a);
		if (manager == null)
			timeManager.put(a, new TimeManager(a));
		return timeManager.get(a);
	}

	public static EnchantmentManager getEnchantmentManager() {
		if(enchantmentManager == null)
			enchantmentManager = new EnchantmentManager();
		return enchantmentManager;
	}

	/**
	 * Gets party manager.
	 * 
	 * @return the party manager
	 */
	public static PartyManager getPartyManager() {
		return partyManager;
	}

	/**
	 * Used to create an instance of the main plugin in onEnable
	 * 
	 * @param survivalGames the survival games
	 */
	public static void init(TheSurvivalGames survivalGames) {
		plugin = survivalGames;
	}

	/**
	 * Gets Main plugin.
	 * 
	 * @return the plugin
	 */
	public static TheSurvivalGames getPlugin() {
		return plugin;

	}

}
