package me.otisps.deathban.listeners;

import me.otisps.deathban.DeathBan;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        player.kickPlayer(DeathBan.getInstance().getBanMessage());
    }
}
