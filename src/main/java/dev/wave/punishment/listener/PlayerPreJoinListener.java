package dev.wave.punishment.listener;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import dev.wave.punishment.user.User;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.util.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerPreJoinListener implements Listener {

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent e){
        User user = Punishments.getInstance().getUserManager().get(e.getUniqueId());

        List<Punishment> punishmentsToRemove = new ArrayList<>();

        for(Punishment punishment: user.getActivePunishmentList()){
            if(punishment.getType() == PunishmentType.BAN){
                if(!punishment.isActive()){
                    continue;
                }
                if(punishment.getExpiryDate() == null || punishment.getExpiryDate().getTime() > System.currentTimeMillis()){
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Text.c(Placeholder.apply(Messages.BAN_MESSAGE.getString(), new Placeholder("{reason}", punishment.getReason()),
                            new Placeholder("{author}", punishment.getAuthor() == null ? "Console" : punishment.getAuthor().getName()),
                            new Placeholder("{expiryDate}", punishment.getExpiryDate() == null ? "Never" : punishment.getExpiryDate().toString()))));
                    return;
                }
                punishmentsToRemove.add(punishment);
            }

        }

        for(Punishment punishment: punishmentsToRemove){
            user.removeActivePunishment(punishment);
        }

    }

}
