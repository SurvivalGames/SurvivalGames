package me.theepicbutterstudios.thesurvivalgames;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "sg_player")
public class PlayerData {
	@Id
    private int id;
    @NotNull
    private String playerName;
    @Length(max = 30)
    @NotEmpty
    private String name;
 
    @NotEmpty
    private String Test;
 
    public void setId(int id) {
        this.id = id;
    }
 
    public int getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
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
 
    public String getTest(){
        return Test;
    }
 
    public void setTest(String test){
        this.Test = test;
    }
}
