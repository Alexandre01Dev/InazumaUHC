package be.alexandre01.inazuma.uhc.roles.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInstantDeath implements Listener {
    private final InazumaUHC i;
    public PlayerInstantDeath(){
        i = InazumaUHC.get;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill(PlayerInstantDeathEvent event){
        Player player = event.getPlayer();
        List<ItemStack> d = event.getDrops();
        if(i.rm.containsRole(player)){
            for(RoleItem r : i.rm.getRole(player).getRoleItems().values()){
                if(d.contains(r.getItemStack())){
                    d.remove(r.getItemStack());
                }
            }
        }
        Role role = i.rm.getRole(player.getUniqueId());
        if(role != null){
            Bukkit.broadcastMessage("§r§l§8»§8§m-----------------------------------------------§l§8«");
            Bukkit.broadcastMessage("§e"+player.getName()+"§7 vient de §cmourir§7 à l'instant, c'était §a"+ role.getName());
            Bukkit.broadcastMessage("§r§l§8»§8§m-----------------------------------------------§l§8«");
        }
    }
}
