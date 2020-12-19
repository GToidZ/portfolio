package me.gtoidz.scrapyourgear.listeners;

import me.gtoidz.scrapyourgear.utils.ConfigReader;
import me.gtoidz.scrapyourgear.utils.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ScrapSellGui implements InventoryHolder, Listener {

    private final Inventory sellGui;

    public ScrapSellGui(){
        sellGui = Bukkit.createInventory(this, 54, ConfigReader.getMessage("gui.title"));
    }

    public Inventory getInventory(){
        return sellGui;
    }

    public void initGui(){
        for(int i = 0; i <= 8; i++){
            if(i == 4){
                sellGui.setItem(i, InvUtils.createGuiItem(Material.getMaterial(Objects.requireNonNull(ConfigReader.getConfig().getString("gui.sell-gui.info"))), "§b§lINFO", "§fPut the items you wanted to sell into this GUI.", "§fWhen you're ready, press the §aEmerald §fat the bottom.", "§fThe items will be graded and priced automatically."));
            } else {
                sellGui.setItem(i, new ItemStack(Material.getMaterial((Objects.requireNonNull(ConfigReader.getConfig().getString("gui.sell-gui.decorations")))), 1));
            }
        }
        for(int i = 45; i <= 53; i++){
            if(i == 49){
                sellGui.setItem(i, InvUtils.createGuiItem(Material.getMaterial(Objects.requireNonNull(ConfigReader.getConfig().getString("gui.sell-gui.button"))), "§a§lSELL ALL", "§cNo reclaims! All transactions are final!"));
            } else {
                sellGui.setItem(i, new ItemStack(Material.getMaterial((Objects.requireNonNull(ConfigReader.getConfig().getString("gui.sell-gui.decorations")))), 1));
            }
        }
    }

}
