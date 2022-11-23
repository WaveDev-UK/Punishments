package dev.wave.punishment.packet;

import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import dev.wave.punishment.punishment.impl.BanPunishment;
import dev.wave.punishment.punishment.impl.KickPunishment;
import dev.wave.punishment.punishment.impl.MutePunishment;
import dev.wave.punishment.punishment.impl.WarnPunishment;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public class PunishmentPacket {

    private final String author;
    private final String reason;
    private final String target;
    private final String type;

    private final boolean active;
    private final long expiryDate;

    public PunishmentPacket(Punishment punishment){
        this.author = punishment.getAuthor().getUniqueId().toString();
        this.reason = punishment.getReason();
        this.target = punishment.getTarget().getUniqueId().toString();
        this.type = punishment.getType().name();
        this.expiryDate = punishment.getExpiryDate().getTime();
        this.active = punishment.isActive();
    }

    public Punishment toPunishment(){
        switch(PunishmentType.valueOf(type)){
            case BAN:
                return new BanPunishment(reason, Bukkit.getOfflinePlayer(UUID.fromString(author)), Bukkit.getOfflinePlayer(UUID.fromString(target)), new Date(expiryDate), active);
            case KICK:
                return new KickPunishment(reason, Bukkit.getOfflinePlayer(UUID.fromString(author)), Bukkit.getOfflinePlayer(UUID.fromString(target)), new Date(expiryDate), active);
            case MUTE:
                return new MutePunishment(reason, Bukkit.getOfflinePlayer(UUID.fromString(author)), Bukkit.getOfflinePlayer(UUID.fromString(target)), new Date(expiryDate), active);
            case WARN:
                return new WarnPunishment(reason, Bukkit.getOfflinePlayer(UUID.fromString(author)), Bukkit.getOfflinePlayer(UUID.fromString(target)), new Date(expiryDate), active);
            default:
                return null;
        }
    }

    public Document asDocument(){
        return new Document("author", author)
                .append("reason", reason)
                .append("target", target)
                .append("type", type)
                .append("active", active)
                .append("expiryDate", expiryDate);
    }

}
