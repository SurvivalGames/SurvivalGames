package com.communitysurvivalgames.thesurvivalgames.runnables;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

/**
 * Name: Scoreboard.java Edited: 8 December 2013
 * 
 * @version 1.0.0
 */
public class Scoreboard implements Runnable {

    private final TheSurvivalGames plugin;

    private Scoreboard(TheSurvivalGames base) {
        this.plugin = SGApi.getPlugin();
   }

    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Objective objective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);

            if (objective == null) {
                createScoreboard(player);
            } else {
                updateScoreboard(player, false);
            }
        }
    }

    private void createScoreboard(Player player) {
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Global", "dummy");
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("WELCOME") + ", " + player.getDisplayName()));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        if (player.isOnline()) {
            try {
                player.setScoreboard(scoreboard);
            } catch (IllegalStateException ex) {
                Bukkit.getLogger().log(Level.SEVERE, I18N.getLocaleString("COULD_NOT_CREATE") + " " + player.getDisplayName());
                return;
            }

            updateScoreboard(player, true);
        }
    }

    private void updateScoreboard(Player player, boolean complete) {
        final Objective objective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        if (!SGApi.getArenaManager().isInGame(player)) {
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("WELCOME") + ", " + player.getDisplayName()));
            sendScore(objective, "&a&l" + I18N.getLocaleString("POINTS"), 11, complete);
            sendScore(objective, "&6&l" + getPlugin().getPlayerData(player).getPoints() + "   ", 10, complete);
            sendScore(objective, "&r", 9, complete);
            sendScore(objective, "&e&l" + I18N.getLocaleString("RANK"), 8, complete);
            sendScore(objective, getPlugin().getPlayerData(player).getRank(), 7, complete);
            sendScore(objective, "&0", 6, complete);
            sendScore(objective, "&4&l" + I18N.getLocaleString("KILLS"), 5, complete);
            sendScore(objective, "&6&l" + getPlugin().getPlayerData(player).getKills() + "  ", 4, complete);
            sendScore(objective, "&c", 3, complete);
            sendScore(objective, "&d&l" + I18N.getLocaleString("WINS"), 2, complete);
            sendScore(objective, "&6&l" + getPlugin().getPlayerData(player).getWins() + " ", 1, complete);
            return;
        }
        SGArena arena;
        try {
            arena = SGApi.getArenaManager().getArena(player);
        } catch (ArenaNotFoundException e) {
            Bukkit.getLogger().severe(e.getMessage());
            return;
        }
        if (arena.getState() == SGArena.ArenaState.WAITING_FOR_PLAYERS) {
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("WAITING_FOR_PLAYERS")));
            sendScore(objective, "&e" + I18N.getLocaleString("MAX_PLAYERS"), 14, complete);
            sendScore(objective, "&f" + arena.getMaxPlayers() + " ", 13, complete);
            sendScore(objective, "&0", 12, complete);
            sendScore(objective, "&e" + I18N.getLocaleString("MIN_PLAYERS"), 11, complete);
            sendScore(objective, "&f" + arena.getMinPlayers() + "  ", 10, complete);
            sendScore(objective, "&r", 9, complete);
            sendScore(objective, "&e" + I18N.getLocaleString("PLAYERS"), 8, complete);
            sendScore(objective, "&f" + arena.getPlayers().size() + "   ", 7, complete);
            sendScore(objective, "&f", 6, complete);
            sendScore(objective, "&4&l" + I18N.getLocaleString("CLASS"), 5, complete);
            sendScore(objective, "*null*", 4, complete); // TODO
            sendScore(objective, "&c", 3, complete);
            sendScore(objective, "&a&l" + I18N.getLocaleString("POINTS"), 2, complete);
            sendScore(objective, "&6&l" + getPlugin().getPlayerData(player).getPoints() + "    ", 1, complete);
            return;
        }

        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("SURVIVAL_GAMES")));

        /*
         * sendScore(objective, "&bKills", arena.getKills(player), complete);
         * TODO sendScore(objective, "&alive", arena.getAlive(), complete); TODO
         * sendScore(objective, "&4Dead", arena.getDead(player), complete); TODO
         * sendScore(objective, "&7Spectating", arena.getSpectating(),
         * complete); TODO sendScore(objective, "&eTime", arena.getSpectating(),
         * complete); TODO
         */
        // TODO Probably end up having something that switches the scoreboard
        // back and forth about every ~5 to showing all players and their lives
        // and this
    }

    private static void sendScore(Objective objective, String title, int value, boolean complete) {

        final Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', title)));

        if (complete && value == 0) {
            // Have to use this because the score wouldn't send otherwise
            score.setScore(-1);
        }

        score.setScore(value);
    }

    TheSurvivalGames getPlugin() {
        return plugin;
    }

    public static void registerScoreboard() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(TheSurvivalGames.getPlugin(TheSurvivalGames.class),
                new Scoreboard(TheSurvivalGames.getPlugin(TheSurvivalGames.class)),
                5,
                20);
    }
}
