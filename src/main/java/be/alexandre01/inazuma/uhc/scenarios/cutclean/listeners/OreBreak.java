package be.alexandre01.inazuma.uhc.scenarios.cutclean.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class OreBreak implements Listener {
    public ArrayList<Material> ores =  new ArrayList<>();
    public HashMap<Material,Material> oresTo = new HashMap<>();
    public HashMap<Material,Integer> xpTo = new HashMap<>();
    public Material[] materials = {Material.COAL_ORE,Material.DIAMOND_ORE,Material.GOLD_ORE,Material.EMERALD_ORE,Material.REDSTONE_ORE,Material.IRON_ORE,Material.LAPIS_ORE};
    public Material[] materialTo = {Material.COAL,Material.DIAMOND,Material.GOLD_INGOT,Material.EMERALD,Material.REDSTONE,Material.IRON_INGOT,Material.INK_SACK};
    public int[] xp = {2,6,5,6,4,3,4};

    public OreBreak(){

        for (int i = 0; i < materials.length; i++) {
            oresTo.put(materials[i],materialTo[i]);
            xpTo.put(materials[i],xp[i]);
        }

        ores.addAll(Arrays.asList(materials));
    }
    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event){
        if(ores.contains(event.getBlock().getType())){
            event.setExpToDrop(xpTo.get(event.getBlock().getType()));
        }
    }
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event){
        Item item = event.getEntity();

        if(ores.contains(item.getItemStack().getType())){
            item.getItemStack().setType(oresTo.get(item.getItemStack().getType()));
        }

        if(item.getItemStack().getType().equals(Material.STONE)){
            item.setItemStack(new ItemStack(Material.COBBLESTONE));
        }
    }
}
