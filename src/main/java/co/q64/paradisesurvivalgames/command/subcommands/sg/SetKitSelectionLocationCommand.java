package co.q64.paradisesurvivalgames.command.subcommands.sg;

import co.q64.paradisesurvivalgames.command.subcommands.SubCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SetKitSelectionLocationCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_CRYSTAL);
    }

}
