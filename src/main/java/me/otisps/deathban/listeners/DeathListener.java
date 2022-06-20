package me.otisps.deathban.listeners;

import me.otisps.deathban.DeathBan;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;

public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.kickPlayer(DeathBan.getInstance().getBanMessage());
        try {
            DeathBan.getInstance().addUser(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
