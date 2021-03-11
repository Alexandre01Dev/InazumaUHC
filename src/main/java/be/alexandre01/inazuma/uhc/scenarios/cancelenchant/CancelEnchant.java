package be.alexandre01.inazuma.uhc.scenarios.cancelenchant;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;

public class CancelEnchant extends Scenario implements Listener {
    public CancelEnchant() {
        super("CancelEnchant", "Désactive les enchantements de feu et de knockback");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.ENCHANTED_BOOK).setDurability((short) 2);
        setItemStack(itemBuilder.toItemStack());
    }
    @EventHandler
    public void onEnchanting(PrepareItemEnchantEvent event){
        Player player = event.getEnchanter();
        ItemStack item = event.getItem();
        if (item.containsEnchantment(Enchantment.FIRE_ASPECT)){
            item.removeEnchantment(Enchantment.FIRE_ASPECT);
            player.sendMessage(Preset.instance.p.prefixName()+"L'enchantement Fire Aspect a était retiré de votre item.");
        }
        if (item.containsEnchantment(Enchantment.KNOCKBACK)){
            item.removeEnchantment(Enchantment.KNOCKBACK);
            player.sendMessage(Preset.instance.p.prefixName()+"L'enchantement Knockback a était retiré de votre item.");
        }
        if (item.containsEnchantment(Enchantment.ARROW_KNOCKBACK)){
            item.removeEnchantment(Enchantment.ARROW_KNOCKBACK);
            player.sendMessage(Preset.instance.p.prefixName()+"L'enchantement Punch a était retiré de votre item.");
        }
        if (item.containsEnchantment(Enchantment.ARROW_FIRE)){
            item.removeEnchantment(Enchantment.ARROW_FIRE);
            player.sendMessage(Preset.instance.p.prefixName()+"L'enchantement Flame a était retiré de votre item.");
        }
    }

}
