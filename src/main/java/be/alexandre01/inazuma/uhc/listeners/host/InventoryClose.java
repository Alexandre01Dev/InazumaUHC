package be.alexandre01.inazuma.uhc.listeners.host;

import be.alexandre01.inazuma.uhc.host.Host;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;

public class InventoryClose implements Listener {
    public Host host;
    public InventoryClose(Host host){
        this.host = host;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        HumanEntity player = event.getPlayer();
     /*   if(host.currentTGUI.containsKey(player.getUniqueId())){
            if(host.lastTGUI.containsKey(player.getUniqueId())){
                host.lastTGUI.remove(player.getUniqueId());
            }
            host.currentTGUI.remove(player.getUniqueId());
        }*/
    }
}
