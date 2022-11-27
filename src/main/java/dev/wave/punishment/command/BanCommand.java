package dev.wave.punishment.command;

import dev.wave.punishment.Util;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.punishment.impl.BanPunishment;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public class BanCommand extends AbstractCommand {

    public BanCommand() {
        super("ban");
        setPermission("punishments.ban");
        setUsage("/ban {player} {duration} {reason}");
    }

    @Override
    public void perform(CommandContext commandContext) {

        OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        if(!target.hasPlayedBefore()){
            Messages.NEVER_PLAYED.send(commandContext.getCommandSender());
            return;
        }

        long time = Util.getTime(commandContext.getArgs());

        String reason = "";

        boolean silent = commandContext.getArgs()[commandContext.getArgs().length - 1].equalsIgnoreCase("-s");

        if(silent) {
            for (int i = 1; i < commandContext.getArgs().length - 1; i++) {
                reason += commandContext.getArgs()[i] + " ";
                Messages.BAN.send(commandContext.getCommandSender(),new Placeholder("{target}", target.getName()), new Placeholder("{reason}", reason), new Placeholder("{author}", commandContext.getCommandSender().getName()));
            }
        } else {
            for (int i = 1; i < commandContext.getArgs().length; i++) {
                reason += commandContext.getArgs()[i] + " ";
            }
            Messages.BAN.broadcast(new Placeholder("{target}", target.getName()), new Placeholder("{reason}", reason), new Placeholder("{author}", commandContext.getCommandSender().getName()));
        }

        Date expiryDate = time > -1 ? new Date(System.currentTimeMillis() + time) : null;

        new BanPunishment(reason, commandContext.getCommandSender() instanceof OfflinePlayer ? (OfflinePlayer) commandContext.getCommandSender() : null, target, expiryDate, true).execute();
    }


}
