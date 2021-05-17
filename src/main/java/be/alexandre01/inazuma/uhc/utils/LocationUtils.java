package be.alexandre01.inazuma.uhc.utils;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class LocationUtils {
    public static Location getTop(Location location){

        for (int i = 255; i > 0 ; i--) {
            location.setY(i);
            if(location.getBlock().getType()!= Material.AIR){
                location.setY(i+1);
                return location;
            }
        }
        return null;
    }
    public static Location getTop(World world,double x, double z){
        Location location = new Location(world,x ,255 ,z );
        return getTop(location);
    }
}
