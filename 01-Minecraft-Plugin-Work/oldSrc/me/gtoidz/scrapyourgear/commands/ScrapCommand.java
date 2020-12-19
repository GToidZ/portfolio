package me.gtoidz.scrapyourgear.commands;

import me.gtoidz.scrapyourgear.ScrapYourGear;
import me.gtoidz.scrapyourgear.listeners.ScrapExpGui;
import me.gtoidz.scrapyourgear.listeners.ScrapSellGui;
import me.gtoidz.scrapyourgear.utils.ConfigReader;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ScrapCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1){
            sendHelpMessage(sender);
            return true;
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")){
                sendHelpMessage(sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                if(sender.isOp() || sender.hasPermission("syg.reload")) {
                    ConfigReader.reloadConfig();
                    sender.sendMessage(ScrapYourGear.prefixedMessage("chat.reload-message"));
                } else {
                    sender.sendMessage(ScrapYourGear.prefixedMessage("chat.permission-error"));
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("money")){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    if(player.hasPermission("syg.command.money")) {
                        if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)){
                            if(player.isOp() || player.hasPermission("syg.bypass")){
                                ScrapSellGui gui = new ScrapSellGui();
                                gui.initGui();
                                player.openInventory(gui.getInventory());
                            } else {
                                player.sendMessage(ScrapYourGear.prefixedMessage("chat.creative-error"));
                            }
                        } else {
                            ScrapSellGui gui = new ScrapSellGui();
                            gui.initGui();
                            player.openInventory(gui.getInventory());
                        }
                    } else {
                        sender.sendMessage(ScrapYourGear.prefixedMessage("chat.permission-error"));
                    }
                } else {
                    sender.sendMessage(ScrapYourGear.prefixedMessage("chat.not-a-player-error"));
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("exp")){
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    if(player.hasPermission("syg.command.exp")) {
                        if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)){
                            if(player.isOp() || player.hasPermission("syg.bypass")){
                                ScrapExpGui gui = new ScrapExpGui();
                                gui.initGui(false);
                                player.openInventory(gui.getInventory());
                            } else {
                                player.sendMessage(ScrapYourGear.prefixedMessage("chat.creative-error"));
                            }
                        } else {
                            ScrapExpGui gui = new ScrapExpGui();
                            gui.initGui(false);
                            player.openInventory(gui.getInventory());
                        }
                    } else {
                        sender.sendMessage(ScrapYourGear.prefixedMessage("chat.permission-error"));
                    }
                } else {
                    sender.sendMessage(ScrapYourGear.prefixedMessage("chat.not-a-player-error"));
                }
                return true;
            } else {
                sender.sendMessage(ScrapYourGear.prefixedMessage("chat.arguments-error"));
                return true;
            }
        }
        return false;
    }

    void sendHelpMessage(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("§3§lSCRAP YOUR GEAR §av" + ScrapYourGear.getInstance().getDescription().getVersion() + "     §7§o@GlitchToidZ");
            sender.sendMessage("§3/scrap reload §f- reloads the plugin's configurations.");
            sender.sendMessage("§3/scrap money §f- opens scrap GUI for selling.");
            sender.sendMessage("§3/scrap exp §f- opens scrap GUI for grinding.");
            sender.sendMessage("§eIf you wish to configure the plugin, check the \"documentation.html\" in the plugin file.");
            return;
        }
        sender.sendMessage("\n§3§lSCRAP YOUR GEAR §av" + ScrapYourGear.getInstance().getDescription().getVersion() + "     §7§o@GlitchToidZ");
        if (sender.isOp() || sender.hasPermission("syg.admin")) {
            sender.sendMessage("\n§3/scrap reload §f- reloads the plugin's configurations.");
        } else {
            sender.sendMessage("\n§c- Hidden admin command. -");
        }
        sender.sendMessage("§3/scrap money §f- opens scrap GUI for selling.");
        sender.sendMessage("§3/scrap exp §f- opens scrap GUI for grinding.");
        if (!(sender instanceof Player)) return;
        if (!sender.isOp() && !sender.hasPermission("syg.admin")) return;
        sender.sendMessage("\n§eYou have been acknowledged as administrator; if you wish to configure the plugin, check the \"documentation.html\" in the plugin file.\n");
    }

}
