package me.gtoidz.scrapyourgear.utils;

import me.gtoidz.scrapyourgear.ScrapYourGear;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigReader {

    private static final int VERSION = 3;

    private static File configFile;
    private static File potentialFile;
    private static File messagesFile;

    private static FileConfiguration config;
    private static FileConfiguration potentials;
    private static FileConfiguration messages;

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getPotentialConfig() {
        return potentials;
    }

    public static FileConfiguration getMessagesConfig() {
        return messages;
    }

    public static void reloadConfig(){
        if(configFile == null){
            configFile = new File(ScrapYourGear.getInstance().getDataFolder(), "config.yml");
        }
        if(potentialFile == null){
            potentialFile = new File(ScrapYourGear.getInstance().getDataFolder(), "potentials.yml");
        }
        if(messagesFile == null){
            messagesFile = new File(ScrapYourGear.getInstance().getDataFolder(), "messages.yml");
        }

        if(!configFile.exists()){
            ScrapYourGear.getInstance().saveResource("config.yml", false);
        }
        if(!potentialFile.exists()){
            ScrapYourGear.getInstance().saveResource("potentials.yml", false);
        }
        if(!messagesFile.exists()){
            ScrapYourGear.getInstance().saveResource("messages.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        potentials = YamlConfiguration.loadConfiguration(potentialFile);
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        if(config.getInt("version") != VERSION){
            ScrapYourGear.getInstance().saveResource("config.yml", true);
            ScrapYourGear.getInstance().saveResource("potentials.yml", true);
            ScrapYourGear.getInstance().saveResource("messages.yml", true);

            config = YamlConfiguration.loadConfiguration(configFile);
            potentials = YamlConfiguration.loadConfiguration(potentialFile);
            messages = YamlConfiguration.loadConfiguration(messagesFile);
        }

    }

    public static String getMessage(String path){
        return ChatColor.translateAlternateColorCodes('&', getMessagesConfig().getString(path));
    }
}
