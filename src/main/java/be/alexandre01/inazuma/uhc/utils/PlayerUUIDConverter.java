package be.alexandre01.inazuma.uhc.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerUUIDConverter implements Listener {
    private static final HashMap<UUID,String> names = new HashMap<>();
    public static String getPlayerName(UUID uuid){
        return names.get(uuid);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        names.put(player.getUniqueId(),player.getName());
    }
}
