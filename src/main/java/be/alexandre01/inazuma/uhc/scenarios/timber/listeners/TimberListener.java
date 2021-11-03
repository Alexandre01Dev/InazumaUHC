package be.alexandre01.inazuma.uhc.scenarios.timber.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class TimberListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
     
        if(block.getType().equals(Material.LOG)|| block.getType().equals(Material.LOG_2)){
            HashMap<Integer, ArrayList<Block>> proceduralMap = new HashMap<>();
            Location upLoc = block.getLocation().clone();
            Location downLoc = block.getLocation().clone();
            boolean hasLeaves = false;
            int yLeaves = 0;
            int t = 1;
            while (upLoc.clone().add(0,1,0).getBlock().getType() == Material.LOG||upLoc.clone().add(0,1,0).getBlock().getType() == Material.LOG_2){
                ArrayList<Block> b = new ArrayList<>();
                b.add(upLoc.add(0,1,0).getBlock());
                proceduralMap.put(t,b);
                t++;

                if(!hasLeaves){
                    if(upLoc.clone().add(1,0,0).getBlock().getType().equals(Material.LEAVES) || upLoc.clone().add(1,0,0).getBlock().getType().equals(Material.LEAVES_2)){
                        hasLeaves = true;
                        yLeaves = upLoc.getBlockY();
                        continue;
                    }
                    if(upLoc.clone().add(-1,0,0).getBlock().getType().equals(Material.LEAVES) || upLoc.clone().add(1,0,0).getBlock().getType().equals(Material.LEAVES_2)){
                        hasLeaves = true;
                        yLeaves = upLoc.getBlockY();
                        continue;
                    }
                    if(upLoc.clone().add(0,0,1).getBlock().getType().equals(Material.LEAVES) || upLoc.clone().add(1,0,0).getBlock().getType().equals(Material.LEAVES_2)){
                        hasLeaves = true;
                        yLeaves = upLoc.getBlockY();
                        continue;
                    }
                    if(upLoc.clone().add(0,0,-1).getBlock().getType().equals(Material.LEAVES) || upLoc.clone().add(1,0,0).getBlock().getType().equals(Material.LEAVES_2)){
                        hasLeaves = true;
                        yLeaves = upLoc.getBlockY();
                    }
                }
            }
            t = 1;
            while (downLoc.clone().add(0,-1,0).getBlock().getType() == Material.LOG||downLoc.clone().add(0,-1,0).getBlock().getType() == Material.LOG_2){
                ArrayList<Block> b = new ArrayList<>();

                if(proceduralMap.containsKey(t)){
                    b.addAll(proceduralMap.get(t));
                }

                b.add(downLoc.add(0,-1,0).getBlock());
                proceduralMap.put(t,b);
                t++;
            }
            if(!hasLeaves){
                upLoc.add(0,-1,0);
            }else {
                upLoc.setY(yLeaves-0.30);
            }
          
            new BukkitRunnable() {
                    int i = new Random().nextInt(2 + 1 - 1) + 1;
                    int ticks = 0;
                    @Override
                    public void run() {

                        if(ticks % (3*10) == 0){
                            if(i != 0){
                                int x = new Random().nextInt(3 + 1 - (-3)) + (-3);
                                System.out.println("X>>" +x);
                                int z = new Random().nextInt(3 + 1 - (-3)) + (-3);
                                System.out.println("Z>>" +z);
                                Location clone = upLoc.clone().add(x,0,z);
                                Item item = clone.getWorld().dropItem(upLoc, new ItemStack(Material.APPLE));
                                item.setVelocity(new Vector(0,0,0));
                               // item.setStackAmount(1);


                                i--;
                            }

                            if (proceduralMap.isEmpty()){
                                cancel();
                            }
                        }

                        if (proceduralMap.containsKey(ticks)) {
                            for(Block b : proceduralMap.get(ticks)){
                                b.breakNaturally();
                                b.getWorld().playSound(b.getLocation(), Sound.DIG_WOOD,1,1);
                            }
                            proceduralMap.remove(ticks);
                        }

                        ticks++;


                    }
                }.runTaskTimer(InazumaUHC.get, 1,3);



        }
    }
}
