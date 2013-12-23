package com.communitysurvivalgames.thesurvivalgames.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BonusManager {

    ArrayList<Bonus> bonus = new ArrayList<Bonus>();
    public static BonusManager bonusManager = new BonusManager();

    public static BonusManager getManager() {
        return bonusManager;
    }

    public void registerAll() {
        register("Who's in charge NOW?!", new String[]{"Kill an Administrator in the survival games"}, 100);
        register("Hope you weren't recording!", new String[]{"Kill an Administrator in the survival games"}, 500);
        register("Slow Reflexes", new String[]{"Die within the first 60 seconds of a SG match."}, 20);
        register("Slowpokes!", new String[]{"Win a SG game using TNT"}, 75);
        register("High Five!", new String[]{"Kill s player with your fists"}, 300);
        register("Fallout 4", new String[]{"Place 10 TNT within 10 secconds"}, 200);
        register("I Need to AXE You Something", new String[]{"Win a SG Match using axes as your only weapon."}, 200);
        register("Rainbow Chicken Dance", new String[]{"Win a SG match by killing the last player with raw chicken"}, 1000);
        register("Y U KILL UR CREATOR", new String[]{"Kill a developer in an SG match"}, 700);
        register("Are you God?", new String[]{"Kill Quantum64 in a SG match"}, Integer.MAX_VALUE);
        //TODO More!
    }

    public void register(String name, String[] lore, int points) {
        bonus.add(new Bonus(name, lore, points, bonus.size()));
    }

    public void trigger(Player p, int id) {
        Bonus b = bonus.get(id);
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4" + p.getDisplayName() + ": &e&l" + b.getName()));
        for (int i = 0; i < b.getLore().length; i++) {
            Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&3    " + b.getLore()));
        }
    }

    public class Bonus {
        int id;
        String name;
        String[] lore;
        int points;

        public Bonus(String name, String[] lore, int points, int id) {
            this.name = name;
            this.lore = lore;
            this.points = points;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String[] getLore() {
            return lore;
        }

        public int getPoints() {
            return points;
        }

        public int getId() {
            return id;
        }
    }
}
