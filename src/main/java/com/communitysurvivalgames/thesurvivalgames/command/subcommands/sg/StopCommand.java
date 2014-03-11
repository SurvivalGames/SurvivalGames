/**
 * Name: StopCommand.java
 * Created: 2 January 2014
 *
 * @verson 1.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import src.main.java.com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import src.main.java.com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class StopCommand implements SubCommand {
    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("stop") && args.length == 1) {
            int i;
            try {
                i = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
               return;
            }
            SGArena a;
            try {
                a = SGApi.getArenaManager().getArena(i);
          } catch (ArenaNotFoundException e) {
                Bukkit.getLogger().severe(e.getMessage());
                return;
            }
            if (a == null) {
                p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARENA") + a);
            return;
            }
            a.end();
            p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("ARENA_END"));
        } else {
            p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
        }
    }
}
