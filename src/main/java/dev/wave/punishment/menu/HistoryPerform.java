package dev.wave.punishment.menu;

import dev.wave.punishment.Punishments;
import me.dan.pluginapi.menu.MenuItem;
import me.dan.pluginapi.menu.MenuPerform;
import me.dan.pluginapi.user.User;
import org.bukkit.inventory.Inventory;

public class HistoryPerform extends MenuPerform {

    @Override
    public boolean perform(MenuItem menuItem, User user) {

        if(menuItem == null){
            return true;
        }

        Inventory inventory = user.getPlayer().getOpenInventory().getTopInventory();

        dev.wave.punishment.user.User punishUser = Punishments.getInstance().getUserManager().get(user.getOfflinePlayer().getUniqueId());


        if (menuItem.getKey().equalsIgnoreCase("next-page")) {
            HistoryUtil.showPage(punishUser, punishUser.getHistoryPage() + 1, inventory);
            return true;
        }

        if(menuItem.getKey().equalsIgnoreCase("previous-page")){
            HistoryUtil.showPage(punishUser, punishUser.getHistoryPage() - 1, inventory);
            return true;
        }


        return true;
    }

    @Override
    public void onClose(User user, Inventory inventory) {

    }
}
