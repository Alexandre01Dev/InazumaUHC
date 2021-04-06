package be.alexandre01.inazuma.uhc.managers.damage;

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

public class NoFallDamager implements Listener {

    private final HashMap<Player, DateBuilderTimer> players;
    private final ArrayList<Player> firstFalling = new ArrayList<>();
    public NoFallDamager() {
        this.players = new HashMap<>();
    }

    public void addPlayer(Player player,long l){
        this.players.put(player, new DateBuilderTimer(l));
        this.firstFalling.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamage(EntityDamageEvent event){
        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){

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
    }

}
