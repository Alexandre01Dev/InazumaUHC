package be.alexandre01.inazuma.uhc.generations;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


import java.util.HashMap;
import java.util.Random;

public class NetherPortalsManager {
    private HashMap<Portal,Location> portalsToNether;
    private HashMap<Portal,Location> portalsToDefault;
    public HashMap<Player,Location> portalUsedByPlayer;
    public boolean active = true;
    private World netherWorld;
    private World defaultWorld;

    public NetherPortalsManager(){
        this.portalUsedByPlayer = new HashMap<>();
        this.portalsToDefault = new HashMap<>();
        this.portalsToNether = new HashMap<>();

        this.netherWorld = InazumaUHC.get.worldGen.netherWorld;
        this.defaultWorld = InazumaUHC.get.worldGen.defaultWorld;
    }

    public void addPortals(Location loc1,Location loc2){
        loc1.add(-1.25,-1,-1.25);
        Location cloneLoc1 = loc1.clone();
        cloneLoc1.add(1.25,3,1.25);
        Portal cuboid1 = new Portal(loc1,cloneLoc1);
        loc2.add(-1.25,-1,-1.25);
        Location cloneLoc2 = loc2.clone();
        cloneLoc2.add(1.25,3,1.25);
        Portal cuboid2 = new Portal(loc2,cloneLoc2);
        portalsToNether.put(cuboid1,loc2);
        portalsToDefault.put(cuboid2,loc1);
    }

    private int mid(int x, int y) {
        return x/2 + y/2 + (x%2 + y%2)/2;
    }
    public HashMap<Portal,Location> getPortalsToNether(){
        return portalsToNether;
    }
    public HashMap<Portal,Location> getPortalsToDefault(){
        return portalsToDefault;
    }

    public Location netherLocGeneration(){
        IPreset preset = Preset.instance.p;
        Random rand1 = new Random();
        int size = preset.getBorderSize(World.Environment.NETHER);
        int x = rand1.nextInt(size - ((-size) + 1)) + (-size);
        Random rand2 = new Random();
        int z = rand2.nextInt(size - ((-size) + 1)) + (-size);
        return new Location(netherWorld,x,70,z);
    }

    public Location defaultLocGeneration(){
        IPreset preset = Preset.instance.p;
        Random rand1 = new Random();
        int size = preset.getBorderSize(World.Environment.NORMAL);
        int x = rand1.nextInt(size - ((-size) + 1)) + (-size);
        Random rand2 = new Random();
        int z = rand2.nextInt(size - ((-size) + 1)) + (-size);
        return new Location(defaultWorld,x,70,z);
    }

    public PortalData find(Location location){
        HashMap<Portal,Location> search;
        if(location.getWorld().getEnvironment().equals(World.Environment.NETHER)){
            search = getPortalsToDefault();
        }else{
            search = getPortalsToNether();
        }
        //System.out.println(search);
        for (Portal cuboid : search.keySet()){
            if(cuboid.contains(location)){
                PortalData portalData = new PortalData(search.get(cuboid),false);
                if(!cuboid.isUsed()){
                    Location loc = new Location(cuboid.getWorld(),mid(cuboid.getMinX(),cuboid.getMaxX()),mid(cuboid.getMinY(),cuboid.getMaxY()),mid(cuboid.getMinZ(),cuboid.getMaxZ()));
                    portalData.setOtherLocation(loc);
                    /*if(loc.getWorld().getEnvironment().equals(World.Environment.NETHER)){
                        getPortalsToNether().get(cuboid);

                    }else {
                        getPortalsToNether().get(cuboid);

                        addPortals(loc,netherLocGeneration());
                    }*/
                    cuboid.setUsed(true);
                }

               return portalData;
            }
        }
        return null;
    }
}
