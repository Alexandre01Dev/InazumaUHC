package be.alexandre01.inazuma.uhc.spectators;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.handler.GlobalMoveHandler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SpectatorManager {
    private HashMap<Player,SpectatorPlayer> players;
    private HashMap<Player,Integer> warnings = new HashMap<>();
    private HashMap<Player, Instant> time = new HashMap<>();
    public SpectatorManager(){
        players = new HashMap<>();
        InazumaUHC.get.lm.addListener(new SpectatorListeners());

        InazumaUHC.get.getGlobalMoveHandler().addMoveHandler(new GlobalMoveHandler.MoveHandler() {
            @Override
            public void onMove(Player player,Location location) {
                final double worldBorder = player.getWorld().getWorldBorder().getSize()/2;
                if(location.getBlockX() >  worldBorder || location.getBlockY() > worldBorder || location.getBlockZ() > worldBorder){
                    if(!warnings.containsKey(player)){
                        warnings.put(player,0);
                        time.put(player,Instant.now());
                    }

                    int i = warnings.get(player);
                    Instant t = time.get(player);
                    if(i > 5){
                        if(Duration.between(t,Instant.now()).toMillis() <= 5000L){
                            player.teleport(player.getWorld().getSpawnLocation());
                            player.sendMessage("§cAuriez vous l'obligence d'arrêter d'essayer de partir de la bordure. Merci bien.");
                        }else{
                            warnings.put(player,0);
                            time.put(player,Instant.now());
                        }
                    }
                }
            }
        });
    }

    public void addPlayer(Player player){
        SpectatorPlayer.builder().player(player).build();
        players.put(player,new SpectatorPlayer(player));
    }

    public void remPlayer(Player player){
        players.remove(player);
    }

    public SpectatorPlayer getPlayer(Player player){
        return players.get(player);
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players.keySet());
    }
    public ArrayList<SpectatorPlayer> getSpectatorPlayers() {
        return new ArrayList<>(players.values());
    }
}
