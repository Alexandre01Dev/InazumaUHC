package be.alexandre01.inazuma.uhc.listeners.host;

import be.alexandre01.inazuma.uhc.host.Host;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickInventory implements Listener {
    private Host host;

    public ClickInventory(){
        this.host = Host.getInstance();
    }
    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();

        if(host.getInventories().containsKey(player)){

        }
    }
}
