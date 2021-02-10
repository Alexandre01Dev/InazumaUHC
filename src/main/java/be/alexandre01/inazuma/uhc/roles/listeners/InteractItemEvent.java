package be.alexandre01.inazuma.uhc.roles.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractItemEvent implements Listener {
    InazumaUHC i;
    public InteractItemEvent(){
        i = InazumaUHC.get;
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(i.rm.containsRole(player.getUniqueId())){
            Action action = event.getAction();
            RoleItem roleItem = i.rm.getRole(player.getUniqueId()).getRoleItems().get(event.getItem());
            if(roleItem != null){
                if(roleItem.getItemStack().equals(event.getItem())){
                    if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
                        if(roleItem.getRightClick() != null){
                            roleItem.getRightClick().a(player);
                        }
                        return;
                    }

                    if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
                        if(roleItem.getLeftClick() != null){
                            roleItem.getLeftClick().a(player);
                        }
                        return;
                    }
                }
                }
        }

    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = event.getPlayer().getItemInHand();
        Entity e = event.getRightClicked();
        if(e instanceof Player){
            Player rightClick = (Player) e;
            if(i.rm.containsRole(player.getUniqueId())){
                RoleItem roleItem = i.rm.getRole(player.getUniqueId()).getRoleItems().get(itemStack);
                if(roleItem != null){
                    if(roleItem.getItemStack().equals(itemStack)){
                        if(roleItem.getRightClickOnPlayer() != null){
                            roleItem.getRightClickOnPlayer().a(player,rightClick);
                        }
                    }
                }
        }
    }
    }
}
