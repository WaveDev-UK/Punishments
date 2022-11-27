package dev.wave.punishment.command;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import dev.wave.punishment.user.User;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class UnbanCommand extends AbstractCommand {
    public UnbanCommand() {
        super("unban");
        setPermission("punishments.unban");
        setUsage("/unban {player}");
    }

    @Override
    public void perform(CommandContext commandContext) {

        OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        if(!target.hasPlayedBefore()){
            Messages.NEVER_PLAYED.send(commandContext.getCommandSender());
            return;
        }

        User targetUser = Punishments.getInstance().getUserManager().get(target.getUniqueId());

        if(targetUser == null){
            Messages.NEVER_PLAYED.send(commandContext.getCommandSender());
            return;
        }

        boolean unbanned = false;

        List<Punishment> punishmentsToRemove = new ArrayList<>();

        for(Punishment activePunishment: targetUser.getActivePunishmentList()){
            if(activePunishment.getType().equals(PunishmentType.BAN)){
                punishmentsToRemove.add(activePunishment);
                unbanned = true;
            }
        }

        punishmentsToRemove.forEach(targetUser::removeActivePunishment);

        if(unbanned){
            Messages.UNBAN_SUCCESS.send(commandContext.getCommandSender(), new Placeholder("{target}", target.getName()));
        }else{
            Messages.UNBAN_FAILURE.send(commandContext.getCommandSender(), new Placeholder("{target}", target.getName()));
        }

    }
}
