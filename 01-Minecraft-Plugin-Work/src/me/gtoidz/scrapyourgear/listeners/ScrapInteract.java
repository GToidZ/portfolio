package me.gtoidz.scrapyourgear.listeners;

import me.gtoidz.scrapyourgear.ScrapYourGear;
import me.gtoidz.scrapyourgear.utils.ConfigReader;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ScrapInteract implements Listener {

    @EventHandler
    public void anvilInteractEvent(PlayerInteractEvent event){
        Player p = event.getPlayer();
        Action a = event.getAction();
        if(a == Action.RIGHT_CLICK_BLOCK && (event.getClickedBlock().getType().equals(Material.ANVIL) || event.getClickedBlock().getType().equals(Material.CHIPPED_ANVIL) || event.getClickedBlock().getType().equals(Material.DAMAGED_ANVIL))) {
            if(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
                if (p.isOp() || p.hasPermission("syg.bypass")) {
                    if (event.getClickedBlock().getRelative(BlockFace.DOWN).getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("blocks.sell-block"))) {
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
                        event.setCancelled(true);
                        ScrapSellGui gui = new ScrapSellGui();
                        gui.initGui();
                        p.openInventory(gui.getInventory());
                    }
                    if (event.getClickedBlock().getRelative(BlockFace.DOWN).getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("blocks.exp-block"))) {
                        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
                        event.setCancelled(true);
                        ScrapExpGui gui = new ScrapExpGui();
                        gui.initGui(false);
                        p.openInventory(gui.getInventory());
                    }
                } else {
                    p.playSound(p.getLocation(), Sound.ENTITY_GHAST_HURT, 1.0f, 1.0f);
                    p.sendMessage(ScrapYourGear.prefixedMessage("chat.creative-error"));
                }
            } else {
                if (event.getClickedBlock().getRelative(BlockFace.DOWN).getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("blocks.sell-block"))) {
                    p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
                    event.setCancelled(true);
                    ScrapSellGui gui = new ScrapSellGui();
                    gui.initGui();
                    p.openInventory(gui.getInventory());
                }
                if (event.getClickedBlock().getRelative(BlockFace.DOWN).getType().toString().equalsIgnoreCase(ConfigReader.getConfig().getString("blocks.exp-block"))) {
                    p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
                    event.setCancelled(true);
                    ScrapExpGui gui = new ScrapExpGui();
                    gui.initGui(false);
                    p.openInventory(gui.getInventory());
                }
            }
        }
    }

}
