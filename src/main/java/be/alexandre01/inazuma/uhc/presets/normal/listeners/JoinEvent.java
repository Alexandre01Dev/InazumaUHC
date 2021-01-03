package be.alexandre01.inazuma.uhc.presets.normal.listeners;

import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import spg.lgdev.Properties;
import spg.lgdev.iSpigot;

public class JoinEvent implements Listener {
    private GameState gameState;
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(gameState == null){
            gameState = GameState.get();
        }
        Player player = event.getPlayer();


        if(!gameState.contains(State.WAITING)){
            player.sendMessage("§cLa partie a déjà commencé, vous êtes un spéctateur de celle-ci.");
            player.setGameMode(GameMode.SPECTATOR);
            return;
        }
        event.setJoinMessage("§e"+player.getName()+" a rejoins la partie");
    }
}
