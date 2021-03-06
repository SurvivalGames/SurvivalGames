/**
 * Name: TimeManager.java
 * Created: 28 December 2013
 *
 * @version 1.0.0
 */

package co.q64.survivalgames.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import co.q64.survivalgames.event.GameStartEvent;
import co.q64.survivalgames.listeners.MoveListener;
import co.q64.survivalgames.locale.I18N;
import co.q64.survivalgames.multiworld.SGWorld;
import co.q64.survivalgames.net.SendWebsocketData;
import co.q64.survivalgames.objects.MapHash;
import co.q64.survivalgames.objects.SGArena;
import co.q64.survivalgames.runnables.CodeExecutor;
import co.q64.survivalgames.runnables.Countdown;

public class TimeManager {

	private SGArena a;

	private Countdown cdm;

	private Countdown cg;
	private Countdown dm;
	private Countdown end;
	private Countdown g;
	private Countdown gameTime;

	public TimeManager(SGArena a) {
		this.setA(a);
	}

	void commenceDm() {
		setCdm(new Countdown(getA(), 1, 10, "DeathMatch", "seconds", new CodeExecutor() {
			@Override
			public void runCode() {
				getA().broadcast(I18N.getLocaleString("START"));
				MoveListener.getPlayers().removeAll(getA().getPlayers());

				countdownEnd();
			}
		}));
		getCdm().setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), getCdm(), 0L, 20L));
	}

	public void countdown() {
		setCg(new Countdown(getA(), 1, 10, "Game", "seconds", new CodeExecutor() {
			@Override
			public void runCode() {

				SendWebsocketData.playToArena(getA(), "play");

				getA().broadcast(I18N.getLocaleString("ODDS"));
				getA().setState(SGArena.ArenaState.IN_GAME);
				for (UUID s : getA().getPlayers()) {
					if (MoveListener.getPlayers().contains(s)) {
						MoveListener.getPlayers().remove(s);
						Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 500, 9));
						Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 250, 2));
						Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.HEAL, getA().getCurrentMap().getGracePeriod(), 50));
					}
				}

				getA().broadcast(ChatColor.GOLD + "You will get your kit in 10 seconds!");

				Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

					@Override
					public void run() {
						for (UUID s : getA().getPlayers()) {
							SGApi.getKitManager().giveKit(Bukkit.getPlayer(s));
						}
					}
				}, 200L);
				countupGame();
				countdownDm();
			}
		}, "sounds"));
		getCg().setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), getCg(), 0L, 20L));
	}

	public void countdownDm() {
		setDm(new Countdown(getA(), 1, SGApi.getPlugin().getPluginConfig().getDmTime(), "DeathMatch", "minutes", new CodeExecutor() {
			@Override
			public void runCode() {
				getA().broadcast(I18N.getLocaleString("DM_STARTING"));
				getA().setState(SGArena.ArenaState.DEATHMATCH);
				for (int i = 0; i < getA().getPlayers().size(); i++) {
					UUID s = getA().getPlayers().get(i);
					Player p = Bukkit.getPlayer(s);
					p.teleport(getA().getCurrentMap().locs.get(i));
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable(){

					@Override
					public void run() {
						MoveListener.getPlayers().addAll(getA().getPlayers());
						commenceDm();
					}}, 50L);
			}
		}));
		getDm().setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), getDm(), 0L, 60 * 20L));
	}

	void countdownEnd() {
		setEnd(new Countdown(getA(), 1, 5, "EndGame", "minutes", new CodeExecutor() {
			@Override
			public void runCode() {
				getA().broadcast(I18N.getLocaleString("END") + " TIED_GAME");
				// tp out of arena, rollback, pick up all items and arrows
			}
		}));
		getEnd().setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), getEnd(), 0L, 60 * 20L));
	}

	public void countdownLobby(int n) {
		// setup the voting
		int i = 0;
		List<MapHash> hashes = new ArrayList<>();
		Collections.shuffle(SGApi.getMultiWorldManager().getWorlds());
		for (SGWorld world : SGApi.getMultiWorldManager().getWorlds()) {
			if (world.isInLobby()) {
				continue;
			}

			if (world.getWorld().getPlayers().isEmpty() && i <= 5) {
				world.setInLobby(true);
				MapHash hash = new MapHash(world, i);
				hashes.add(hash);
				i++;
			}
		}
		for (MapHash hash : hashes) {
			getA().getVotes().put(hash, 0);
		}

		for (UUID s : getA().getPlayers()) {
			ItemManager.getInstance().getItem("vote-item").givePlayerItem(Bukkit.getPlayer(s));
		}

		getA().broadcast("Use the emerald in your inventory to vote!");
		getA().broadcastVotes();

		getA().setState(SGArena.ArenaState.PRE_COUNTDOWN);

		setG(new Countdown(getA(), 1, n, "Game", "minutes", new CodeExecutor() {
			@Override
			public void runCode() {
				//handle votes
				Map.Entry<MapHash, Integer> maxEntry = null;
				for (Map.Entry<MapHash, Integer> entry : getA().getVotes().entrySet()) {
					if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
						maxEntry = entry;
					}
				}
				getA().setCurrentMap(maxEntry.getKey().getWorld());
				getA().getVotes().remove(maxEntry);
				for (MapHash m : getA().getVotes().keySet()) {
					m.getWorld().setInLobby(false);
				}
				getA().getVotes().clear();
				getA().broadcast(SGApi.getArenaManager().getPrefix() + I18N.getLocaleString("MAP_WINNER") + " " + getA().getCurrentMap().getWorld().getName());

				Bukkit.getPluginManager().callEvent(new GameStartEvent(getA()));
				getA().broadcast(I18N.getLocaleString("GAME_STARTING"));
				getA().setState(SGArena.ArenaState.STARTING_COUNTDOWN);

				int index = 0;
				for (UUID s : getA().getPlayers()) {
					Player p = Bukkit.getPlayer(s);
					Bukkit.getLogger().info("List: " + getA().getCurrentMap().locs.toString());
					Location loc = getA().getCurrentMap().locs.get(index);
					p.getInventory().clear();
					p.teleport(loc);

					index++;
				}
				for (UUID s : getA().getPlayers()) {
					SendWebsocketData.stopMusic(Bukkit.getPlayer(s));
				}
				countdown();
				MoveListener.getPlayers().addAll(getA().getPlayers());
			}
		}));
		getG().setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), getG(), 0L, 60 * 20L));
	}

	public void countupGame() {
		setGameTime(new Countdown(getA(), -1, 1, "GameTime", "seconds", new CodeExecutor() {
			@Override
			public void runCode() {
				//Ignored
			}
		}, "check"));
		getGameTime().setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), getGameTime(), 0L, 20L));
	}

	public void forceReset() {
		if (getG() != null)
			Bukkit.getScheduler().cancelTask(getG().getId());
		if (getCg() != null)
			Bukkit.getScheduler().cancelTask(getCg().getId());
		if (getDm() != null)
			Bukkit.getScheduler().cancelTask(getDm().getId());
		if (getCdm() != null)
			Bukkit.getScheduler().cancelTask(getCdm().getId());
		if (getEnd() != null)
			Bukkit.getScheduler().cancelTask(getEnd().getId());
		if (getGameTime() != null)
			Bukkit.getScheduler().cancelTask(getGameTime().getId());
	}

	public SGArena getA() {
		return a;
	}

	public Countdown getCdm() {
		return cdm;
	}

	public Countdown getCg() {
		return cg;
	}

	public Countdown getDm() {
		return dm;
	}

	public Countdown getEnd() {
		return end;
	}

	public Countdown getG() {
		return g;
	}

	public Countdown getGameTime() {
		return gameTime;
	}

	public void setA(final SGArena a) {
		this.a = a;
	}

	public void setCdm(final Countdown cdm) {
		this.cdm = cdm;
	}

	public void setCg(final Countdown cg) {
		this.cg = cg;
	}

	public void setDm(final Countdown dm) {
		this.dm = dm;
	}

	public void setEnd(final Countdown end) {
		this.end = end;
	}

	public void setG(final Countdown g) {
		this.g = g;
	}

	public void setGameTime(final Countdown gameTime) {
		this.gameTime = gameTime;
	}

	public void forceStart() {
		if (getG() != null) {
			Bukkit.getScheduler().cancelTask(getG().getId());
			getG().ce.runCode();
		} else {
			
		}
	}
	
	public void forceStartDM(){
		if (getDm() != null){
			Bukkit.getScheduler().cancelTask(getDm().getId());
			getDm().ce.runCode();
		}
	}
}
