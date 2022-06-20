package me.otisps.deathban.listeners;

import me.otisps.deathban.DeathBan;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void playerJoinEvent(PlayerJoinEvent event){
        DeathBan.getInstance().storeUsername(event.getPlayer());
        if(DeathBan.getInstance().isUserPresent(event.getPlayer())){
            event.getPlayer().kickPlayer(DeathBan.getInstance().getBanMessage());
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerEnter(final PlayerJoinEvent event) {
        if (event.getPlayer().isDead()) {
            event.getPlayer().spigot().respawn();
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }
}
