package dev.wave.punishment.command;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.punishment.Punishment;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class HistoryCommand extends AbstractCommand {


    public HistoryCommand() {
        super("history");
        setPermission("punishments.history");
        setUsage("/history {player}");
    }


    @Override
    public void perform(CommandContext commandContext) {
        if(commandContext.getArgs().length == 0){
            commandContext.getCommandSender().sendMessage("§cUsage: /history {player}");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        if(!target.hasPlayedBefore()){
            commandContext.getCommandSender().sendMessage("§cThis player has never played before.");
            return;
        }

        commandContext.getCommandSender().sendMessage("§7History for §e" + target.getName());
        for(Punishment punishment: Punishments.getInstance().getUserManager().get(target.getUniqueId()).getPunishmentList()){
            commandContext.getCommandSender().sendMessage(punishment.asHistory());
        }

    }

}
