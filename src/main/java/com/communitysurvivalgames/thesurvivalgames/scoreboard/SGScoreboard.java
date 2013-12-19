public class SGScoreboard {

public void createArenaScoreboard(SGArena arena){
  coreboard board = arena.scoreboard;
  Objective object = board.getObjective(DisplaySlot.SIDEBAR);
  object.setDisplayName(ChatColor.RED + "The Survival Games");
  
  Score timer = object.getScore(Bukkit.getOfflinePlayer(TheSurvivalGames.cutString("Timer")));
  //TIMER.SETSCORE(ARENATIMER SOON);
  Score dead = object.getScore(Bukkit.getOfflinePlayer(TheSurvivalGames.cutString("Dead")));
   //DEAD.SETSCORE(ARENADEAD SOON);
  Score alive = object.getScore(Bukkit.getOfflinePlayer(TheSurvivalGames.cutString("Alive")));
   //ALIVE.SETSCORE(ARENAALIVE SOON);
  
  
}

public void updateScoreboard(SGArena arena){
  Scoreboard board = arena.scoreboard;
  Objective object = board.getObjective(DisplaySlot.SIDEBAR);
  object.setDisplayName(ChatColor.RED + "The Survival Games");
  
  Score timer = object.getScore(Bukkit.getOfflinePlayer(TheSurvivalGames.cutString("Timer")));
  //TIMER.SETSCORE(ARENATIMER SOON);
  Score dead = object.getScore(Bukkit.getOfflinePlayer(TheSurvivalGames.cutString("Dead")));
   //DEAD.SETSCORE(ARENADEAD SOON);
  Score alive = object.getScore(Bukkit.getOfflinePlayer(TheSurvivalGames.cutString("Alive")));
   //ALIVE.SETSCORE(ARENAALIVE SOON);
}

public void removeScoreboard(Player player) {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
}

}
