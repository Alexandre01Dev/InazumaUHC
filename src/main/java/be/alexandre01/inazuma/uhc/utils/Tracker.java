package be.alexandre01.inazuma.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tracker {
    private static Tracker tracker;
    private HashMap<UUID /*TRACKER*/, UUID /*TRACKED*/> trackerMap = new HashMap<>();
    private HashMap<UUID, ItemStack> compass = new HashMap<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private void setItemToPlayer(Player player){
        ItemStack it = new ItemStack(Material.COMPASS);
        ItemMeta im = it.getItemMeta();

        im.addEnchant(Enchantment.DURABILITY,1,true);
        im.setDisplayName("§7Le trackeur");
        it.setItemMeta(im);

        player.getInventory().addItem(it);
        player.updateInventory();
    }
    public void setTargetToPlayer(Player player, Player tracked){
        trackerMap.put(player.getUniqueId(),tracked.getUniqueId());
        setItemToPlayer(player);
    }
    public void removeTargetToPlayer(Player player){
        trackerMap.remove(player.getUniqueId());
    }
    public static Tracker getOrCreate(){
        return tracker;
    }
    public static void initialize(){
        Tracker t = tracker = new Tracker();

        t.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for(UUID uuid : t.trackerMap.keySet()){
                    Player player = Bukkit.getPlayer(uuid);
                    if(player == null || !player.isOnline())
                        continue;

                    Player target = Bukkit.getPlayer(t.trackerMap.get(uuid));

                    if(target == null || !target.isOnline())
                        continue;

                    player.setCompassTarget(target.getLocation());
                }
            }
        },0,1, TimeUnit.SECONDS);
    }
}
