package co.q64.paradisesurvivalgames.command.subcommands.sg;

import co.q64.paradisesurvivalgames.command.subcommands.SubCommand;
import co.q64.paradisesurvivalgames.locale.I18N;
import co.q64.paradisesurvivalgames.managers.SGApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommand {

    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (!p.hasPermission("sg.remove") && !p.isOp()) {
            return;
        }
        if (SGApi.getPlugin().getPluginConfig().isBungeecordMode()) {
            Bukkit.getLogger().severe("You're running the server in Bungeecord mode, " +
                    "yet you are not running Bungeecord at all... people these days");
        }
        if (cmd.equals("remove") && args.length == 1) {
            int num;
            try {
                num = Integer.parseInt(args[0]);
            } catch (NumberFormatException x) {
                p.sendMessage(SGApi.getArenaManager().getError() + I18N.getLocaleString("NOT_NUMBER"));
                return;
            }
            SGApi.getArenaManager().removeArena(num);
            p.sendMessage(SGApi.getArenaManager().getPrefix() + I18N.getLocaleString("REMOVED_ARENA"));
        }
    }
}
