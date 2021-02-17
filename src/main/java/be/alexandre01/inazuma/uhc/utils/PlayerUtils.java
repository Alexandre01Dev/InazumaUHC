package be.alexandre01.inazuma.uhc.utils;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class PlayerUtils {
    public static Entity getNearestEntityInSight(Player player, int range) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            return entities.get(k);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static ArrayList<Player> getNearbyPlayers(Location location, int x, int y, int z){
        ArrayList<Player> p = new ArrayList<>();
        InazumaUHC i = InazumaUHC.get;
        for (Entity entity : location.getWorld().getNearbyEntities(location,x,y,z)){
            if(entity instanceof Player){
                Player player = (Player) entity;
                if(!i.spectatorManager.getPlayers().contains(player)){
                    p.add(player);
                }
            }
        }
        return p;
    }

    public static ArrayList<Player> getNearbyPlayersFromPlayer(Player player, int x, int y, int z){
        ArrayList<Player> p = new ArrayList<>();
        InazumaUHC i = InazumaUHC.get;
        for (Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),x,y,z)){
            if(entity instanceof Player){
                Player opposant = (Player) entity;
                if(opposant == player)
                    continue;

                if(!i.spectatorManager.getPlayers().contains(opposant)){
                    p.add(player);
                }
            }
        }
        return p;
    }
}
