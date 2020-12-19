package me.gtoidz.scrapyourgear.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class InvUtils {

    public static ItemStack createGuiItem(Material mat, String name, String... lore){
        ItemStack itemStack = new ItemStack(mat, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);

        ArrayList<String> loreToAdd = new ArrayList<String>(Arrays.asList(lore));

        itemMeta.setLore(loreToAdd);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
