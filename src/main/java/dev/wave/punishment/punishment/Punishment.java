package dev.wave.punishment.punishment;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.configuration.Config;
import dev.wave.punishment.punishment.impl.BanPunishment;
import dev.wave.punishment.punishment.impl.KickPunishment;
import dev.wave.punishment.punishment.impl.MutePunishment;
import dev.wave.punishment.punishment.impl.WarnPunishment;
import me.dan.pluginapi.item.Item;
import me.dan.pluginapi.message.Placeholder;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.UUID;

public interface Punishment {

    static Punishment fromDocument(Document document) {
        if(!document.containsKey("type")){
            System.out.println("no type");
            return null;
        }

        PunishmentType type;
        try{
            type = PunishmentType.valueOf(document.getString("type"));
        }catch (IllegalArgumentException e){
            System.out.println("invalid type");
            return null;
        }

        OfflinePlayer author = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("author")));
        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("target")));
        String reason = document.getString("reason");
        Date expiryDate = document.getLong("expiryDate") == -1 ? null : new Date(document.getLong("expiryDate"));
        boolean active = document.getBoolean("active");

        switch (type){
            case BAN:
                return new BanPunishment(reason, author, target, expiryDate, active);
            case KICK:
                return new KickPunishment(reason, author, target, expiryDate, active);
            case MUTE:
                return new MutePunishment(reason, author, target, expiryDate, active);
            case WARN:
                return new WarnPunishment(reason, author, target, expiryDate, active);
            default:
                return null;
        }
    }

    default String asHistory(){
        return "§7[§c" + getType().name() + "§7] §c"
                + getTarget().getName() + " §7by §c"
                + getAuthor().getName() + " §7for §c"
                + getReason() + " §7Expires §c"
                + getExpiryDate() == null ? "Never" : getExpiryDate().toString()
                + "§7[" + (isActive() ? "§aActive" : "§cInactive") + "§7]";
    }
    String getReason();

    boolean isActive();

    void setActive(boolean active);
    OfflinePlayer getAuthor();

    OfflinePlayer getTarget();

    Date getExpiryDate();

    PunishmentType getType();

    void execute();

    default ItemStack getItem(){
        Item item = Config.HISTORY_ITEM.getItem();

       return item.toItemStack(new Placeholder("{reason}", getReason()),
                new Placeholder("{author}", getAuthor() == null ? "Console": getAuthor().getName()),
                new Placeholder("{target}", getTarget().getName()),
                new Placeholder("{expires}", getExpiryDate() == null ? "Never" : getExpiryDate().toString()),
                new Placeholder("{active}", isActive() ? "Active" : "Inactive"),
                new Placeholder("{punishment}", getType().name()));
    }
}
