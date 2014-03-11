package com.communitysurvivalgames.thesurvivalgames.ability;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class Spy extends SGAbility implements Listener {
	
	private List<Player> cooldown = new ArrayList<Player>(), nofall = new ArrayList<Player>();
	
	private Vector getVectorForPoints(Location l1, Location l2) {
        double g = -0.08;
        double d = l2.distance(l1);
        double t = d;
        double vX = (1.0+0.07*t) * (l2.getX() - l1.getX())/t;
        double vY = (1.0+0.03*t) * (l2.getY() - l1.getY())/t - 0.5*g*t;
        double vZ = (1.0+0.07*t) * (l2.getZ() - l1.getZ())/t;
        return new Vector(vX, vY, vZ);
	}
	
	public Spy() {
		super(9);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onGrappleThrow(ProjectileLaunchEvent event) {
		if(!event.getEntityType().equals(EntityType.FISHING_HOOK)) return;
		if(!(event.getEntity().getShooter() instanceof Player)) return;
		
		final Player player = (Player) event.getEntity().getShooter();
		
		if(cooldown.contains(player)) {
			event.setCancelled(true);
			return;
		}
		
		Location target = null;
		
		for(Block block : player.getLineOfSight(null, 100)) {
			if(!block.getType().equals(Material.AIR)) {
				target = block.getLocation();
				break;
			}
		}
		
		if(target == null) {
			event.setCancelled(true);
			return;
		}
		
		player.teleport(player.getLocation().add(0, 0.5, 0));
		
		final Vector v = getVectorForPoints(player.getLocation(), target);
		
		event.getEntity().setVelocity(v);
		
		if(!nofall.contains(player)) nofall.add(player);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			public void run() {
				player.setVelocity(v);
			}
		}, 5);
		
		cooldown.add(player);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			public void run() {
				cooldown.remove(player);
			}
		}, 15);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerFall(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) return;
		if(!event.getCause().equals(DamageCause.FALL)) return;
		
		Player player = (Player) event.getEntity();
		
		if(nofall.contains(player)) {
			event.setCancelled(true);
			nofall.remove(player);
		}
	}
}