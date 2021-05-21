package be.alexandre01.inazuma.uhc.listeners.host;

import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInventory implements Listener {
    private Host host;

    public CloseInventory(){
        this.host = Host.getInstance();
    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(host.getHome().getInvs().containsKey(player)){
            FastInv inv = host.getHome().getInventory(player);
            if(inv.getInventory().equals(event.getInventory())){
                if(host.getWorkingPlaces().containsKey(player)){
                    WorkingPlace workingPlace = host.getWorkingPlaces().get(player);
                    workingPlace.rmvInstance(player);
                    host.getInstances().remove(player);
                    //SOUND
                    if(workingPlace.getSoundProperty() != null){
                        player.playSound(player.getLocation(),workingPlace.getSoundProperty().getSound(), workingPlace.getSoundProperty().getV1(), workingPlace.getSoundProperty().getV2());
                    }
                }
            }
        }
    }
}
