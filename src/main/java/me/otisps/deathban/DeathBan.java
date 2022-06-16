package me.otisps.deathban;

import me.otisps.deathban.listeners.DeathListener;
import me.otisps.deathban.listeners.JoinListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class DeathBan extends JavaPlugin {
    private static DeathBan instance;
    private FileConfiguration config = this.getConfig();
    private File dataFile;
    private FileConfiguration data;

    public static DeathBan getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        this.getConfig();
        createCustomConfig();

        instance = this;
        System.out.println("[DeathBan] Plugin Enabled!");

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[DeathBan] Plugin Disabled!");

    }
    private void createCustomConfig() {
        dataFile = new File(getDataFolder(), "custom.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("custom.yml", false);
        }

        data = new YamlConfiguration();
        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCustomConfig() {
        return this.data;
    }

    public boolean isUserPresent(Player player){
        return data.contains(player.getUniqueId() + "");
    }

    public void addUser(Player player) throws IOException {
        data.set(player.getUniqueId() + "", true);
        data.save(dataFile);
    }

    public void removeUser(Player player) throws IOException {
        data.set(player.getUniqueId() + "", null);
        data.save(dataFile);
    }

    public String getBanMessage(){
        String message = config.get("ban-message").toString();
        return message;
    }
}
