package dev.wave.punishment.user;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.packet.SavePacket;
import me.dan.pluginapi.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager extends Manager<UUID, User> {


    private final List<User> pendingSave;

    public UserManager() {
        loadUsers();
        pendingSave = new ArrayList<>();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Punishments.getInstance(), () -> {
            if (pendingSave.size() > 0) {
                Bukkit.getScheduler().runTaskAsynchronously(Punishments.getInstance(), this::saveAll);
            }
        }, 20 * 60 * 5, 20 * 60 * 5);
    }


    private void loadUsers() {
        for (OfflinePlayer offlinePlayer : Bukkit.getServer().getOfflinePlayers()) {
            User user = new User(offlinePlayer.getUniqueId());
            insert(user.getUuid(), user);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = new User(player.getUniqueId());
            insert(user.getUuid(), user);
        }
    }

    @Override
    public User get(UUID uuid) {
        User user = super.get(uuid);
        if (user == null) {
            return null;
        }
        pendingSave.add(user);
        return user;
    }


    public void saveAll() {

        for (User user : pendingSave) {
            new SavePacket(user).save();
        }
        pendingSave.clear();


    }


}
