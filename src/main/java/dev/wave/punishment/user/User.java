package dev.wave.punishment.user;

import com.mongodb.client.MongoCollection;
import dev.wave.punishment.Punishments;
import dev.wave.punishment.packet.SavePacket;
import dev.wave.punishment.punishment.Punishment;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class User {

    private final UUID uuid;
    private final List<Punishment> punishmentList;
    private final List<Punishment> activePunishmentList;

    @Setter
    private User historyTarget;
    @Setter
    private int historyPage;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.punishmentList = new ArrayList<>();
        this.activePunishmentList = new ArrayList<>();
        pullPunishments();
    }

    public void addPunishment(Punishment punishment) {
        punishmentList.add(punishment);
    }

    private void pullPunishments() {
        MongoCollection<Document> collection;
        try {
            collection = Punishments.getInstance().getDatabase().getCollection(uuid.toString());
        } catch (IllegalArgumentException e) {
            Punishments.getInstance().getDatabase().createCollection(uuid.toString());
            return;
        }
        for (Document document : collection.find()) {
            Punishment punishment = Punishment.fromDocument(document);
            if (punishment == null) {
                continue;
            }

            punishmentList.add(punishment);
            if (!punishment.isActive()) {
                continue;
            }

            if(punishment.getExpiryDate() != null && punishment.getExpiryDate().getTime() < System.currentTimeMillis()){
                punishment.setActive(false);
                continue;
            }

            activePunishmentList.add(punishment);
        }

        new SavePacket(this).save();

    }


    public void removeActivePunishment(Punishment activePunishment) {
        punishmentList.remove(activePunishment);
        activePunishmentList.remove(activePunishment);
        activePunishment.setActive(false);
        punishmentList.add(activePunishment);
    }
}
