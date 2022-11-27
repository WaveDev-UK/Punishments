package dev.wave.punishment.command;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.configuration.Config;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.menu.HistoryUtil;
import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.user.User;
import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.menu.Menu;
import me.dan.pluginapi.message.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.awt.*;

public class HistoryCommand extends AbstractCommand {


    public HistoryCommand() {
        super("history");
        setPermission("punishments.history");
        setUsage("/history {player}");
    }


    @Override
    public void perform(CommandContext commandContext) {

        OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        Player player = (Player) commandContext.getCommandSender();
        if(!target.hasPlayedBefore()){
            Messages.NEVER_PLAYED.send(commandContext.getCommandSender());
            return;
        }

        Menu menu = Config.HISTORY_GUI.getMenu();

        User user = Punishments.getInstance().getUserManager().get(player.getUniqueId());
        user.setHistoryPage(1);
        User targetUser = Punishments.getInstance().getUserManager().get(target.getUniqueId());
        user.setHistoryTarget(targetUser);



        PluginAPI.getInstance().getMenuManager().setMenu(PluginAPI.getInstance().getUserManager().getUser(user.getUuid()), menu);
        HistoryUtil.showPage(user, 1, player.getOpenInventory().getTopInventory());
    }

}
