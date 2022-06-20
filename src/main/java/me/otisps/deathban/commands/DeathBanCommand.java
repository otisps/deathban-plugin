package me.otisps.deathban.commands;

import me.otisps.deathban.DeathBan;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Locale;

public class DeathBanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) return true;
        DeathBan.getInstance().reloadConfig();
        if (args.length == 2 && args[1].equalsIgnoreCase("reset")) {
            try {
                String username = args[0].toLowerCase(Locale.ROOT);
                DeathBan plugin = DeathBan.getInstance();
                plugin.removeUser(plugin.findUUID(username));
                System.out.println("Player " + username + " was removed, With UUID: " + plugin.findUUID(username));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return true;
    }
}
