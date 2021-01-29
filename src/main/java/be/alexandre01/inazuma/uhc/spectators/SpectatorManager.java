package be.alexandre01.inazuma.uhc.spectators;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class SpectatorManager {
    private HashMap<Player,SpectatorPlayer> players;

    public SpectatorManager(){
        players = new HashMap<>();
        InazumaUHC.get.lm.addListener(new SpectatorListeners());
    }

    public void addPlayer(Player player){
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
