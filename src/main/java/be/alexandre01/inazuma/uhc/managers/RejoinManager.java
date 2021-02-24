package be.alexandre01.inazuma.uhc.managers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.InventoryContainer;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RejoinManager implements Listener {
    private HashMap<Player, InventoryContainer> playersDisconnect = new HashMap<>();
    private HashMap<Player, InventoryContainer> playersKilled = new HashMap<>();
    private HashMap<Block, Player> chests = new HashMap<>();

    public void onDisconnect(Player p){
        playersDisconnect.put(p,new InventoryContainer(p.getInventory()));
        Block b = p.getLocation().getBlock();
        b.setType(Material.CHEST);
        chests.put(b,p);
    }

    public void onKilled(Player p){
        if(!Role.isDistributed){
            playersKilled.put(p,new InventoryContainer(p.getInventory()));
        }
    }
    public void reviveKilledPlayer(Player p){

    }
    public void rejoinDisconnectedPlayer(Player p){

    }

    @EventHandler
    public void onChestClick(PlayerInteractEvent event){
        if(chests.containsKey(event.getClickedBlock())){

        }
    }
}
