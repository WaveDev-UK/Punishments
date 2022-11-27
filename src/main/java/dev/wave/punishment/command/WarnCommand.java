package dev.wave.punishment.command;

import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.punishment.impl.WarnPunishment;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class WarnCommand extends AbstractCommand {


    public WarnCommand() {
        super("warn");
        setDescription("Warn a player");
        setPermission("punishments.warn");
        setUsage("/warn {player} {reason}");
    }

    @Override
    public void perform(CommandContext commandContext) {

        OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        if(!target.hasPlayedBefore()){
            Messages.NEVER_PLAYED.send(commandContext.getCommandSender());
            return;
        }

        String reason = "";

        boolean silent = false;

        for (int i = 1; i < commandContext.getArgs().length; i++) {

            if(commandContext.getArgs()[i] == "-s"){
                silent = true;
                continue;
            }
            reason += commandContext.getArgs()[i] + " ";
        }

        if(!silent) {
            Messages.WARN.broadcast(new Placeholder("{target}", target.getName()), new Placeholder("{reason}", reason), new Placeholder("{author}", commandContext.getCommandSender().getName()));
        }else{
            Messages.WARN.send(commandContext.getCommandSender(), new Placeholder("{target}", target.getName()), new Placeholder("{reason}", reason), new Placeholder("{author}", commandContext.getCommandSender().getName()));

        }
        new WarnPunishment(reason, commandContext.getCommandSender() instanceof OfflinePlayer ? (OfflinePlayer) commandContext.getCommandSender() : null, target, null, true).execute();
    }
}
