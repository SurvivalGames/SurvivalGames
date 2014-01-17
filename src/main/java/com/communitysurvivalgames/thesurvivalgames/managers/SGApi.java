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

    public static ArenaManager getArenaManager() {
        if (arenaManager == null)
            arenaManager = new ArenaManager();
        return arenaManager;
    }

    public static BonusManager getBonusManager() {
        if (bonusManager == null)
            bonusManager = new BonusManager();
        return bonusManager;
    }

    public static KitManager getKitManager() {
        if (kitManager == null)
            kitManager = new KitManager();
        return kitManager;
    }

    public static MultiWorldManager getMultiWorldManager() {
        if (multiWorldManager == null)
            multiWorldManager = new MultiWorldManager();
        return multiWorldManager;
    }

    public static SignManager getSignManager() {
        return signManager;
    }

    public static TimeManager getTimeManager() {
        if (timeManager == null)
            timeManager = new TimeManager();
        return timeManager;
    }

    public static PartyManager getPartyManager() {
        return partyManager;
    }

    public static void init(TheSurvivalGames survivalGames) {
        plugin = survivalGames;
    }

    public static TheSurvivalGames getPlugin() {
        return plugin;

    }

}
