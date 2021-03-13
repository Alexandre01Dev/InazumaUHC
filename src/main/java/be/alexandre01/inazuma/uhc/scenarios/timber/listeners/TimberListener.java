package be.alexandre01.inazuma.uhc.scenarios.timber.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TimberListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        if(block.getType().equals(Material.LOG)|| block.getType().equals(Material.LOG_2)){
            Location upLoc = block.getLocation().clone();
            Location downLoc = block.getLocation().clone();
            while (upLoc.clone().add(0,1,0).getBlock().getType() == Material.LOG||upLoc.clone().add(0,1,0).getBlock().getType() == Material.LOG_2){
                upLoc.add(0,1,0).getBlock().breakNaturally();
            }
            while (downLoc.clone().add(0,-1,0).getBlock().getType() == Material.LOG||downLoc.clone().add(0,-1,0).getBlock().getType() == Material.LOG_2){
                downLoc.add(0,-1,0).getBlock().breakNaturally();
            }
            upLoc.add(0,-4,0);
            Bukkit.getScheduler().runTaskTimerAsynchronously(InazumaUHC.get, new BukkitRunnable() {
                int i = new Random().nextInt(2-1)+1;
                @Override
                public void run() {
                    i--;

                    int x = new Random().nextInt(-2+2)-2;
                    int z = new Random().nextInt(-2+2)-2;
                    Location clone = upLoc.clone().add(x,0,z);
                    clone.getWorld().dropItemNaturally(upLoc, new ItemStack(Material.APPLE));
                    if (i==0){
                        cancel();
                    }
                }
            }, 10,10);
        }
    }
}
