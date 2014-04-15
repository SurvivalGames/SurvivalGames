package co.q64.paradisesurvivalgames.command.subcommands.sg;

import co.q64.paradisesurvivalgames.command.subcommands.SubCommand;
import org.bukkit.entity.Player;

public class BountyCommand implements SubCommand {
    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("bounty")) {
            {
                //TODO: I18N
                p.sendMessage("This command has not been fully implemented yet!");
            }
        }
    }
}
