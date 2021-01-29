package be.alexandre01.inazuma.uhc.listeners.game;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectionEvent implements Listener {
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID)){
            Entity entity = event.getEntity();
            entity.teleport(entity.getWorld().getSpawnLocation());
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        event.setCancelled(true);
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        event.setCancelled(true);
    }
}
