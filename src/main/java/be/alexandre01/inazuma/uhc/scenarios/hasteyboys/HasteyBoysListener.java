package be.alexandre01.inazuma.uhc.scenarios.hasteyboys;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class HasteyBoysListener implements Listener {
    @EventHandler
    public void onCraftTable(CraftItemEvent event){
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null)
            return;
        if (isUpgradeItem(event.getCurrentItem().getType()))
            event.setCurrentItem(enchantAndUpgrade(event.getCurrentItem()));
    }

    public Boolean isUpgradeItem(Material type) {
        return (type == Material.WOOD_AXE || type == Material.WOOD_PICKAXE || type == Material.WOOD_SPADE || type == Material.GOLD_AXE || type == Material.GOLD_PICKAXE || type == Material.GOLD_SPADE || type == Material.STONE_AXE || type == Material.STONE_PICKAXE || type == Material.STONE_SPADE || type == Material.IRON_AXE || type == Material.IRON_PICKAXE || type == Material.IRON_SPADE || type == Material.DIAMOND_AXE || type == Material.DIAMOND_PICKAXE || type == Material.DIAMOND_SPADE);
    }

    public ItemStack enchantAndUpgrade(ItemStack item) {
            item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        return item;
    }
}
