package be.alexandre01.inazuma.uhc.presets.normal.listeners;

import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class JoinEvent implements Listener {
    private GameState gameState;
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(gameState == null){
            gameState = GameState.get();
        }
        Player player = event.getPlayer();


        if(!gameState.contains(State.WAITING) && !gameState.contains(State.PREPARING)){
            return;
        }
        event.setJoinMessage("§c"+player.getName()+"§e a rejoint la partie");
    }
}
