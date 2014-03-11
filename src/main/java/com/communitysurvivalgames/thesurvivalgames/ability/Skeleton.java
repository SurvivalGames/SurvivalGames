package com.communitysurvivalgames.thesurvivalgames.ability;


public class Skeleton extends SGAbility implements Listener {

	public static List<Entity> arrows = new ArrayList<Entity>();

	public Skeleton() {
		super(8);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void entityDamagedByEntityt(EntityDamageByEntityEvent event) {
		if (arrows.contains(event.getDamager())) {
			arrows.remove(event.getEntity());
			event.setDamage(4);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void entityShootBowEvent(EntityShootBowEvent event) {

		if (event.getEntity() instanceof Player) {
			Player shooter = (Player) event.getEntity();
			if (hasAbility(shooter)) {
				if (event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)) {
					arrows.add(event.getProjectile());
				}
			}
		}
	}
}
