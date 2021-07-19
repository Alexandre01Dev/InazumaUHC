package be.alexandre01.inazuma.uhc.roles.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.custom_events.roles.RoleItemUseEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class DropRoleItemEvent implements Listener {
    private final InazumaUHC i;
    private HashMap<Integer,RoleItem> roleItemDropped = new HashMap<>();

    public DropRoleItemEvent(){
       i = InazumaUHC.get;
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemDrop().getItemStack();

        if(i.rm.containsRole(player)){
            Role role = i.rm.getRole(player);
            HashMap<String,RoleItem> h = role.getRoleItems();
            String displayName = itemStack.getItemMeta().getDisplayName();
            if(h.containsKey(displayName)){
                RoleItem roleItem = h.get(displayName);
                if(roleItem.isDroppableItem()){
                    ItemStack it = player.getItemInHand();
                    roleItemDropped.put(event.getItemDrop().getEntityId(),roleItem);
                    if(it != null)
                        return;
                    roleItem.getPlayersHaveItem().remove(player);
                    h.remove(displayName);
                    return;
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem().getItemStack();

        if(i.rm.containsRole(player)){
            Role role = i.rm.getRole(player);
            HashMap<String,RoleItem> h = role.getRoleItems();
            String displayName = itemStack.getItemMeta().getDisplayName();
            int id = event.getItem().getEntityId();
            if(!roleItemDropped.containsKey(id))
                return;

            RoleItem roleItem = roleItemDropped.get(id);

                if(roleItem.getRolesToAccess().contains(role.getClass())){
                    roleItem.getPlayersHaveItem().add(player);
                    h.put(displayName,roleItem);
                    return;
                }
                event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemInHand();

        if(i.rm.containsRole(player)){
            if(i.rm.getRole(player).getRoleItems().containsKey(itemStack.getItemMeta().getDisplayName())){
                RoleItem roleItem = i.rm.getRole(player).getRoleItems().get(itemStack.getItemMeta().getDisplayName());
                    event.setCancelled(true);

                if(roleItem.getPlaceBlock() != null){
                    if((roleItem.getVerificationOnPlaceBlock() != null)){
                        if(!roleItem.getVerificationOnPlaceBlock().verification(player))
                            return;
                    }
                        RoleItemUseEvent roleItemUseEvent = new RoleItemUseEvent(player, i.rm.getRole(player), roleItem);
                        Bukkit.getPluginManager().callEvent(roleItemUseEvent);
                        if(roleItemUseEvent.isCancelled())
                            return;


                        roleItem.getPlaceBlock().execute(player,event.getBlockAgainst());
                    }

        }
    }
    }


}
