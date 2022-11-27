package dev.wave.punishment.listener;

import dev.wave.punishment.configuration.Config;
import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        User user = PluginAPI.getInstance().getUserManager().getUser(e.getWhoClicked().getUniqueId());

        if(user == null){
            return;
        }

        if(PluginAPI.getInstance().getMenuManager().getMenu(user) == null || !PluginAPI.getInstance().getMenuManager().getMenu(user).equals(Config.HISTORY_GUI.getMenu())){
            return;
        }

        e.setCancelled(true);

    }

}
