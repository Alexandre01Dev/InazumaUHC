package be.alexandre01.inazuma.uhc.managers.damage;

import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class InvincibilityDamager implements Listener {

    private final HashMap<Player, DateBuilderTimer> players;

    public InvincibilityDamager() {
        this.players = new HashMap<>();
    }

    public void addPlayer(Player player,long l){
        this.players.put(player, new DateBuilderTimer(l));
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof Player){
            Player player = (Player) entity;
            if(!players.containsKey(player)){
                return;
            }
            DateBuilderTimer dateBuilderTimer = players.get(player);
            dateBuilderTimer.loadDate();
            if(dateBuilderTimer.getDate().getTime() > 0){
                event.setDamage(0);
                event.setCancelled(true);
                return;
            }
            players.remove(player);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
        Entity entity = event.getDamager();
        if(entity instanceof Player){
            Player player = (Player) entity;
            if(!players.containsKey(player)){
                return;
            }
            DateBuilderTimer dateBuilderTimer = players.get(player);
            dateBuilderTimer.loadDate();
            if(dateBuilderTimer.getDate().getTime() > 0){
                event.setDamage(0);
                event.setCancelled(true);
                return;
            }
            players.remove(player);
        }

    }

}
