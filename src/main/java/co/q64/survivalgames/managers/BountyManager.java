package co.q64.survivalgames.managers;

import co.q64.survivalgames.event.GameEndEvent;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.objects.SGArena;
import co.q64.survivalgames.util.EconUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class BountyManager implements Listener {

	HashMap<String, Integer> amount = new HashMap<>();
	private SGArena arena;
	private Map<String, HashMap<String, Integer>> bounties = new HashMap<>();

	public BountyManager(SGArena a) {
		SGApi.getPlugin().getServer().getPluginManager().registerEvents(this, SGApi.getPlugin());
		this.arena = a;
		bounties = new HashMap<String, HashMap<String, Integer>>();
		amount = new HashMap<String, Integer>();
	}

	public void addBounty(int bounty, Player reciever, Player sender) {
		if (arena.getPlayers().contains(reciever.getUniqueId())) {
			try {
				EconUtil.removePoints(sender, bounty);
				amount.put(sender.getName(), bounty);
				bounties.put(reciever.getName(), amount);
				reciever.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("BOUNTY_SUCCESS_RECEIVED").replace("%amount", amount.toString()).replace("%sender", sender.getName()));
				sender.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("BOUNTY_SUCCESS_SENT").replace("%amount", amount.toString()).replace("%receiver", reciever.getName()));
			} catch (Exception e) {
				sender.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("BOUNTY_FAIL"));
			}
		} else {
			sender.sendMessage(SGApi.getArenaManager().getError() + "Player is not in game.");
		}
	}

	public int getBountied(Player reciever, Player sender) {
		HashMap<String, Integer> name = bounties.get(reciever.getName());
		int amount = name.get(sender.getName());
		try {
			return amount;
		} catch (Exception e) {
			return 0;
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameEnd(GameEndEvent event) {
		for (String s : bounties.keySet()) {
			//TODO Add points through use of EconUtil - I don't really understand how we use getBounty() explain?
		}
		bounties.clear();
	}

	public void removceBounty(Player reciever, Player sender) {
		if (arena.getPlayers().contains(reciever.getUniqueId())) {
			try {
				bounties.remove(reciever.getName());
				sender.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("BOUNTY_SUCCESS"));
			} catch (Exception e) {
				sender.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("BOUNTY_FAIL"));
			}
		}
	}
}