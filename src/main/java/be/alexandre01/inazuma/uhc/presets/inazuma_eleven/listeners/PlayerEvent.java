package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEvent implements Listener {
    private GameState gameState;
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(gameState == null){
            gameState = GameState.get();
        }
        Player player = event.getPlayer();


        if(gameState.contains(State.PLAYING) || gameState.contains(State.STOPPING)){
            event.setJoinMessage("");
            return;
        }

        Bukkit.getOnlinePlayers().forEach((p) ->{
            TitleUtils.sendActionBar(p,"§a"+player.getName()+"§e a rejoint la partie");
        });

        event.setJoinMessage("");
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if(gameState.contains(State.PLAYING) || gameState.contains(State.STOPPING)){
            event.setQuitMessage("");
            return;
        }
        if(GameState.get().contains(State.WAITING)||(GameState.get().contains(State.PREPARING))){
            Bukkit.getOnlinePlayers().forEach((p) ->{
                TitleUtils.sendActionBar(p,"§c"+event.getPlayer().getName()+"§e a quitté la partie");
            });
        }

    }

    @EventHandler
    public void onPlayerInstantDeath(PlayerInstantDeathEvent event){
        Player player = event.getPlayer();
        event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE));
        if(!Role.isDistributed){
            Bukkit.broadcastMessage(Preset.instance.p.prefixName()+" §c§l"+player.getName()+"§7 vient de mourir (PVE).");
            InazumaUHC.get.getRejoinManager().onKilled(player);
            event.getDrops().clear();
        }
    }
}
