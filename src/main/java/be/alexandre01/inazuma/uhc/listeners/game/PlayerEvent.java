package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Options;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;
import java.util.logging.Handler;

public class PlayerEvent implements Listener {
    public ArrayList<Player> players = null;
    public PlayerEvent(){

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        InazumaUHC inazumaUHC = InazumaUHC.get;
        Player player = event.getPlayer();
        player.setFlySpeed(1);
        player.setFlying(false);
        World world = Bukkit.getWorld(Options.to("worldsTemp").get("defaultUUID").getString());
        System.out.println(Options.to("worldsTemp").get("defaultUUID").getString());
        player.teleport(world.getSpawnLocation());
        player.getInventory().clear();
        player.updateInventory();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setTotalExperience(0);
        inazumaUHC.getScoreboardManager().onLogin(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        InazumaUHC inazumaUHC = InazumaUHC.get;
        inazumaUHC.getScoreboardManager().onLogout(event.getPlayer());
    }

    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event){
        if(!GameState.get().contains(State.PLAYING)){
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onFly(PlayerToggleFlightEvent event){
        if(GameState.get().contains(State.STARTING)){
            Player player = event.getPlayer();
            Location loc = InazumaUHC.get.teamManager.getTeam(player).getLocation();
            Location pLoc = player.getLocation();
            loc.setPitch(pLoc.getPitch());
            loc.setYaw(pLoc.getYaw());
            player.teleport(loc);
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(GameState.get().contains(State.WAITING)){
            event.setCancelled(true);
        }
        if(GameState.get().contains(State.STARTING)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFirstDamage(EntityDamageEvent event){
        if(GameState.get().contains(State.PLAYING)){
            if(players == null){
                players = new ArrayList<>(Bukkit.getOnlinePlayers());
            }
            if(players.isEmpty()){
                return;
            }
            if(event.getEntity() instanceof Player){
                Player player = (Player) event.getEntity();
                if(players.contains(player)){
                    if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                        event.setCancelled(true);
                        players.remove(player);
                    }

                }
            }

        }
    }
}
