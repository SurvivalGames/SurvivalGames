package co.q64.survivalgames.runnables;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import co.q64.survivalgames.exception.ArenaNotFoundException;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.managers.SGApi;
import co.q64.survivalgames.objects.SGArena;
import co.q64.survivalgames.util.EconUtil;
import co.q64.survivalgames.TheSurvivalGames;

/**
 * Name: Scoreboard.java Edited: 8 December 2013
 *
 * @version 1.0.0
 */
public class Scoreboard implements Runnable {

	private boolean count = false;
	private final TheSurvivalGames plugin;

	private Scoreboard(TheSurvivalGames base) {
		this.plugin = SGApi.getPlugin();
	}

	public static void registerScoreboard() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getPlugin(TheSurvivalGames.class), new Scoreboard(JavaPlugin.getPlugin(TheSurvivalGames.class)), 5, 100);
	}

	private static void sendScore(Objective objective, String title, int value, boolean complete) {

		@SuppressWarnings("deprecation")
		//f bukkit
		final Score score = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.translateAlternateColorCodes('&', title)));
		score.setScore(value);
	}

	private void createScoreboard(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("Global", "dummy");
		objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("WELCOME") + ", " + player.getName()));
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

	TheSurvivalGames getPlugin() {
		return plugin;
	}

	@Override
	public void run() {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			final Objective objective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);

			//if (objective == null) {
			createScoreboard(player);
			//} else {
			//	updateScoreboard(player, false);
			//}
		}
	}

	private void updateScoreboard(Player player, boolean complete) {
		count = !count;
		final Objective objective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
		if (!SGApi.getArenaManager().isInGame(player)) {
			if (player.getWorld() != Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))
				return;
			objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("WELCOME") + ", " + player.getName()));
			sendScore(objective, "&a&l" + I18N.getLocaleString("POINTS"), 11, complete);
			sendScore(objective, "&6&l" + EconUtil.getPoints(player) + "   ", 10, complete);
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
			if (SGApi.getKitManager().getKit(player) == null)
				sendScore(objective, "Select a kit!", 4, complete);
			else
				sendScore(objective, SGApi.getKitManager().getKit(player).getName(), 4, complete);
			sendScore(objective, "&c", 3, complete);
			sendScore(objective, "&a&l" + I18N.getLocaleString("POINTS"), 2, complete);
			sendScore(objective, "&6&l" + EconUtil.getPoints(player) + "    ", 1, complete);
			return;
		}

		if (arena.getState() == SGArena.ArenaState.PRE_COUNTDOWN) {
			objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("Starting in: " + SGApi.getTimeManager(arena).getG().timeToString()) + "" + " " + SGApi.getTimeManager(arena).getG().s[1]));

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
			if (SGApi.getKitManager().getKit(player) == null)
				sendScore(objective, "Select a kit!", 4, complete);
			else
				sendScore(objective, SGApi.getKitManager().getKit(player).getName(), 4, complete);
			sendScore(objective, "&c", 3, complete);
			sendScore(objective, "&a&l" + I18N.getLocaleString("POINTS"), 2, complete);
			sendScore(objective, "&6&l" + EconUtil.getPoints(player) + "    ", 1, complete);//test
			return;

		}
		objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l" + I18N.getLocaleString("SURVIVAL_GAMES")));

		//sendScore(objective, "&bKills", arena.kills.get(player.getName()), complete);
		sendScore(objective, "&aAlive", arena.getPlayers().size(), complete);
		sendScore(objective, "&4Dead", arena.getDead(), complete);
		sendScore(objective, "&7Spectating", arena.getSpectators().size(), complete);

	}
}
