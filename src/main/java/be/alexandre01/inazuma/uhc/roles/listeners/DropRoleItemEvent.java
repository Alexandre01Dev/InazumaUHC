package be.alexandre01.inazuma.uhc.roles.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DropRoleItemEvent implements Listener {
    private final InazumaUHC i;

    public DropRoleItemEvent(){
       i = InazumaUHC.get;
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemDrop().getItemStack();

        if(i.rm.containsRole(player)){
            if(i.rm.getRole(player).getRoleItems().containsKey(itemStack)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemInHand();

        if(i.rm.containsRole(player)){
            if(i.rm.getRole(player).getRoleItems().containsKey(itemStack)){
                event.setCancelled(true);
            }
        }
    }


}
