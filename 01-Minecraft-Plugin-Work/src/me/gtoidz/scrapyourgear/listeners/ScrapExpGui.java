package me.gtoidz.scrapyourgear.listeners;

import me.gtoidz.scrapyourgear.utils.ConfigReader;
import me.gtoidz.scrapyourgear.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ScrapExpGui implements InventoryHolder {

    private final Inventory expGui;

    public ScrapExpGui(){
        expGui = Bukkit.createInventory(this, 54, ConfigReader.getMessage("gui.title"));
    }

    public Inventory getInventory(){
        return expGui;
    }

    public void initGui(boolean bottles) {
        for (int i = 0; i <= 8; i++) {
            if (i == 4) {
                expGui.setItem(i, InvUtils.createGuiItem(Material.getMaterial(Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.info"))), "§b§lINFO", "§fPut the items you wanted to scrap into this GUI.", "§fWhen you're ready, press the §aBottle o' Enchanting §fat the bottom.", "§fThe items will be graded automatically."));
            } else {
                expGui.setItem(i, new ItemStack(Material.getMaterial((Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.decorations")))), 1));
            }
        }
        for (int i = 45; i <= 52; i++) {
            if (i == 49) {
                expGui.setItem(i, InvUtils.createGuiItem(Material.getMaterial(Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.button"))), "§a§lSCRAP ALL", "§cNo reclaims! All procedures are final!"));
            } else {
                expGui.setItem(i, new ItemStack(Material.getMaterial((Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.decorations")))), 1));
            }
        }
        if (!bottles) {
            expGui.setItem(53, InvUtils.createGuiItem(Material.getMaterial((Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.bottle-disable")))), "§aPrefer Bottles?", "§cNo"));
        } else {
            expGui.setItem(53, InvUtils.createGuiItem(Material.getMaterial((Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.bottle-enable")))), "§aPrefer Bottles?", "§aYes"));
        }
    }
    
}
