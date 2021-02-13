package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.ArrayList;

public class FreezePlayerListener implements Listener {
    private ArrayList<Player> p;

    public FreezePlayerListener(){
        p = new ArrayList<>();
    }

    public ArrayList<Player> getP() {
        return p;
    }

    public void setP(ArrayList<Player> p) {
        this.p = p;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(p.contains(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageOther(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getEntity();
            if(p.contains(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(p.contains(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        if(p.contains(player)){
            event.setCancelled(true);
        }
    }
}
