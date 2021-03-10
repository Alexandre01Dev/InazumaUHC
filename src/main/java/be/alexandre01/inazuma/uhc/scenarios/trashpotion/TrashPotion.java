package be.alexandre01.inazuma.uhc.scenarios.trashpotion;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class TrashPotion extends Scenario implements Listener {
    public TrashPotion() {
        super("TrashPotion", "Clear une potion une fois bu.");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.GLASS_BOTTLE);
        setItemStack(itemBuilder.toItemStack());
    }
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        final Player player = e.getPlayer();

        if (e.getItem().getTypeId() == 373) {
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(InazumaUHC.get, new Runnable() {
                public void run() {
                    player.setItemInHand(new ItemStack(Material.AIR));
                }
            }, 1L);
        }
        if (e.getItem().getTypeId() == 335) {
            e.setCancelled(true);
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(InazumaUHC.get, new Runnable() {
                public void run() {
                    player.setItemInHand(new ItemStack(Material.BUCKET));
                    player.getWorld().spigot().strikeLightningEffect(player.getLocation(), true);
                    player.setFireTicks(60);
                    player.setHealth(player.getHealth()-2);
                    Bukkit.broadcastMessage(Preset.instance.p.prefixName() + "§7 Félicitation à §c"+ player.getName() + " §7qui a essayé de tricher, mais en vain...");
                    player.sendMessage(Preset.instance.p.prefixName()+ "§7Viens discord juste après ta mort sous peine d'une §csanction§7.");
                }
            }, 1L);
        }
    }
}