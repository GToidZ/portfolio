package me.gtoidz.scrapyourgear.utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradeCalculation {

    private static FileConfiguration potentials = ConfigReader.getPotentialConfig();
    private static boolean debug = ConfigReader.getConfig().getBoolean("debug");

    public static double calculatePrice(ItemStack item){
        double material = evaluateMaterialPotential(item, Procedure.SELL);
        double durability = evaluateDurabilityPotential(item, Procedure.SELL);

        /*Enchants Section*/
        List<Double> enchantsPotentials = new ArrayList<Double>();

        for(Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()){
            double enchantment = evaluateEnchantmentPotential(entry.getKey(), entry.getValue(), Procedure.SELL);
            enchantsPotentials.add(enchantment);
        }

        double enchantment = 0.0;
        for(Double d:enchantsPotentials){
            enchantment += d;
        }

        String formula = ConfigReader.getConfig().getString("formulas.sell.total-formula").replace("<var1>", String.valueOf(material)).replace("<var2>", String.valueOf(durability)).replace("<var3>", String.valueOf(enchantment));
        if(debug) System.out.println(formula);
        Expression e = new ExpressionBuilder(formula).build();
        return e.evaluate();
    }

    public static int calculateExp(ItemStack item, boolean bottles){
        double material = evaluateMaterialPotential(item, Procedure.EXP);
        double durability = evaluateDurabilityPotential(item, Procedure.EXP);

        /*Enchants Section*/
        List<Double> enchantsPotentials = new ArrayList<Double>();

        for(Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()){
            double enchantment = evaluateEnchantmentPotential(entry.getKey(), entry.getValue(), Procedure.EXP);
            enchantsPotentials.add(enchantment);
        }

        double enchantment = 0.0;
        for(Double d:enchantsPotentials){
            enchantment += d;
        }

        String formula = ConfigReader.getConfig().getString("formulas.exp.total-formula").replace("<var1>", String.valueOf(material)).replace("<var2>", String.valueOf(durability)).replace("<var3>", String.valueOf(enchantment));
        if(debug) System.out.println(formula);

        if(bottles){
            Expression e = new ExpressionBuilder(formula).build();
            return (int) e.evaluate() / ConfigReader.getConfig().getInt("formulas.exp.bottle-divisor");
        } else {
            Expression e = new ExpressionBuilder(formula).build();
            return (int) e.evaluate();
        }
    }

    enum Procedure {
        SELL, EXP
    }

    static double evaluateMaterialPotential(ItemStack item, Procedure procedure){
        double materialPotential;
        StaticItemList.ItemType material = StaticItemList.itemTypeList.get(item.getType());
        switch(material){
            case WOOD:
                materialPotential = potentials.getDouble("material.wood");
                if(debug) System.out.println("WOOD");
                break;
            case LEATHER:
                materialPotential = potentials.getDouble("material.leather");
                if(debug) System.out.println("LEATHER");
                break;
            case STONE:
                materialPotential = potentials.getDouble("material.stone");
                if(debug) System.out.println("STONE");
                break;
            case CHAIN:
                materialPotential = potentials.getDouble("material.chain");
                if(debug) System.out.println("CHAIN");
                break;
            case GOLD:
                materialPotential = potentials.getDouble("material.gold");
                if(debug) System.out.println("GOLD");
                break;
            case IRON:
                materialPotential = potentials.getDouble("material.iron");
                if(debug) System.out.println("IRON");
                break;
            case DIAMOND:
                materialPotential = potentials.getDouble("material.diamond");
                if(debug) System.out.println("DIAMOND");
                break;
            case TRIDENT:
                materialPotential = potentials.getDouble("material.trident");
                if(debug) System.out.println("TRIDENT");
                break;
            case CROSSBOW:
                materialPotential = potentials.getDouble("material.crossbow");
                if(debug) System.out.println("CROSSBOW");
                break;
            case BOW:
                materialPotential = potentials.getDouble("material.bow");
                if(debug) System.out.println("BOW");
                break;
            case ROD:
                materialPotential = potentials.getDouble("material.rod");
                if(debug) System.out.println("ROD");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + material);
        }

        String formula = "";
        switch (procedure){
            case SELL:
                formula = ConfigReader.getConfig().getString("formulas.sell.material-formula").replace("<var1>", String.valueOf(materialPotential));
                break;
            case EXP:
                formula = ConfigReader.getConfig().getString("formulas.exp.material-formula").replace("<var1>", String.valueOf(materialPotential));
                break;
        }
        if(debug) System.out.println(formula);
        Expression e = new ExpressionBuilder(formula).build();
        return e.evaluate();
    }

    static double evaluateDurabilityPotential(ItemStack item, Procedure procedure){
        Damageable data = (Damageable) item.getItemMeta();

        double durabilityPotential;

        int durability = (int) item.getType().getMaxDurability() - data.getDamage();

        float durabilityPercent = durability * 100 / (int) item.getType().getMaxDurability();

        if(debug) System.out.println("CurrentDurability: "+durability);
        if(debug) System.out.println("MaxDurability: "+(int) item.getType().getMaxDurability());
        if(debug) System.out.println("PercentageDurability: "+durabilityPercent);

        if (durabilityPercent == 100) {
            durabilityPotential = potentials.getDouble("durabilities.best");
            if(debug) System.out.println("DurabilityPot: 100");
        } else if (durabilityPercent < 100 && durabilityPercent >= 75) {
            durabilityPotential = potentials.getDouble("durabilities.good");
            if(debug) System.out.println("DurabilityPot: 75");
        } else if (durabilityPercent < 75 && durabilityPercent >= 50) {
            durabilityPotential = potentials.getDouble("durabilities.fine");
            if(debug) System.out.println("DurabilityPot: 50");
        } else if (durabilityPercent < 50 && durabilityPercent >= 25) {
            durabilityPotential = potentials.getDouble("durabilities.bad");
            if(debug) System.out.println("DurabilityPot: 25");
        } else {
            durabilityPotential = potentials.getDouble("durabilities.broken");
            if(debug) System.out.println("DurabilityPot: 0");
        }

        String formula = "";
        switch (procedure){
            case SELL:
                formula = ConfigReader.getConfig().getString("formulas.sell.durability-formula").replace("<var1>", String.valueOf(durabilityPotential));
                break;
            case EXP:
                formula = ConfigReader.getConfig().getString("formulas.exp.durability-formula").replace("<var1>", String.valueOf(durabilityPotential));
                break;
        }
        if(debug) System.out.println(formula);
        Expression e = new ExpressionBuilder(formula).build();
        return e.evaluate();
    }

    static double evaluateEnchantmentPotential(Enchantment enchantment, int level, Procedure procedure){

        if(debug) System.out.println("RawEnchant: "+enchantment+" ; "+enchantment.getKey()+" / "+level);
        String finalEnchantment = enchantment.getKey().toString().substring(10).toLowerCase().replace("_", "");
        if(debug) System.out.println("FinalEnchant: "+finalEnchantment);

        String formula = "";
        double enchantmentValue = 0.0;

        switch (procedure){
            case SELL:
                formula = ConfigReader.getConfig().getString("formulas.sell.enchantment-formula");
                enchantmentValue = ConfigReader.getPotentialConfig().getDouble("enchantments."+finalEnchantment+".money");
                break;
            case EXP:
                formula = ConfigReader.getConfig().getString("formulas.exp.enchantment-formula");
                enchantmentValue = ConfigReader.getPotentialConfig().getDouble("enchantments."+finalEnchantment+".exp");
                break;
        }

        if(ConfigReader.getPotentialConfig().getBoolean("enchantments."+finalEnchantment+".negative")) enchantmentValue = -(enchantmentValue);

        String finalFormula = formula.replace("<var1>", String.valueOf(enchantmentValue)).replace("<var2>", String.valueOf(level));
        if(debug) System.out.println(finalFormula);
        Expression e = new ExpressionBuilder(finalFormula).build();
        return e.evaluate();

    }


}
