package dev.wave.punishment.punishment;

import dev.wave.punishment.punishment.impl.BanPunishment;
import dev.wave.punishment.punishment.impl.KickPunishment;
import dev.wave.punishment.punishment.impl.MutePunishment;
import dev.wave.punishment.punishment.impl.WarnPunishment;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public interface Punishment {

    static Punishment fromDocument(Document document) {
        if(!document.containsKey("type")){
            return null;
        }

        PunishmentType type;
        try{
            type = PunishmentType.valueOf(document.getString("type"));
        }catch (IllegalArgumentException e){
            return null;
        }

        OfflinePlayer author = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("author")));
        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(document.getString("target")));
        String reason = document.getString("reason");
        Date expiryDate = new Date(document.getLong("expiryDate"));
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

    String getReason();

    boolean isActive();

    void setActive(boolean active);
    OfflinePlayer getAuthor();

    OfflinePlayer getTarget();

    Date getExpiryDate();

    PunishmentType getType();
    void execute();

}
