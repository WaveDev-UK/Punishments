package dev.wave.punishment.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;

@Getter
@AllArgsConstructor
public enum Config implements Configuration {

    MONGO_HOST("mongo.host", "localhost"),
    MONGO_PORT("mongo.port", 27017),
    MONGO_DATABASE("mongo.database", "punishments"),
    MONGO_USER("mongo.user", "root"),
    MONGO_PASSWORD("mongo.password", "password");

    private final String path;
    @Setter
    private Object value;

}
