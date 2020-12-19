package me.gtoidz.scrapyourgear.listeners;

import me.gtoidz.scrapyourgear.ScrapYourGear;
import me.gtoidz.scrapyourgear.utils.ConfigReader;
import me.gtoidz.scrapyourgear.utils.GradeCalculation;
import me.gtoidz.scrapyourgear.utils.StaticItemList;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ScrapGuiListener implements Listener {

    private boolean scraped = false;
    private boolean swapped = false;

    @EventHandler
    public void interactGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory targetGui = event.getView().getTopInventory();

        if (targetGui.getHolder() instanceof ScrapSellGui) {
            if ((event.getRawSlot() >= 0 && event.getRawSlot() <= 8) || (event.getRawSlot() >= 45 && event.getRawSlot() <= 53)) {
                event.setCancelled(true);
                if (event.getCurrentItem().getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("gui.sell-gui.button")) && event.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lSELL ALL")) {
                    double reward = 0.0;
                    boolean unaccepted = false;
                    for (int i = 9; i <= 44; i++) {
                        if(targetGui.getItem(i) != null) {
                            ItemStack item = event.getInventory().getItem(i);
                            if (!item.getType().equals(Material.AIR)) {
                                if (StaticItemList.itemTypeList.containsKey(item.getType())) {
                                    reward += GradeCalculation.calculatePrice(item);
                                    if(!scraped) scraped = true;
                                } else {
                                    event.getWhoClicked().getInventory().addItem(item);
                                    unaccepted = true;
                                }
                            }
                        }
                    }
                    ScrapYourGear.getEco().depositPlayer((OfflinePlayer) event.getWhoClicked(), reward);
                    if (scraped) {
                        String message = ScrapYourGear.prefixedMessage("chat.money-reward-message").replace("<money>", String.valueOf(Math.round(reward * 100) / 100));
                        player.sendMessage(message);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
                    }
                    if(!scraped){
                        scraped = true;
                        player.sendMessage(ScrapYourGear.prefixedMessage("chat.nothing-message"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    if (unaccepted) {
                        player.sendMessage(ScrapYourGear.prefixedMessage("chat.unwanted-items-message"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f);
                    }
                    player.closeInventory();
                }
            }
        }

        if (targetGui.getHolder() instanceof ScrapExpGui) {
            if ((event.getRawSlot() >= 0 && event.getRawSlot() <= 8) || (event.getRawSlot() >= 45 && event.getRawSlot() <= 53)) {
                event.setCancelled(true);
                if (event.getCurrentItem().getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("gui.exp-gui.button")) && event.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lSCRAP ALL")) {
                    int reward = 0;
                    boolean unaccepted = false;

                    if(targetGui.getItem(53).getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("gui.exp-gui.bottle-enable"))){
                        for (int i = 9; i <= 44; i++) {
                            if (targetGui.getItem(i) != null) {
                                ItemStack item = event.getInventory().getItem(i);
                                if (!item.getType().equals(Material.AIR)) {
                                    if (StaticItemList.itemTypeList.containsKey(item.getType())) {
                                        reward += GradeCalculation.calculateExp(item, true);
                                        if(!scraped) scraped = true;
                                    } else {
                                        event.getWhoClicked().getInventory().addItem(item);
                                        unaccepted = true;
                                    }
                                }
                            }
                        }
                        ItemStack expPots = new ItemStack(Material.EXPERIENCE_BOTTLE);
                        expPots.setAmount(reward);

                        if(scraped) {
                            String message = ScrapYourGear.prefixedMessage("chat.bottles-reward-message").replace("<number>", String.valueOf(reward));
                            player.getInventory().addItem(expPots);
                            player.sendMessage(message);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        }

                    } else if(targetGui.getItem(53).getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("gui.exp-gui.bottle-disable"))) {
                        for (int i = 9; i <= 44; i++) {
                            if (targetGui.getItem(i) != null) {
                                ItemStack item = event.getInventory().getItem(i);
                                if (!item.getType().equals(Material.AIR)) {
                                    if (StaticItemList.itemTypeList.containsKey(item.getType())) {
                                        reward += GradeCalculation.calculateExp(item, false);
                                        if (!scraped) scraped = true;
                                    } else {
                                        event.getWhoClicked().getInventory().addItem(item);
                                        unaccepted = true;
                                    }
                                }
                            }
                        }

                        if(scraped){
                            String message = ScrapYourGear.prefixedMessage("chat.exp-reward-message").replace("<exp>", String.valueOf(reward));
                            player.giveExp(reward);
                            player.sendMessage(message);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        }
                    }

                    if(!scraped){
                        scraped = true;
                        player.sendMessage(ScrapYourGear.prefixedMessage("chat.nothing-message"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }

                    if (unaccepted) {
                        player.sendMessage(ScrapYourGear.prefixedMessage("chat.unwanted-items-message"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.5f, 1.0f);
                    }
                    player.closeInventory();
                }

                if(event.getSlot() == 53) {
                    if (event.getCurrentItem().getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("gui.exp-gui.bottle-disable"))) {
                        //targetGui.setItem(53, InvUtils.createGuiItem(Material.getMaterial(Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.bottle-enable"))), "§aPrefer Bottles?", "§aYes"));
                        Map<Integer, ItemStack> items = new HashMap<>();
                        for (int i = 9; i <= 44; i++) {
                            if (targetGui.getItem(i) != null) {
                                ItemStack item = event.getInventory().getItem(i);
                                items.put(i, item);
                            }
                        }
                        ScrapExpGui newGui = new ScrapExpGui();
                        newGui.initGui(true);
                        for(Map.Entry<Integer, ItemStack> entry:items.entrySet()) {
                            newGui.getInventory().setItem(entry.getKey(), entry.getValue());
                        }
                        swapped = true;
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
                        player.closeInventory();
                        player.openInventory(newGui.getInventory());
                        swapped = false;
                    }

                    if (event.getCurrentItem().getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("gui.exp-gui.bottle-enable"))) {
                        //targetGui.setItem(53, InvUtils.createGuiItem(Material.getMaterial(Objects.requireNonNull(ConfigReader.getConfig().getString("gui.exp-gui.bottle-disable"))), "§aPrefer Bottles?", "§cNo"));
                        Map<Integer, ItemStack> items = new HashMap<>();
                        for (int i = 9; i <= 44; i++) {
                            if (targetGui.getItem(i) != null) {
                                ItemStack item = event.getInventory().getItem(i);
                                items.put(i, item);
                            }
                        }
                        ScrapExpGui newGui = new ScrapExpGui();
                        newGui.initGui(false);
                        for(Map.Entry<Integer, ItemStack> entry:items.entrySet()) {
                            newGui.getInventory().setItem(entry.getKey(), entry.getValue());
                        }
                        swapped = true;
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
                        player.closeInventory();
                        player.openInventory(newGui.getInventory());
                        swapped = false;
                    }
                }
            }
        }
    }

    @EventHandler
    public void closeGui(InventoryCloseEvent event){
        Inventory targetGui = event.getView().getTopInventory();
        if(targetGui.getHolder() instanceof ScrapSellGui || targetGui.getHolder() instanceof ScrapExpGui){
            if(!scraped && !swapped){
                for(int i = 9; i <= 44; i++){
                    if(targetGui.getItem(i) != null){
                        ItemStack item = event.getInventory().getItem(i);
                        if(!item.getType().equals(Material.AIR)){
                            event.getPlayer().getInventory().addItem(item);
                        }
                    }
                }
            }
            scraped = false;
        }
    }


}
