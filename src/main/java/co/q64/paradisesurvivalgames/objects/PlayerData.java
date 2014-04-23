//PLEASE DON'T EDIT THIS CLASS - Quantum64

package co.q64.paradisesurvivalgames.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import co.q64.paradisesurvivalgames.util.PlayerNameUtil;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

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
	private int kills;
	@NotNull
	private String playerName;
	@NotNull
	private int points;
	@NotEmpty
	private String rank;
	@NotNull
	private int wins;

	public PlayerData() {
		//TODO Just no...
	}

	public PlayerData(Player p) {
		this.playerName = p.getUniqueId().toString();
		this.kills = 0;
		this.points = 0;
		this.rank = "&7M";
		if (PlayerNameUtil.getDevs().contains(p.getName()) || PlayerNameUtil.getAwesomePeople().contains(p.getName())) {
			this.setRank("&d&lDeveloper");
		}
	}

	public void addKill() {
		setKills(getKills() + 1);
	}

	public void addKills(int kills) {
		setKills(getKills() + kills);
	}

	public void addPoints(int points) {
		setPoints(getPoints() + points);
	}

	public void addWin() {
		setWins(getWins() + 1);
	}

	public void addWins(int wins) {
		setKills(getKills() + wins);
	}

	public int getId() {
		return id;
	}

	public int getKills() {
		return kills;
	}

	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(playerName);
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPoints() {
		return points;
	}

	public String getRank() {
		return rank;
	}

	public int getWins() {
		return wins;
	}

	//
	// End persistence code
	//

	public void removeKills(int kills) {
		setKills(getKills() - kills);
	}

	public void removePoints(int points) {
		setPoints(getPoints() - points);
	}

	public void removeWins(int wins) {
		setKills(getKills() - wins);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setPlayer(Player player) {
		this.playerName = player.getName();
	}

	public void setPlayerName(String ply) {
		this.playerName = ply;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setRank(String rank) {
		if (rank.length() > 16)
			rank = rank.substring(0, 16);
		this.rank = rank;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}
}
