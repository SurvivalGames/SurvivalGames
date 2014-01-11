package com.communitysurvivalgames.thesurvivalgames.util.items;

import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class RailGun implements Listener {
	int timer, id = 0;
	Random gen = new Random();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getItem().getType() == Material.DIAMOND_HOE)
		try {
			for (Block loc : event.getPlayer().getLineOfSight(null, 100)) {
				playFirework(loc.getLocation());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Object[] dataStore = new Object[5];

	public void playFirework(Location loc) throws Exception {
		Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
		if (dataStore[0] == null)
			dataStore[0] = getMethod(loc.getWorld().getClass(), "getHandle");
		if (dataStore[2] == null)
			dataStore[2] = getMethod(fw.getClass(), "getHandle");
		dataStore[3] = ((Method) dataStore[0]).invoke(loc.getWorld(), (Object[]) null);
		dataStore[4] = ((Method) dataStore[2]).invoke(fw, (Object[]) null);
		if (dataStore[1] == null)
			dataStore[1] = getMethod(dataStore[3].getClass(), "addParticle");
		((Method) dataStore[1]).invoke(dataStore[3], new Object[] { "fireworksSpark", loc.getX(), loc.getY(), loc.getZ(), gen.nextGaussian() * 0.05D, -(loc.getZ() * 1.15D) * 0.5D, gen.nextGaussian() * 0.05D });
		fw.remove();
	}

	private Method getMethod(Class<?> cl, String method) {
		for (Method m : cl.getMethods())
			if (m.getName().equals(method))
				return m;
		return null;
	}
}
