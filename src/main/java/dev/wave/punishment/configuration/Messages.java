package dev.wave.punishment.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.message.Message;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Messages implements Configuration, Message {

    PREFIX("prefix", "&8[&cPunishments&8] &e"),
    NEVER_PLAYED("never-played", "&cThis player has never played before."),
    BAN("ban", "{prefix}{target} &7has been banned for &e{reason} &7by &e{author}"),
    BAN_MESSAGE("ban-message", "&cYou have been banned for &e{reason} &cby &e{author}. &cExpires: &e{expiryDate}"),
    MUTE_MESSAGE("mute-message", "&cYou are muted for &e{reason} &cby &e{author}. &cExpires: &e{expiryDate}"),
    UNBAN_SUCCESS("unban-success", "{prefix}{target} &7has been unbanned."),
    UNBAN_FAILURE("unban-failure", "{prefix}{target} &7is not banned."),

    UNMUTE_SUCCESS("unmute-success", "{prefix}{target} &7has been unmuted."),
    UNMUTE_FAILURE("unmute-failure", "{prefix}{target} &7is not muted."),
    MUTE("mute", "{prefix}{target} &7has been muted for &e{reason} &7by &e{author}"),
    WARN("warn", "{prefix}{target} &7has been warned for &e{reason} &7by &e{author}"),

    MUTED("muted", "{prefix}You are currently muted for &e{reason} &7by &e{author}, &7expires: &e{expiry}"),;


    private final String path;

    @Setter
    private Object value;

    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }

    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }

}
