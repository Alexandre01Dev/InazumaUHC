package be.alexandre01.inazuma.uhc.listeners.game;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
        if(!(event.getEntity() instanceof ArmorStand)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;

        if(event.getClickedBlock() != null){
            Material m = event.getClickedBlock().getType();
            if(m.equals(Material.ACACIA_DOOR)){
                return;
            }

            if(m.equals(Material.DARK_OAK_DOOR)){
                return;
            }

            if(m.equals(Material.BIRCH_DOOR)){
                return;
            }

            if(m.equals(Material.DARK_OAK_DOOR_ITEM)){
                return;
            }

            if(m.equals(Material.JUNGLE_DOOR)){
                return;
            }

            if(m.equals(Material.SPRUCE_DOOR)){
                return;
            }

            if(m.equals(Material.WOOD_DOOR)){
                return;
            }

            if(m.equals(Material.WOODEN_DOOR)){
                return;
            }

        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event){
        event.setCancelled(true);
    }
}
