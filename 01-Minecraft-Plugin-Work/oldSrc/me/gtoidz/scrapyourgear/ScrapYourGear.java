package me.gtoidz.scrapyourgear;

import me.gtoidz.scrapyourgear.commands.ScrapCommand;
import me.gtoidz.scrapyourgear.listeners.ScrapGuiListener;
import me.gtoidz.scrapyourgear.listeners.ScrapInteract;
import me.gtoidz.scrapyourgear.utils.ConfigReader;
import me.gtoidz.scrapyourgear.utils.StaticItemList;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ScrapYourGear extends JavaPlugin {

    private static ScrapYourGear instance;
    private static Economy eco = null;

    public static Economy getEco() {
        return eco;
    }

    @Override
    public void onEnable(){
        if(!setupEconomy()){
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        instance = this;
        ConfigReader.reloadConfig();
        registerEvents(Bukkit.getPluginManager());
        registerCommands();
        StaticItemList.initList();
    }

    @Override
    public void onDisable(){
        instance = null;
    }

    public static ScrapYourGear getInstance(){
        return instance;
    }

    private void registerEvents(PluginManager manager){
        manager.registerEvents(new ScrapInteract(), this);
        manager.registerEvents(new ScrapGuiListener(), this);
    }

    private void registerCommands(){
        this.getCommand("scrap").setExecutor(new ScrapCommand());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return true;
    }

    public static String prefixedMessage(String path){
        return ConfigReader.getMessage("chat.prefix") + "" + ConfigReader.getMessage(path);
    }

}
