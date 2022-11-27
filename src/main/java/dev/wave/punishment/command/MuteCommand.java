package dev.wave.punishment.command;

import dev.wave.punishment.Util;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.punishment.impl.MutePunishment;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;

public class MuteCommand extends AbstractCommand {

    public MuteCommand() {
        super("mute");
        setPermission("punishments.mute");
        setUsage("/mute {player} {duration} {reason}");
    }

    @Override
    public void perform(CommandContext commandContext) {

        OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        if(!target.hasPlayedBefore()){
            Messages.NEVER_PLAYED.send(commandContext.getCommandSender());
            return;
        }

        long time = Util.getTime(commandContext.getArgs());



        boolean silent = commandContext.getArgs()[commandContext.getArgs().length - 1].equalsIgnoreCase("-s");

        String reason = "";

        int start = time == -1 ? 1 : 2;
        int end = silent ? commandContext.getArgs().length - 1 : commandContext.getArgs().length;

        for(int i = start; i < end; i++){
            reason += commandContext.getArgs()[i] + " ";
        }

        if(silent) {

            Messages.MUTE.send(commandContext.getCommandSender() ,new Placeholder("{target}", target.getName()), new Placeholder("{reason}", reason), new Placeholder("{author}", commandContext.getCommandSender().getName()));
        } else {

            Messages.MUTE.broadcast(new Placeholder("{target}", target.getName()), new Placeholder("{reason}", reason), new Placeholder("{author}", commandContext.getCommandSender().getName()));
        }

        Date expiryDate = time > -1 ? new Date(System.currentTimeMillis() + time) : null;

        new MutePunishment(reason, commandContext.getCommandSender() instanceof OfflinePlayer ? (OfflinePlayer) commandContext.getCommandSender() : null, target, expiryDate, true).execute();
    }


}
