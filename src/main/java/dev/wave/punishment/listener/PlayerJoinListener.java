package dev.wave.punishment.listener;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        User user = Punishments.getInstance().getUserManager().get(e.getPlayer().getUniqueId());

        if(user == null){
            Punishments.getInstance().getUserManager().insert(e.getPlayer().getUniqueId(), new User(e.getPlayer().getUniqueId()));
        }
    }

}
