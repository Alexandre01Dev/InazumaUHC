package be.alexandre01.inazuma.uhc.managers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.utils.InventoryContainer;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.util.*;

public class RejoinManager implements Listener {
    private HashMap<UUID, InventoryContainer> playerInventory = new HashMap<>();

    private HashMap<Block, UUID> chests = new HashMap<>();
    private HashMap<UUID,Block> playerChest = new HashMap<>();
    private HashMap<Block,Inventory> chestInv = new HashMap<>();
    private HashMap<UUID, DateBuilderTimer> times = new HashMap<>();

    public void onDisconnect(Player p){
        playerInventory.put(p.getUniqueId(),new InventoryContainer(p.getInventory()));
        Block b = p.getLocation().getBlock();
        b.setType(Material.CHEST);
        chests.put(b,p.getUniqueId());
        playerChest.put(p.getUniqueId(),b);
        
        Inventory inventory = Bukkit.createInventory(null, 9*4);
        Role r = InazumaUHC.get.rm.getRole(p.getUniqueId());
        for(ItemStack i : p.getInventory().getContents()){
            if(i != null){
                if(r!= null){
                    if(r.getRoleItems().containsKey(i)){
                        continue;
                    }
                }
                inventory.addItem(i);
            }

        }
        for(ItemStack a : p.getInventory().getArmorContents()){
            if(a != null){
                inventory.addItem(a);
            }
        }

        chestInv.put(b,inventory);
        times.put(p.getUniqueId(),new DateBuilderTimer(1000*20).loadComplexDate());
        System.out.println("times put "+ p.getName());
    }

    public boolean isValidPlayer(Player player){
        System.out.println("TIMES >> "+times.containsKey(player.getUniqueId())+" "+ player.getName());
        if(times.containsKey(player.getUniqueId())){
            if(times.get(player.getUniqueId()).getDate().getTime() > 0){
                return true;
            }
        }
       return false;
    }
    public void onKilled(Player p){
        if(!Role.isDistributed){
            Block b = p.getLocation().getBlock();
            b.setType(Material.CHEST);
            chests.put(b,p.getUniqueId());
            playerChest.put(p.getUniqueId(),b);
            playerInventory.put(p.getUniqueId(),new InventoryContainer(p.getInventory()));
            times.put(p.getUniqueId(),new DateBuilderTimer(1000*60*5).loadComplexDate());

            Inventory inventory = Bukkit.createInventory(null, 9*4);
            for(ItemStack i : p.getInventory().getContents()){
                inventory.addItem(i);
            }
            for(ItemStack a : p.getInventory().getArmorContents()){
                inventory.addItem(a);
            }

            chestInv.put(b,inventory);
        }
    }
    public void revivePlayer(Player p){
        playerInventory.get(p.getUniqueId()).restitutionToPlayer(p);

        Block b = playerChest.get(p.getUniqueId());

        
        playerInventory.remove(p.getUniqueId());



        times.remove(p.getUniqueId());
        if(b != null){
            b.setType(Material.AIR);
            chests.remove(b);
            chestInv.remove(b);
        }

        playerChest.remove(p.getUniqueId());

        Role role = InazumaUHC.get.rm.getRole(p.getUniqueId());
        if(role == null){
            InazumaUHC.get.rm.addRole(p.getUniqueId());
        }else {
            role.getPlayers().add(p);

            if(role.getLoad() != null){
                role.getLoad().a();
            }

            if(!role.listeners.isEmpty()){
                role.deployListeners();
            }

            if(!role.getCommands().isEmpty()){
                role.loadCommands();
            }
        }
    }
    public void rejoinDisconnectedPlayer(Player p){

    }

    @EventHandler
    public void onChestClick(PlayerInteractEvent event){
        if(chests.containsKey(event.getClickedBlock())){
            DateBuilderTimer d = times.get(chests.get(event.getClickedBlock()));
            d.loadComplexDate();
            if(d.getDate().getTime() <= 0){
                event.getPlayer().openInventory(chestInv.get(event.getClickedBlock()));
                event.setCancelled(true);
                return;
            }
            event.getPlayer().sendMessage(Preset.instance.p.prefixName()+" §cCe coffre est bloqué pendant "+ d.getLongBuild());
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onInstantKill(PlayerInstantDeathEvent event){
        Player killed = event.getPlayer();
        Player killer = event.getKiller();
        if(killer != null){
            //TON CODE
        }
    }
}
