package me.otisps.deathban;

import me.otisps.deathban.commands.DeathBanCommand;
import me.otisps.deathban.listeners.DeathListener;
import me.otisps.deathban.listeners.JoinListener;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DeathBan extends JavaPlugin {
    private static DeathBan instance;
    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final Pattern linkPattern = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
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

        this.getCommand("deathban").setExecutor(new DeathBanCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[DeathBan] Plugin Disabled!");

    }
    private void createCustomConfig() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
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
        return data.contains("bans." + player.getUniqueId().toString());
    }

    public void addUser(Player player) throws IOException {
        data.set("bans." + player.getUniqueId().toString() + "", true);
        data.save(dataFile);
    }

    public void removeUser(String uuid) throws IOException {
        data.set("bans." + uuid + "", null);
        data.save(dataFile);
    }

    public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void storeUsername(Player player){
        String username = player.getName().toLowerCase();
        String playerId = player.getUniqueId().toString();
        data.set("usernames." + username, playerId);
        try {
            data.save(dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String findUUID(String string){
        Map<String, Object> userIdMap = this.data.getConfigurationSection("usernames").getValues(false);
        for (String username: userIdMap.keySet()) {
            if(username.equalsIgnoreCase(string)){
                return userIdMap.get(username).toString();
            }
        }
        return "069a79f4-44e9-4726-a5be-fca90e38aaf5";
    }
    public String getBanMessage(){
        String message = config.get("ban-message").toString();
        message = hex(message);
        return message;
    }
}
