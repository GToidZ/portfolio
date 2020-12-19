package me.gtoidz.scrapyourgear.utils;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class StaticItemList {

    public static Map<Material, ItemType> itemTypeList = new HashMap<Material, ItemType>();
    
    public static void initList(){
        if(itemTypeList.isEmpty()){
            itemTypeList.put(Material.WOODEN_SWORD, ItemType.WOOD);
            itemTypeList.put(Material.STONE_SWORD, ItemType.STONE);
            itemTypeList.put(Material.GOLDEN_SWORD, ItemType.GOLD);
            itemTypeList.put(Material.IRON_SWORD, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_SWORD, ItemType.DIAMOND);
            
            itemTypeList.put(Material.WOODEN_AXE, ItemType.WOOD);
            itemTypeList.put(Material.STONE_AXE, ItemType.STONE);
            itemTypeList.put(Material.GOLDEN_AXE, ItemType.GOLD);
            itemTypeList.put(Material.IRON_AXE, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_AXE, ItemType.DIAMOND);
            
            itemTypeList.put(Material.WOODEN_PICKAXE, ItemType.WOOD);
            itemTypeList.put(Material.STONE_PICKAXE, ItemType.STONE);
            itemTypeList.put(Material.GOLDEN_PICKAXE, ItemType.GOLD);
            itemTypeList.put(Material.IRON_PICKAXE, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_PICKAXE, ItemType.DIAMOND);
            
            itemTypeList.put(Material.WOODEN_SHOVEL, ItemType.WOOD);
            itemTypeList.put(Material.STONE_SHOVEL, ItemType.STONE);
            itemTypeList.put(Material.GOLDEN_SHOVEL, ItemType.GOLD);
            itemTypeList.put(Material.IRON_SHOVEL, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_SHOVEL, ItemType.DIAMOND);
            
            itemTypeList.put(Material.LEATHER_HELMET, ItemType.LEATHER);
            itemTypeList.put(Material.CHAINMAIL_HELMET, ItemType.CHAIN);
            itemTypeList.put(Material.GOLDEN_HELMET, ItemType.GOLD);
            itemTypeList.put(Material.IRON_HELMET, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_HELMET, ItemType.DIAMOND);

            itemTypeList.put(Material.LEATHER_CHESTPLATE, ItemType.LEATHER);
            itemTypeList.put(Material.CHAINMAIL_CHESTPLATE, ItemType.CHAIN);
            itemTypeList.put(Material.GOLDEN_CHESTPLATE, ItemType.GOLD);
            itemTypeList.put(Material.IRON_CHESTPLATE, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_CHESTPLATE, ItemType.DIAMOND);

            itemTypeList.put(Material.LEATHER_LEGGINGS, ItemType.LEATHER);
            itemTypeList.put(Material.CHAINMAIL_LEGGINGS, ItemType.CHAIN);
            itemTypeList.put(Material.GOLDEN_LEGGINGS, ItemType.GOLD);
            itemTypeList.put(Material.IRON_LEGGINGS, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_LEGGINGS, ItemType.DIAMOND);

            itemTypeList.put(Material.LEATHER_BOOTS, ItemType.LEATHER);
            itemTypeList.put(Material.CHAINMAIL_BOOTS, ItemType.CHAIN);
            itemTypeList.put(Material.GOLDEN_BOOTS, ItemType.GOLD);
            itemTypeList.put(Material.IRON_BOOTS, ItemType.IRON);
            itemTypeList.put(Material.DIAMOND_BOOTS, ItemType.DIAMOND);

            itemTypeList.put(Material.TRIDENT, ItemType.TRIDENT);
            itemTypeList.put(Material.CROSSBOW, ItemType.CROSSBOW);
            itemTypeList.put(Material.BOW, ItemType.BOW);
            itemTypeList.put(Material.FISHING_ROD, ItemType.ROD);

        }
    }
    
    public enum ItemType{
        WOOD, LEATHER, STONE, CHAIN, GOLD, IRON, DIAMOND, TRIDENT, CROSSBOW, BOW, ROD
    }

}
