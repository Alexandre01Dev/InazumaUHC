package be.alexandre01.inazuma.uhc.managers.player;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunkCoord;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunksGenerator;
import be.alexandre01.inazuma.uhc.presets.Preset;
import com.google.common.collect.Iterables;
import lombok.var;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerMovementManager implements Listener {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private HashMap<Grid,ArrayList<Location>> locations;
    private HashMap<Location, action> actions;
    private HashMap<ChunkCoord, ChunkGrid> in;
    private HashMap<ChunkCoord, ArrayList<ChunkGrid>> nearChunksGrid;
    private HashMap<Block,action> blockLocations = new HashMap<>();
    private HashMap<Player,ArrayList<Location>> playerLocations = new HashMap<>();
    private final int radiusGrid = 6;
    private final double mouvementRadius = 1.5d;
    private ArrayList<Player> players = new ArrayList<>();
    private ChunkGrid chunkGrid;
    public PlayerMovementManager(){
        locations = new HashMap<>();
        actions = new HashMap<>();
        in = new HashMap<>();
        nearChunksGrid = new HashMap<>();
    }

    public void init(){
        InazumaUHC.get.lm.addListener(this);
    }

    public void setTimer(){
        chunkGrid = new ChunkGrid(radiusGrid);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    ArrayList<Grid> gs = chunkGrid.getGrids(player.getLocation());
                    if(gs == null)
                        continue;

                    if(gs.isEmpty())
                        continue;


                    playerLocations.remove(player);
                    ArrayList<Location> locs = new ArrayList<>();
                    for(Grid g : gs){
                        System.out.println("GS SIZE>>" + gs.size()+ "GC SIZE"+ g.chunkCoords.size());
                        if(locations.containsKey(g)){
                            for(Location loc : locations.get(g)){
                                System.out.println("PIEGE !!");
                                double cX = player.getLocation().getBlockX()/16D;
                                double cZ = player.getLocation().getBlockZ()/16D;
                                if(Math.abs(loc.getChunk().getX()-cX) <= mouvementRadius && Math.abs(loc.getChunk().getZ()-cZ) <= mouvementRadius){
                                    locs.add(loc);
                                }
                            }
                        }
                    }
                    playerLocations.put(player,locs);
                }
            }
        },0,1, TimeUnit.SECONDS);
    }
    public void addBlockLocation(Location loc,action action){
        Location location = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());


        blockLocations.put(location.getBlock(),action);
    //   ArrayList<Location> gs = new ArrayList<>();


      //  gs.add(location);


      //  actions.put(location,action);
    }
    public void remBlockLocation(Location loc){
        Location location = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());


        blockLocations.remove(location.getBlock());
        //   ArrayList<Location> gs = new ArrayList<>();


        //  gs.add(location);


        //  actions.put(location,action);
    }


    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event){
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY() && to.getBlockZ() == from.getBlockZ()) {
            return; // did not actually move to a new block. do nothing.
        }

        Block underPlayer = to.getBlock();
        String blockType = underPlayer.getType().toString();


        if (!blockLocations.containsKey(underPlayer)) {
            return;
        }

        Player player = event.getPlayer();

        if(InazumaUHC.get.spectatorManager.getPlayers().contains(player))
            return;

        blockLocations.get(underPlayer).a(player);
    }


    public interface action{
        public void a(Player player);
    }




    public class ChunkGrid{
        private HashMap<Integer,ArrayList<Grid>> gridsByX;
        private HashMap<Integer,ArrayList<Grid>> gridsByZ;
        private HashMap<Integer,Grid> gridsByCoord;
        public ChunkGrid( int radius){
            gridsByZ = new HashMap<>();
            gridsByX = new HashMap<>();
            gridsByCoord = new HashMap<>();
            int border = ((Preset.instance.p.getBorderSize(World.Environment.NORMAL)/16)/radius)+1;
            for (int j = -border; j < border; j++) {
                for (int k = -border; k < border; k++) {
                    ArrayList<Grid> gX = new ArrayList<>();
                    ArrayList<Grid> gZ = new ArrayList<>();
                    Grid lastGridX = null;
                    Grid lastGridZ = null;
                    if(gridsByX.containsKey(j)){
                        gX = gridsByX.get(j);
                        lastGridX = Iterables.getLast(gX);
                    }
                    if(gridsByZ.containsKey(k)){
                        gZ = gridsByZ.get(k);
                        lastGridZ = Iterables.getLast(gZ);
                    }
                    Grid grid = new Grid(j,k,radius);

                    gX.add(grid);
                    gridsByX.put(j,gX);

                    gZ.add(grid);
                    gridsByZ.put(k,gZ);

                    gridsByCoord.put(j*k,grid);


                    grid.backGridZ = grid;

                    if(lastGridX != null){
                        grid.backGridX = lastGridZ;
                        lastGridX.afterGridZ = grid;
                    }
                    if(lastGridZ != null){
                        grid.backGridZ = lastGridX;
                        lastGridZ.afterGridX = grid;
                    }
                }
            }
        }

        public Grid createOrGetGrid(Location location){
            Chunk c = location.getChunk();
            int x = c.getX()% radiusGrid;
            int z = c.getZ()% radiusGrid;
            if(gridsByX.containsKey(x) && gridsByZ.containsKey(z)){
                return gridsByCoord.get(x*z);
            }

            Grid grid = new Grid(x,z,radiusGrid);

            ArrayList<Grid> gX = new ArrayList<>();
            ArrayList<Grid> gZ = new ArrayList<>();
            if(gridsByX.containsKey(x)){
                gX = gridsByX.get(x);
            }
            gX.add(grid);
            gridsByX.put(x,gX);

            if(gridsByZ.containsKey(z)){
                gZ = gridsByZ.get(z);
            }
            gZ.add(grid);

            gridsByZ.put(z,gZ);

            gridsByCoord.put(x*z,grid);



            return grid;
        }
        public ArrayList<Grid> getGrids(Location location){
            ArrayList<Grid> grids = new ArrayList<>();
            Chunk c = location.getChunk();

            // A RECUP SI (location.getBlockX/16D/radius)-x > 0.25 = recup le border derriere.
            int x = c.getX()% radiusGrid;
            int z = c.getZ()% radiusGrid;
            System.out.println("xRad >> "+x);
            System.out.println("zRad >> "+z);


            for(Integer xv : gridsByX.keySet()){
                System.out.println("X GRID >> "+ xv);
            }

            for(Integer zv : gridsByZ.keySet()){
                System.out.println("Z GRID >> "+ zv);
            }
            if(gridsByX.containsKey(x) && gridsByZ.containsKey(z)){
                Grid grid = gridsByCoord.get(x*z);
                grids.add(grid);

                double vX = location.getBlockX() / 16D / radiusGrid - x;
                if (vX == 0.25d) {
                    grids.add(grid.backGridX);
                } else if (vX == 0.75d) {
                    grids.add(grid.afterGridX);
                }

                double vZ = location.getBlockZ() / 16D / radiusGrid - z;
                if (vZ == 0.25d) {
                    grids.add(grid.backGridZ);
                } else if (vZ == 0.75d) {
                    grids.add(grid.afterGridZ);
                }
                System.out.println("xRad >> "+x+"vX >> "+ vX);
                System.out.println("zRad >> "+z+"vZ >> "+ vZ);
            }


            return grids;
        }
    }

    public class Grid{
        public Grid backGridX = null;
        public Grid afterGridX = null;

        public Grid backGridZ = null;
        public Grid afterGridZ = null;
        public ArrayList<ChunkCoord> chunkCoords = new ArrayList<>();
        public Grid(int x,int z,int radius){
            for (int j = x; j < x+radius; j++) {
                for (int k = z; k < z+radius; k++) {
                    chunkCoords.add(new ChunkCoord(j,k));
                }
            }
        }
    }
}
