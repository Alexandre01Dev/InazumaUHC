package be.alexandre01.inazuma.uhc.scenarios.cutclean.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class OreBreak implements Listener {
    public ArrayList<Material> ores = new ArrayList<>();
    public Material[] materials = {Material.COAL_ORE,Material.DIAMOND_ORE,Material.GOLD_ORE,Material.EMERALD_ORE,Material.REDSTONE_ORE,Material.IRON_ORE,Material.LAPIS_ORE};

    public OreBreak(){
        ores.addAll(Arrays.asList(materials));
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event){
        Block block = event.getBlock();
        if(ores.contains(block.getType())){
            Collection<ItemStack> blockDrops = event.getBlock().getDrops();
            event.getPlayer().giveExp(event.getExpToDrop());
            event.setCancelled(true);

            block.setType(Material.AIR);
            for(ItemStack it : blockDrops){
                block.getWorld().dropItemNaturally(block.getLocation(),it);
            }
        }

        if(block.getType().equals(Material.STONE)){
            event.setCancelled(true);
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(),new ItemStack(Material.COBBLESTONE));
        }
    }
}
