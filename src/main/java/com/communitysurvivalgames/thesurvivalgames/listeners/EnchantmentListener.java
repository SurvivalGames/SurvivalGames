package com.communitysurvivalgames.thesurvivalgames.listeners;


public class EnchantmentListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void EnchantItem(EnchantItemEvent e) {
        if (e.getItem().getType() == Material.GOLD_SWORD || e.getItem().getType() == Material.WOOD_SWORD || e.getItem().getType() == Material.STONE_SWORD
                || e.getItem().getType() == Material.DIAMOND_SWORD) {
            e.getEnchantsToAdd().clear();
            Random r = new Random();
            if (r.nextInt(1) == 0) {
                e.getEnchantsToAdd().put(Enchantment.DAMAGE_ALL, 1);
            } else {
                int rnd = r.nextInt(3);
                if (rnd == 0 || rnd == 3) {
                    e.getEnchantsToAdd().put(Enchantment.DAMAGE_ALL, 2);
                }

                if (rnd == 1) {
                    e.getEnchantsToAdd().put(Enchantment.KNOCKBACK, 1);
                }

                if (rnd == 2) {
                    e.getEnchantsToAdd().put(Enchantment.getByName("Shocking"), 1);
                }
            }
        }

    }
}
