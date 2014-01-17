/**
 * Name: SGApi.java Edited: 15 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;

public class SGApi {

    private static ArenaManager arenaManager;
    private static BonusManager bonusManager;
    private static KitManager kitManager;
    private static MultiWorldManager multiWorldManager;
    private static SignManager signManager;
    private static TimeManager timeManager;
    private static PartyManager partyManager;
    private static TheSurvivalGames plugin;

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
    public static TimeManager getTimeManager() {
        if (timeManager == null)
            timeManager = new TimeManager();
        return timeManager;
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
