package be.alexandre01.inazuma.uhc.managers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.spectators.BukkitTeamInitializer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.utils.InventoryContainer;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

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
        
        Inventory inventory = Bukkit.createInventory(null, 9*5);
        Role r = InazumaUHC.get.rm.getRole(p.getUniqueId());

        for(ItemStack i : p.getInventory().getContents()){
            if(i != null){
                if(r!= null){
                    if(r.getRoleItems().containsKey(i.getItemMeta().getDisplayName())){
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
        times.put(p.getUniqueId(),new DateBuilderTimer(1000*60*10).loadComplexDate());
        System.out.println("times put "+ p.getName());
    }

    public boolean isValidPlayer(Player player){
        System.out.println("TIMES >> "+times.containsKey(player.getUniqueId())+" "+ player.getName());
        if(times.containsKey(player.getUniqueId())){
            times.get(player.getUniqueId()).loadDate();
            if(times.get(player.getUniqueId()).getDate().getTime() > 0){
                return true;
            }
        }
       return false;
    }
    public void onKilled(Player p){
            Block b = p.getLocation().getBlock();
            b.setType(Material.CHEST);
            chests.put(b,p.getUniqueId());
            playerChest.put(p.getUniqueId(),b);
            playerInventory.put(p.getUniqueId(),new InventoryContainer(p.getInventory()));
            times.put(p.getUniqueId(),new DateBuilderTimer(1000*60*5).loadComplexDate());

            Inventory inventory = Bukkit.createInventory(null, 9*4);
            for(ItemStack i : p.getInventory().getContents()){
                if(i != null){
                    inventory.addItem(i);
                }
            }
            for(ItemStack a : p.getInventory().getArmorContents()){
                if(a != null){
                    inventory.addItem(a);
                }
            }

            chestInv.put(b,inventory);
    }
    public void teleportRandom(Player player){
        Random rand1 = new Random();
        int size = Preset.instance.p.getBorderSize(World.Environment.NORMAL);
        int x = rand1.nextInt(size - ((-size) + 1)) + (-size);
        Random rand2 = new Random();
        int z = rand2.nextInt(size - ((-size) + 1)) + (-size);

        World w = InazumaUHC.get.worldGen.defaultWorld;
        Location l = new Location(w,x,121.001,z);

        player.teleport(l);
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

        if(Role.isDistributed){
            Role role = InazumaUHC.get.rm.getRole(p.getUniqueId());
            if(role == null){
                Role nRole = InazumaUHC.get.rm.addRole(p.getUniqueId());
                nRole.spoilRole(p);
                nRole.giveItem(p);
            }else {
                role.getPlayers().add(p);

                if(role.getLoad() != null){
                    role.getLoad().a(p);
                }

                if(!role.listeners.isEmpty()){
                    if(!role.isListenerDeployed){
                        role.deployListeners();
                    }

                }

                if(!role.getCommands().isEmpty()){
                    role.loadCommands();
                }
            }
        }

        if(!InazumaUHC.get.getRemainingPlayers().contains(p)){
            InazumaUHC.get.getRemainingPlayers().add(p);
        }
        p.setGameMode(GameMode.SURVIVAL);

        if(InazumaUHC.get.spectatorManager.getPlayers().contains(p)){
            InazumaUHC.get.spectatorManager.remPlayer(p);
            InazumaUHC.get.teamManager.getTeam(p).setBukkitTeam();
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.setAllowFlight(false);
            p.setFlying(false);
        }

        for(Player player : Bukkit.getOnlinePlayers()){
            if(p != player && !InazumaUHC.get.spectatorManager.getPlayers().contains(p)){
                player.showPlayer(p);
            }
        }


    }
    public void rejoinKilledPlayer(Player p){

    }

    @EventHandler
    public void onChestClick(PlayerInteractEvent event){
        if(InazumaUHC.get.spectatorManager.getPlayers().contains(event.getPlayer()))
            return;
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

}
