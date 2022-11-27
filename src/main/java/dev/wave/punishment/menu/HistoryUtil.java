package dev.wave.punishment.menu;

import dev.wave.punishment.punishment.Punishment;
import dev.wave.punishment.user.User;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.Inventory;

import java.util.List;

@UtilityClass
public class HistoryUtil {

    private int getPages(User user){
        List<Punishment> punishmentList = user.getPunishmentList();

        int size = punishmentList.size();
        int remainder = punishmentList.size() % 36;

        size = size - remainder;

        if(size == 0){
            return 1;
        }

        return size/36;

    }
    public void showPage(User punishUser, int page, Inventory inventory){

        if(page < 1){
            page = 1;
        }

        User user = punishUser.getHistoryTarget();

        punishUser.setHistoryPage(page);

        if(page > getPages(user)){
            return;
        }

        int start = (page-1)*36;

        int slot = 9;

        for(int i = start; i < start+36; i++){

            if(i >= user.getPunishmentList().size()){
                break;
            }

            Punishment punishment = user.getPunishmentList().get(i);

            inventory.setItem(slot, null);

            if(punishment == null){
                slot++;
                continue;
            }

            inventory.setItem(slot, punishment.getItem());
            slot++;
        }

    }


}
