package be.alexandre01.inazuma.uhc.scenarios.cutclean.listeners;


import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityKill implements Listener {
    @EventHandler
    public void onEntityKill(EntityDeathEvent event){

        Entity entity = event.getEntity();
        if(entity.getLastDamageCause() != null){
            if(entity.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                Player player = event.getEntity().getKiller();
                if(player != null){
                    player.giveExp(event.getDroppedExp());
                    event.setDroppedExp(0);
                }
            }
        }


        if(entity instanceof Pig){
            for(ItemStack i  : event.getDrops()){
                if(i.getType().equals(Material.PORK)){
                    i.setType(Material.GRILLED_PORK);
                }
            }
            return;
        }
        if(entity instanceof Rabbit){
            for(ItemStack i  : event.getDrops()){
                if(i.getType().equals(Material.RABBIT)){
                    i.setType(Material.COOKED_RABBIT);
                }
            }
            return;
        }
        if(entity instanceof Fish){
            for(ItemStack i  : event.getDrops()){
                if(i.getType().equals(Material.RAW_FISH)){
                    i.setType(Material.COOKED_FISH);
                }
            }
            return;
        }
        if(entity instanceof Sheep){
            for(ItemStack i  : event.getDrops()){
                if(i.getType().equals(Material.MUTTON)){
                    i.setType(Material.COOKED_MUTTON);
                }
            }
            return;
        }
        if(entity instanceof Cow){
            for(ItemStack i  : event.getDrops()){
                if(i.getType().equals(Material.RAW_BEEF)){
                    i.setType(Material.COOKED_BEEF);
                }
            }
            return;
        }
        if(entity instanceof Chicken){
            for(ItemStack i  : event.getDrops()){
                if(i.getType().equals(Material.RAW_CHICKEN)){
                    i.setType(Material.COOKED_CHICKEN);
                }
            }
        }
    }
}
