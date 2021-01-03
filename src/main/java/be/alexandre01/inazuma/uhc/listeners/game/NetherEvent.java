package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.generations.NetherPortalsManager;
import be.alexandre01.inazuma.uhc.generations.Portal;
import be.alexandre01.inazuma.uhc.generations.PortalData;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.utils.Cuboid;
import be.alexandre01.inazuma.uhc.utils.Region;
import net.minecraft.server.v1_8_R3.PortalTravelAgent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TravelAgent;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

import javax.lang.model.element.ElementVisitor;
import javax.sound.sampled.Port;
import java.util.ArrayList;

public class NetherEvent  implements Listener {
    public static Location from = null;
    @EventHandler
    public void onNetherPortal(PlayerPortalEvent event){
        NetherPortalsManager npm = InazumaUHC.get.npm;
       // from = event.getFrom();
        if(!npm.active){
            event.getPlayer().sendMessage("§cTu ne peux plus rentrer dans le Nether car il est désormais désactivé.");
            return;
        }
        if(Preset.instance.p.getNether()){
            event.useTravelAgent(true);

            World netherWorld = InazumaUHC.get.worldGen.netherWorld;
            World defaultWorld =  InazumaUHC.get.worldGen.defaultWorld;
            Location l = null;
            TravelAgent p =  event.getPortalTravelAgent();
            p.setCanCreatePortal(true);
            if(event.getPlayer().getWorld() == defaultWorld){

                PortalData data = npm.find(event.getFrom());
                Location search = data.dLoc;

                System.out.println(search);
                if(search != null){

                   l = p.findOrCreate(search);
                   if(!data.b){
                       Location v = l.clone();
                       boolean isX = false;
                       double lastValue;
                       for (int i = -1; i < 1; i++) {
                           v.setX(v.getX()+i);
                           if(v.getBlock().getType().equals(Material.OBSIDIAN)){
                               isX = true;
                               lastValue = v.getBlockX()+i;
                               break;
                           }
                       }

                       Location block1 = l.clone();
                       Location block2 = l.clone();
                       if(isX){
                           block1.add(-2,-1,-1);
                           block2.add(4,6,1);
                       }else {
                           block1.add(-1,-1,-2);
                           block2.add(1,6,4);
                       }

                       Portal portal = new Portal(block1,block2);
                       npm.getPortalsToDefault().put(portal,event.getFrom());
                       npm.portalUsedByPlayer.put(event.getPlayer(),event.getFrom());
                       System.out.println(l.getBlockX()+"/"+l.getBlockY()+"/"+l.getBlockZ());
                       data.b = true;
                   }


                }else {
                    p.setCreationRadius(1);
                    p.setSearchRadius(1);
                    l = npm.netherLocGeneration();
                    l = p.findOrCreate(l);

                    npm.addPortals(event.getFrom(),l);
                }
            }else{
                PortalData data = npm.find(event.getFrom());
                Location search = data.dLoc;
                System.out.println(search);
                if(search != null){
                    p.setCreationRadius(1);
                    p.setSearchRadius(1);
                    l = p.findOrCreate(search);
                    if(!data.b){
                        Location v = l.clone();
                        boolean isX = false;
                        double lastValue;
                        for (int i = -1; i < 1; i++) {
                            v.setX(v.getX()+i);
                            if(v.getBlock().getType().equals(Material.OBSIDIAN)){
                                isX = true;
                                lastValue = v.getBlockX()+i;
                                break;
                            }
                        }

                        Location block1 = l.clone();
                        Location block2 = l.clone();
                        if(isX){
                            block1.add(-2,-1,-1);
                            block2.add(4,6,1);
                        }else {
                            block1.add(-1,-1,-2);
                            block2.add(1,6,4);
                        }

                        Portal portal = new Portal(block1,block2);
                        npm.getPortalsToNether().put(portal,event.getFrom());
                        System.out.println(l.getBlockX()+"/"+l.getBlockY()+"/"+l.getBlockZ());
                        data.b = true;
                    }



                    //     npm.getPortalsToDefault().put();
                }else {
                    l = npm.defaultLocGeneration();
                    p.setCreationRadius(1);
                    p.setSearchRadius(1);
                    l = p.findOrCreate(l);
                    npm.addPortals(l,event.getFrom());
                }
            }
            event.setTo(l);
           // event.setPortalTravelAgent(p);
            PortalTravelAgent po;

            System.out.println("portal>" );
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(Messages.get("game.netherAllowed"));

    }
   /* @EventHandler
    public void onMove(PlayerMoveEvent event){
        NetherPortalsManager npm = InazumaUHC.get.npm;

        if(npm.find(event.getPlayer().getLocation()) != null){
            event.getPlayer().sendMessage("reg"+event.getPlayer().getLocation().getX()+"/"+event.getPlayer().getLocation().getZ());
        }
    }*/

    @EventHandler
    public void onCreate(PortalCreateEvent event){
        System.out.println(event.getReason());
        portalCreation(event.getWorld(),event.getBlocks());
    }

    public void portalCreation(World world,ArrayList<Block> blocks){
        NetherPortalsManager npm = InazumaUHC.get.npm;
        Orientation o = getOrientation(blocks);
        Location[] locs = getMaxLocations(blocks);

        Portal cuboid = new Portal(locs[0],locs[1]);
        Location loc = new Location(cuboid.getWorld(),mid(cuboid.getMinX(),cuboid.getMaxX()),mid(cuboid.getMinY(),cuboid.getMaxY()),mid(cuboid.getMinZ(),cuboid.getMaxZ()));
        if (world.getEnvironment().equals(World.Environment.NORMAL)) {
            if(from != null){
                npm.getPortalsToNether().put(cuboid,from);

                from = null;
            }else {
                npm.getPortalsToNether().put(cuboid,npm.netherLocGeneration());
            }

        }else {
            if(from != null){
                npm.getPortalsToDefault().put(cuboid,from);
                System.out.println("from !");
                from = null;
            }else {
                npm.getPortalsToDefault().put(cuboid,npm.defaultLocGeneration());
            }

        }

        //cuboid.setUsed(true);
    }
    private int mid(int x, int y) {
        return x/2 + y/2 + (x%2 + y%2)/2;
    }

    private Orientation getOrientation(ArrayList<Block> blocks){
        Location lastLoc = null;
        for(Block b : blocks){
            if(lastLoc != null){
                double lastX = lastLoc.getBlockX();
                double lastZ = lastLoc.getBlockZ();

                double currentX = b.getLocation().getBlockX();
                double currentZ = b.getLocation().getBlockZ();

                if(lastX != currentX){
                    return Orientation.X;
                }

                if(lastZ != currentZ){
                    return Orientation.Z;
                }
            }
            lastLoc = b.getLocation();

        }
        return null;
    }

    enum Orientation{
        X,Z;
    }

    private Location[] getMaxLocations(ArrayList<Block> blocks){
        World world = blocks.get(0).getWorld();
        double minX = blocks.get(0).getX();
        double minY = blocks.get(0).getY();
        double minZ = blocks.get(0).getZ();


        double maxX = blocks.get(0).getX();
        double maxY = blocks.get(0).getY();
        double maxZ = blocks.get(0).getZ();

        for (int i = 1; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if(minX > b.getX()){
                minX = b.getX();
            }

            if(minY > b.getY()){
                minY = b.getY();
            }

            if(minZ > b.getZ()){
                minZ = b.getZ();
            }

            if(maxX < b.getX()){
                maxX = b.getX();
            }

            if(maxY < b.getY()){
                maxY = b.getY();
            }

            if(maxZ < b.getZ()){
                maxZ = b.getZ();
            }

        }
        Location[] locs = new Location[2];
        locs[0] = new Location(world,minX-1,minY-1,minZ-1);
        locs[1] = new Location(world,maxX+1,maxY+1,maxZ+1);


        return locs;
    }
}
