package co.q64.paradisesurvivalgames.listeners;

import co.q64.paradisesurvivalgames.exception.ArenaNotFoundException;
import co.q64.paradisesurvivalgames.managers.SGApi;
import co.q64.paradisesurvivalgames.objects.SGArena;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ChestListener implements Listener {
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof DoubleChest) {
            DoubleChest c = (DoubleChest) e.getInventory().getHolder();
            SGArena a;
            try {
                a = SGApi.getArenaManager().getArena((Player) e.getPlayer());
                if (a.getSpectators().contains(e.getPlayer().getName())) {
                    e.setCancelled(true);
                    return;
                }
                SGApi.getChestManager().fillChest(a, (Chest) c.getRightSide());
                SGApi.getChestManager().fillChest(a, (Chest) c.getLeftSide());
            } catch (ArenaNotFoundException e1) {
            }
            return;
        }
        if (e.getInventory().getHolder() instanceof Chest) {
            Chest c = (Chest) e.getInventory().getHolder();
            SGArena a;
            try {
                a = SGApi.getArenaManager().getArena((Player) e.getPlayer());
                if (a.getSpectators().contains(e.getPlayer().getName())) {
                    e.setCancelled(true);
                    return;
                }
                SGApi.getChestManager().fillChest(a, c);
            } catch (ArenaNotFoundException e1) {
            }
        }

    }
}
