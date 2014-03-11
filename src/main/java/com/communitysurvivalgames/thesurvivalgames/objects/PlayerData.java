//PLEASE DON'T EDIT THIS CLASS - Quantum64
package com.communitysurvivalgames.thesurvivalgames.objects;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.util.PlayerNameUtil;

@Entity()
@Table(name = "sg_player")
public class PlayerData {

    //
    // Start persistence code
    // WARNING: DO NOT EDIT
    //
    @Id
    private int id;

    @NotNull
    private String playerName;
    @NotNull
    private int points;
    @NotNull
    private int kills;
    @NotNull
    private int wins;
    @NotEmpty
    private String rank;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public int getKills() {
        return kills;
    }

    public int getWins() {
        return wins;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String ply) {
        this.playerName = ply;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(playerName);
    }

    public void setPlayer(Player player) {
        this.playerName = player.getName();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    void setKills(int kills) {
        this.kills = kills;
    }

    void setWins(int wins) {
        this.wins = wins;
    }

    //
    // End persistence code
    //

    public PlayerData() {
        //TODO Just no...
    }

    public PlayerData(Player p) {
        this.playerName = p.getName();
        this.kills = 0;
        this.points = 0;
        this.rank = "&7[M]";
        if (PlayerNameUtil.getDevs().contains(p.getName()) || PlayerNameUtil.getAwesomePeople().contains(p.getName())) {
            this.setRank("&d&lDeveloper");
        }
    }

    public void addPoints(int points) {
        setPoints(getPoints() + points);
    }

    public void removePoints(int points) {
        setPoints(getPoints() - points);
    }

    public void addKill() {
        setKills(getKills() + 1);
    }

    public void addKills(int kills) {
        setKills(getKills() + kills);
    }

    public void removeKills(int kills) {
        setKills(getKills() - kills);
    }

    public void addWin() {
        setWins(getWins() + 1);
    }

    public void addWins(int wins) {
        setKills(getKills() + wins);
    }

    public void removeWins(int wins) {
        setKills(getKills() - wins);
    }
}
