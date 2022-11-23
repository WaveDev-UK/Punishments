package dev.wave.punishment;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import dev.wave.punishment.configuration.Config;
import lombok.Getter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.file.YamlFile;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Punishments extends JavaPlugin {


    @Getter
    private static Punishments instance;

    private MongoClient mongoClient;
    private MongoDatabase database;
    @Override
    public void onEnable() {
        instance = this;
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        String mongoClientURIString = "mongodb://" + Config.MONGO_USER.getString() + ":" + Config.MONGO_PASSWORD.getString() + "@" + Config.MONGO_HOST.getString() + ":" + Config.MONGO_PORT.getInt();

        MongoClientURI mongoClientURI = new MongoClientURI(mongoClientURIString);

        this.mongoClient = new MongoClient(mongoClientURI);

        this.mongoClient.startSession();

        this.database = mongoClient.getDatabase(Config.MONGO_DATABASE.getString());




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
