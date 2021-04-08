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
import java.util.Objects;
import java.util.stream.Collectors;

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
                    d.removeAll( d.stream()
                            .filter(itemStack -> itemStack != null)
                            .filter(itemStack -> itemStack.getItemMeta() != null)
                            .filter(itemStack -> itemStack.getItemMeta().getDisplayName() != null)
                            .filter(itemStack -> itemStack.getItemMeta().getDisplayName().equals(r.getItemStack().getItemMeta().getDisplayName()))
                            .collect(Collectors.toList()));
            }
        }
        Role role = i.rm.getRole(player.getUniqueId());
        if(role != null){
            Bukkit.broadcastMessage("§r§l§8»§8§m-----------------------------------------------§l§8«");
            if(role.getRoleCategory() != null){
                for(String msg : role.getRoleCategory().getDeathMessage()){
                    Bukkit.broadcastMessage(msg.replaceAll("%player%",player.getName()).replaceAll("%role%",role.getRoleCategory().getPrefixColor()+role.getName()));
                }
            }else {
                Bukkit.broadcastMessage("§e"+player.getName()+"§7 vient de §cmourir");
                Bukkit.broadcastMessage("§7 Son rôle était §f: " + role.getName());
            }
            Bukkit.broadcastMessage("§r§l§8»§8§m-----------------------------------------------§l§8«");

            if(!role.getPlayers().isEmpty()){
                role.removePlayer(player);
            }
            role.getEliminatedPlayers().add(player.getUniqueId());
            return;
        }
        Bukkit.broadcastMessage("§r§l§8»§8§m-----------------------------------------------§l§8«");
            Bukkit.broadcastMessage("§e"+player.getName()+"§7 vient de §cmourir§7.");
        Bukkit.broadcastMessage("§r§l§8»§8§m-----------------------------------------------§l§8«");


    }
}
