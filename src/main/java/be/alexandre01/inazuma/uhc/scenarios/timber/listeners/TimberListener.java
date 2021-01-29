package be.alexandre01.inazuma.uhc.scenarios.timber.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
        }


    }
}
