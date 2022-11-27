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
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        User user = Punishments.getInstance().getUserManager().get(e.getPlayer().getUniqueId());
        List<Punishment> punishmentsToRemove = new ArrayList<>();
        for (Punishment activePunishment : user.getActivePunishmentList()) {
            if (!activePunishment.getType().equals(PunishmentType.MUTE)) {
                continue;
            }

            if(!activePunishment.isActive()){
                continue;
            }

            if (activePunishment.getExpiryDate() == null || activePunishment.getExpiryDate().getTime() > System.currentTimeMillis()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Text.c(Placeholder.apply(Messages.MUTE_MESSAGE.getString(), new Placeholder("{reason}", activePunishment.getReason()),
                        new Placeholder("{author}", activePunishment.getAuthor() == null ? "Console" : activePunishment.getAuthor().getName()),
                        new Placeholder("{expiryDate}", activePunishment.getExpiryDate() == null ? "Never" : activePunishment.getExpiryDate().toString()))));
                return;
            }
            punishmentsToRemove.add(activePunishment);

        }

        for(Punishment punishment: punishmentsToRemove){
            user.removeActivePunishment(punishment);
        }

    }

}
