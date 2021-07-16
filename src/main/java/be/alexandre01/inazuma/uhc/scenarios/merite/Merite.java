package be.alexandre01.inazuma.uhc.scenarios.merite;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Merite extends Scenario implements Listener {
    public Merite() {
        super("Merite", "Améliore le stuff");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.BLAZE_POWDER);
        setItemStack(itemBuilder.toItemStack());
    }

    @EventHandler
    public void onCraftingEvent(CraftItemEvent event){
        ItemStack i = event.getCurrentItem();
        if (i.getType().equals(Material.ANVIL)){
            event.setCancelled(true);
            event.getWhoClicked().sendMessage( Preset.instance.p.prefixName() +" §7UHC Merite est activé, vous ne pouvez pas craft cette item.");
        }
        if (i.getType().equals(Material.ENCHANTMENT_TABLE)){
            event.setCancelled(true);
            event.getWhoClicked().sendMessage( Preset.instance.p.prefixName() +" §7UHC Merite est activé, vous ne pouvez pas craft cette item.");
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        

    }
}
