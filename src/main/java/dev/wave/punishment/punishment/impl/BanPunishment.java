package dev.wave.punishment.punishment.impl;

import dev.wave.punishment.Punishments;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.util.Text;
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
        Punishments.getInstance().getUserManager().get(getTarget().getUniqueId()).addPunishment(this);
        if(target.isOnline()){

            target.getPlayer().kickPlayer(Text.c(Placeholder.apply(Messages.BAN_MESSAGE.getString(), new Placeholder("{reason}", getReason()),
                    new Placeholder("{author}", getAuthor() == null ? "Console" : getAuthor().getName()),
                    new Placeholder("{expiryDate}", getExpiryDate() == null ? "Never" : getExpiryDate().toString()))));
        }

    }

    @Override
    public PunishmentType getType() {
        return PunishmentType.BAN;
    }
}
