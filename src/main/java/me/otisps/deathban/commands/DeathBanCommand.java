package me.otisps.deathban.commands;

import me.otisps.deathban.DeathBan;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeathBanCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) return true;
        DeathBan.getInstance().reloadConfig();
        if (args.length == 2 && args[1].equalsIgnoreCase("reset")) {
            try {
                DeathBan.getInstance().removeUser(DeathBan.getInstance().getServer().getPlayer(args[0]));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabCompletes = new ArrayList<>();
        tabCompletes.add("reload");
        return tabCompletes;
    }
}
