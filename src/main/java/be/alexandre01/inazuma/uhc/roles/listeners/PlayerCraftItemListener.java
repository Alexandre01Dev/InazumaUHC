package be.alexandre01.inazuma.uhc.roles.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class PlayerCraftItemListener implements Listener {
    private InazumaUHC i;
    public PlayerCraftItemListener(){
        this.i = InazumaUHC.get;
    }
    @EventHandler
    public void onCraft(CraftItemEvent event){
        Player player = (Player) event.getWhoClicked();
        if(i.rm.containsRole(player)){
            Role role = i.rm.getRole(player);
            if(!role.getRoleCrafts().containsKey(event.getRecipe())){
                event.setCancelled(true);
                player.sendMessage("§cVous n'avez pas accès à ce craft.");
            }
        }
    }
}
