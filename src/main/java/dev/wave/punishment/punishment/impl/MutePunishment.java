package dev.wave.punishment.punishment.impl;

import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.punishment.PunishmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MutePunishment implements Punishment {

    private final String reason;
    private final OfflinePlayer author;
    private final OfflinePlayer target;
    private final Date expiryDate;

    @Setter
    private boolean active;
    @Override
    public PunishmentType getType() {
        return PunishmentType.MUTE;
    }

    @Override
    public void execute() {

    }


}
