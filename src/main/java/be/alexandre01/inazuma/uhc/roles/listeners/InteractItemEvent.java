package be.alexandre01.inazuma.uhc.roles.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.roles.RoleItemTargetEvent;
import be.alexandre01.inazuma.uhc.custom_events.roles.RoleItemUseEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import org.bukkit.Bukkit;
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
        if(event.getItem() == null)
            return;
        if(i.rm.containsRole(player.getUniqueId())){
            Action action = event.getAction();
            ItemStack itemStack = event.getItem();
            if(itemStack == null)
                return;
            if(itemStack.getItemMeta().getDisplayName() == null)
                return;
            RoleItem roleItem = i.rm.getRole(player.getUniqueId()).getRoleItems().get(itemStack.getItemMeta().getDisplayName());
            if(roleItem != null){
                    if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)){
                        if(roleItem.getRightClick() != null){
                            if((roleItem.getVerificationOnRightClick() != null)){
                                if(!roleItem.getVerificationOnRightClick().verification(player))
                                    return;
                            }
                                RoleItemUseEvent roleItemUseEvent = new RoleItemUseEvent(player, i.rm.getRole(player), roleItem);
                                Bukkit.getPluginManager().callEvent(roleItemUseEvent);
                                if(roleItemUseEvent.isCancelled())
                                    return;

                                roleItem.getRightClick().execute(player);
                            }

                        if(roleItem.getRightClickOnPlayerFarTuple() != null){


                            Entity entity = PlayerUtils.getNearestPlayerInSight(player,roleItem.getRightClickOnPlayerFarTuple().a());
                            if(entity == null){
                                player.sendMessage(Preset.instance.p.prefixName()+" Vous n'avez séléctionné aucun joueur.");
                                return;
                            }


                            if(!(entity instanceof Player)){
                                player.sendMessage(Preset.instance.p.prefixName()+" Vous n'avez séléctionné aucun joueur.");
                                return;
                            }


                            Player target = (Player) entity;
                                if((roleItem.getVerificationOnRightClickOnPlayer() != null)){
                                    if(!roleItem.getVerificationOnRightClickOnPlayer().verification(player, target))
                                        return;
                                }

                                RoleItemTargetEvent roleItemTargetEvent = new RoleItemTargetEvent(player,target, i.rm.getRole(player), roleItem);
                                Bukkit.getPluginManager().callEvent(roleItemTargetEvent);
                                if(roleItemTargetEvent.isCancelled())
                                    return;
                                roleItem.getRightClickOnPlayerFarTuple().b().execute(player,target);
                            }
                            if(!roleItem.isPlaceableItem()){
                                if(roleItem.getPlaceBlock() != null){
                                    if((roleItem.getVerificationOnPlaceBlock() != null)){
                                        if(!roleItem.getVerificationOnPlaceBlock().verification(player))
                                            return;
                                    }
                                    RoleItemUseEvent roleItemUseEvent = new RoleItemUseEvent(player, i.rm.getRole(player), roleItem);
                                    Bukkit.getPluginManager().callEvent(roleItemUseEvent);
                                    if(roleItemUseEvent.isCancelled())
                                        return;
                                    roleItem.getPlaceBlock().execute(player,event.getClickedBlock().getRelative(event.getBlockFace()));

                                }
                                event.setCancelled(true);
                            }
                            return;
                    }

                    if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)){
                        if(roleItem.getLeftClick() != null){
                            if((roleItem.getVerificationOnLeftClick() != null)){
                                if(!roleItem.getVerificationOnLeftClick().verification(player))
                                    return;
                            }

                                RoleItemUseEvent roleItemUseEvent = new RoleItemUseEvent(player, i.rm.getRole(player), roleItem);
                                Bukkit.getPluginManager().callEvent(roleItemUseEvent);
                                if(roleItemUseEvent.isCancelled())
                                    return;
                                roleItem.getLeftClick().execute(player);
                        }
                        return;
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
                if(itemStack == null)
                    return;
                if(itemStack.getItemMeta() == null){
                    return;
                }
                if(itemStack.getItemMeta().getDisplayName() == null)
                    return;
                RoleItem roleItem = i.rm.getRole(player.getUniqueId()).getRoleItems().get(itemStack.getItemMeta().getDisplayName());
                if(roleItem != null){
                    if(roleItem.getItemStack().getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())){
                        if(roleItem.getRightClickOnPlayer() != null){
                            if(roleItem.getVerificationOnRightClickOnPlayer().verification(player,rightClick)){
                            RoleItemTargetEvent roleItemTargetEvent = new RoleItemTargetEvent(player,rightClick, i.rm.getRole(player), roleItem);
                            Bukkit.getPluginManager().callEvent(roleItemTargetEvent);
                            if(roleItemTargetEvent.isCancelled())
                                return;


                            roleItem.getRightClickOnPlayer().execute(player,rightClick);
                        }
                        }
                    }
                }
        }
    }
    }


}
