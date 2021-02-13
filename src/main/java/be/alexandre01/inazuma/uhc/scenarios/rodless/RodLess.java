package be.alexandre01.inazuma.uhc.scenarios.rodless;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RodLess extends Scenario implements Listener {
    public RodLess() {
        super("RodLess", "Empêche toute utilisation de la Rod");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.FISHING_ROD);
        setItemStack(itemBuilder.toItemStack());
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        Player p = event.getPlayer ();
        if (event.getItem().getType () == Material.FISHING_ROD) {
            event.setCancelled(true);
            p.sendMessage( Preset.instance.p.prefixName() +" §7Rodless est activé, vous ne pouvez pas utiliser cette item.");
        }

    }

    @EventHandler
    public void onCraftingEvent(CraftItemEvent event){
        ItemStack i = event.getCurrentItem();
        if (i.getType().equals(Material.FISHING_ROD)){
            event.setCancelled(true);
            event.getWhoClicked().sendMessage( Preset.instance.p.prefixName() +" §7Rodless est activé, vous ne pouvez pas craft cette item.");
        }

    }

}
