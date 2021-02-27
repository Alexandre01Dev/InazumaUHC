package be.alexandre01.inazuma.uhc.spectators;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;

import java.util.ArrayList;

public class SpectatorListeners implements Listener {
    private ArrayList<Player> blockedInEntity;
    InazumaUHC i;
    public SpectatorListeners(){
         this.i = InazumaUHC.get;
         this.blockedInEntity = new ArrayList<>();
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event){
        if(i.spectatorManager.getPlayers().contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event){
    if(event.getDamager() instanceof Player){
        Player player = (Player) event.getDamager();
        if(i.spectatorManager.getPlayers().contains(player)){
            event.setCancelled(true);
        }
    }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(i.spectatorManager.getPlayers().contains(player)){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onFood(FoodLevelChangeEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(i.spectatorManager.getPlayers().contains(player)){
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent event){
        if(i.spectatorManager.getPlayers().contains(event.getPlayer())){
            event.setCancelled(true);
                Player spectator = event.getPlayer();
                spectator.setGameMode(GameMode.SPECTATOR);
                i.spectatorManager.getPlayer(spectator).setCamera(event.getRightClicked());
                blockedInEntity.add(spectator);
        }
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event){

        Player spectator = event.getPlayer();
        if(blockedInEntity.contains(spectator)){
            spectator.setAllowFlight(true);
            spectator.setFlying(true);
            i.spectatorManager.getPlayer(spectator).setCamera(spectator);
            spectator.setGameMode(GameMode.ADVENTURE);
            spectator.setAllowFlight(true);
            spectator.setFlying(true);
            blockedInEntity.remove(spectator);
        }

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(i.spectatorManager.getPlayers().contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        if(i.spectatorManager.getPlayers().contains(event.getPlayer())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event){
        if(event.getTarget() instanceof Player){
            Player target = (Player) event.getTarget();
            if(i.spectatorManager.getPlayers().contains(target)){
                event.setCancelled(true);
            }
        }

    }
}
