package dev.wave.punishment.listener;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import dev.wave.punishment.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        User user = Punishments.getInstance().getUserManager().get(e.getPlayer().getUniqueId());
        for (Punishment activePunishment : user.getActivePunishmentList()) {
            if (!activePunishment.getType().equals(PunishmentType.MUTE)) {
                continue;
            }
            if (activePunishment.getExpiryDate().getTime() > System.currentTimeMillis()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage("You are muted!");
                return;
            }
            user.removeActivePunishment(activePunishment);

        }
    }

}
