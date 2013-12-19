public class ScoreboardManager {

private Objective ob;
private Scoreboard scoreboard = null;
private Objective alive = null;

public ScoreboardManager(Plugin p) {

Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

ob = board.registerNewObjective("Alive", "dummy");
o.setDisplayName(ChatColor.RED + "The Survival Games");
o.setDisplaySlot(DisplaySlot.SIDEBAR);

this.alive = ob;
this.scoreboard = board;
}

public void showScoreboard(Player p){
clearScoreboard(p);
p.setScoreboard(this.scoreboard);
}

public void clearScoreboard(Player p){
p.setScoreboard(null);
}


}
