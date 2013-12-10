package me.theepicbutterstudios.thesurvivalgames.objects;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
 
    public int getPoints(){
        return points;
    }
 
    public void setPoints(int points){
        this.points = points;
    }
    
    //
    // End persistence code
    //
}
