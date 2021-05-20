package be.alexandre01.inazuma.uhc.listeners.host;

import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.HostButton;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
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
        System.out.println("CLICK");
        Player player = (Player) event.getWhoClicked();
        if(host.getHome().getInvs().containsKey(player)){
            FastInv inv = host.getHome().getInventory(player);
            if(inv.getInventory().equals(event.getInventory())){
                if(host.getWorkingPlaces().containsKey(player)){
                    WorkingPlace workingPlace = host.getWorkingPlaces().get(player);
                    if(workingPlace.getHostsButtons().containsKey(event.getSlot())){
                        HostButton hostButton = workingPlace.getHostsButtons().get(event.getSlot());
                        //SOUND
                        if(hostButton.getSound() != null){
                            player.playSound(player.getLocation(),hostButton.getSound().getSound(),hostButton.getSound().getV1(), hostButton.getSound().getV2());
                        }

                        switch (hostButton.getType()){
                            case NONE:
                                break;
                            case DIRECT:
                                if(hostButton.getAction() != null)
                                    hostButton.getAction().onClick(player);
                            case OPTION:
                                break;
                            case REDIRECTION:
                                break;
                        }

                    }
        }

                event.setCancelled(true);
            }
        }
        //if(host.getInventories().containsKey(player)){

        //}
    }
}
