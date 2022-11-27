package dev.wave.punishment;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;
import dev.wave.punishment.command.BanCommand;
import dev.wave.punishment.command.HistoryCommand;
import dev.wave.punishment.command.MuteCommand;
import dev.wave.punishment.command.WarnCommand;
import dev.wave.punishment.configuration.Config;
import dev.wave.punishment.configuration.Messages;
import dev.wave.punishment.listener.AsyncPlayerChatListener;
import dev.wave.punishment.listener.PlayerJoinListener;
import dev.wave.punishment.menu.HistoryPerform;
import dev.wave.punishment.user.UserManager;
import lombok.Getter;
import me.dan.pluginapi.PluginAPI;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.plugin.CustomPlugin;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Punishments extends CustomPlugin {
    @Getter
    private static Punishments instance;

    private UserManager userManager;
    private MongoClient mongoClient;
    private MongoDatabase database;
    @Override
    public void enable() {
        instance = this;
        Configuration.loadConfig(new YamlFile("config.yml", this.getDataFolder().getAbsolutePath(), null, this), Config.values());
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), Messages.values());
        String mongoClientURIString = "mongodb://" + Config.MONGO_USER.getString() + ":" + Config.MONGO_PASSWORD.getString() + "@" + Config.MONGO_HOST.getString() + ":" + Config.MONGO_PORT.getInt();

        MongoClientURI mongoClientURI = new MongoClientURI(mongoClientURIString);

        this.mongoClient = new MongoClient(mongoClientURI);

        this.mongoClient.startSession();

        this.database = mongoClient.getDatabase(Config.MONGO_DATABASE.getString());

        this.userManager = new UserManager();

        registerEvents(new PlayerJoinListener(), new AsyncPlayerChatListener());

        registerCommands(new HistoryCommand(), new BanCommand(), new MuteCommand(), new WarnCommand());

        PluginAPI.getInstance().getMenuManager().registerPerformMethod(Config.HISTORY_GUI.getMenu(), new HistoryPerform());

    }


    @Override
    public void disable() {
       userManager.saveAll();
    }
}
