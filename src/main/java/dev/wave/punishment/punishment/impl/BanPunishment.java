package dev.wave.punishment.punishment.impl;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;


@Getter
@AllArgsConstructor
public class BanPunishment implements Punishment {

    private final String reason;
    private final OfflinePlayer author;
    private final OfflinePlayer target;
    private final Date expiryDate;

    @Setter
    private boolean active;

    @Override
    public void execute() {
        Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, expiryDate, author == null ? "Console" : author.getName());
        Punishments.getInstance().getUserManager().get(getTarget().getUniqueId()).addPunishment(this);
        if(author == null || !author.isOnline()){
            return;
        }

    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.BAN;
    }
}
