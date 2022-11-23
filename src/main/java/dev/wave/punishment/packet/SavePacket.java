package dev.wave.punishment.packet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import dev.wave.punishment.Punishments;
import dev.wave.punishment.user.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavePacket {

    private final UUID uuid;
    private final List<Document> punishmentPackets;

    public SavePacket(User user){
        this.punishmentPackets = new ArrayList<>();
        this.uuid = user.getUuid();
        user.getPunishmentList().forEach(punishment -> punishmentPackets.add(new PunishmentPacket(punishment).asDocument()));
    }

    public void save(){

       MongoCollection<Document> collection;
       try{
           collection = Punishments.getInstance().getDatabase().getCollection(uuid.toString());
       }catch (IllegalArgumentException e){
           Punishments.getInstance().getDatabase().createCollection(uuid.toString());
              collection = Punishments.getInstance().getDatabase().getCollection(uuid.toString());
       }
            collection.deleteOne(new Document("uuid", uuid.toString()));
            collection.insertOne(new Document("uuid", uuid.toString()).append("punishments", punishmentPackets));
    }

}
