package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
            TitleUtils.sendActionBar(p,"§a"+player.getName()+"§e a rejoins la partie");
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
}
