package com.communitysurvivalgames.thesurvivalgames.command.subcommands;

import com.communitysurvivalgames.thesurvivalgames.command.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equals("remove") && args.length == 1) {
            int num;
            try {
                num = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(ArenaManager.getManager().error + I18N.getLocaleString("NOT_NUMBER"));
                return;
            }
            ArenaManager.getManager().removeArena(num);
            p.sendMessage(ArenaManager.getManager().prefix + I18N.getLocaleString("REMOVED_ARENA"));
        }
    }
}
