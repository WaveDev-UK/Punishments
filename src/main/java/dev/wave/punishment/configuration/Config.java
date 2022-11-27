package dev.wave.punishment.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.item.Item;
import me.dan.pluginapi.menu.Menu;
import me.dan.pluginapi.menu.MenuItem;
import me.dan.pluginapi.util.Pair;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Config implements Configuration {

    MONGO_HOST("mongo.host", "localhost"),
    MONGO_PORT("mongo.port", 27017),
    MONGO_DATABASE("mongo.database", "punishments"),
    MONGO_USER("mongo.user", "root"),
    MONGO_PASSWORD("mongo.password", "password"),
    HISTORY_ITEM("history-item", Item.builder()
            .name(" ")
            .lore(
                    Arrays.asList(
                            "&8&l&m-------------------",
                            " ",
                            "&7Punishment: &c{punishment}",
                            "&7Reason: &c{reason}",
                            "&7Expires: &c{expires}",
                            "&7Author: &c{author}",
                            "&7Active: &c{active}",
                            " ",
                            "&8&l&m-------------------"
                    )

            ).itemFlags(Arrays.asList(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES))
            .material("paper")
            .enchantments(
                    Arrays.asList(
                            new Pair<>(Enchantment.ARROW_INFINITE, 1)
                    )
            ).build()),
    HISTORY_GUI("history-gui", new Menu()
            .setName("&cHistory")
            .setSize(54)
            .addItems(new MenuItem()
                    .setSlots(
                            1,
                            2,
                            3,
                            4,
                            6,
                            7,
                            8,
                            9,
                            46,
                            48,
                            50,
                            51,
                            52,
                            54)
                            .setKey("spacer-1")
                            .setItem(Item.builder()
                                    .amount(1)
                                    .material("black_stained_glass_pane")
                                    .build()),
                    new MenuItem()
                            .setSlots(5, 49)
                            .setKey("spacer-2")
                            .setItem(Item.builder()
                                    .amount(1)
                                    .material("white_stained_glass_pane")
                                    .build()),
                    new MenuItem()
                            .setSlots(
                                    47
                            )
                            .setItem(Item.builder()
                                    .material("arrow")
                                    .name("&cPrevious Page").build())
                            .setKey("previous-page"),
                    new MenuItem()
                            .setItem(Item.builder()
                                    .material("arrow")
                                    .name("&cNext Page").build())
                            .setKey("next-page")
                            .setSlots(53)));

    private final String path;
    @Setter
    private Object value;

}
