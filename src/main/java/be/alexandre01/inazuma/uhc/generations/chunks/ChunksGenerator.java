package be.alexandre01.inazuma.uhc.generations.chunks;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.chunks.ForcedChunkFinishedEvent;
import be.alexandre01.inazuma.uhc.custom_events.chunks.ForcedChunkLoadingEvent;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ChunksGenerator {
    public static HashMap<World.Environment,ArrayList<ChunkCoord>> chunksCalcHash = new HashMap<>();
    private ChunksExecutor chunksExecutor;
    private  ArrayList<ChunkCoord> chunksCord;
    private int totalChunk;
    private int calculateChunks = 0;
    private int actualChunk = 0;
    private InazumaUHC inazumaUHC;
    private  World world;

    public ChunksGenerator(){
        inazumaUHC = InazumaUHC.get;
        chunksExecutor = new ChunksExecutor(this);
    }
    private ArrayList<ChunkCoord> around(Chunk origin, int radius) {

        org.bukkit.World world =  origin.getWorld();

        int length = (radius * 2) + 1;
        ArrayList<ChunkCoord> chunks = new ArrayList<>(length * length);

        int cX = origin.getX();
        int cZ = origin.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                System.out.println(cX + x+">"+cZ + z);
                chunks.add(new ChunkCoord(cX + x, cZ + z));
            }
        }
        chunksCalcHash.put(origin.getWorld().getEnvironment(),new ArrayList<>(chunks));
        return chunks;

    }

    public void generate(final Chunk origin, final int radius, boolean n) {
        if (n) {
            ForcedChunkLoadingEvent forcedChunkLoadingEvent = new ForcedChunkLoadingEvent(this);
            Bukkit.getPluginManager().callEvent(forcedChunkLoadingEvent);
            if(forcedChunkLoadingEvent.isCancelled()){
                return;
            }
            InazumaUHC.get.worldGen.defaultWorld.getChunkAt(0,0);
            this.world = origin.getWorld();
            this.chunksCord = around(origin, radius);
            this.totalChunk = this.chunksCord.size();
            System.out.println("ENVIRONNEMENT >>"+world.getEnvironment().name());
            if(world.getEnvironment().equals(World.Environment.NORMAL)){
                chunksExecutor.schedule();
            }

        }
        Instant before = Instant.now();
        for (int i = this.actualChunk; i < this.chunksCord.size(); i++) {
            ChunkCoord cc = this.chunksCord.get(i);
            this.chunksCord.remove(cc);
            calculateChunks++;
            Chunk c = this.world.getChunkAt(cc.getX(), cc.getZ());
            c.load(true);
            c.load(false);
            c = null;

            if (Duration.between(before, Instant.now()).toMillis() >= 450) {
                int r = this.totalChunk - this.chunksCord.size();
                int pourcentage = r * 100 / this.totalChunk;
                System.out.println("La map (" + (this.totalChunk / 16) + "/" + (this.totalChunk / 16) + ") est gen à " + pourcentage + "%");

                new BukkitRunnable() {
                    public void run() {

                        ChunksGenerator.this.generate(origin, radius, false);
                    }

                }.runTaskLater(inazumaUHC, 7);
                return;
            }
        }
        if(world.getEnvironment().equals(World.Environment.NORMAL)){
            chunksExecutor.getScheduledExecutorService().shutdown();
            for(Player player : Bukkit.getOnlinePlayers()){
                TitleUtils.sendActionBar(player,"§eLa §lmap §evient d'être §a§lGÉNÉRÉ !");
            }
        }

        chunksCord = null;
        System.out.println("finish");
        ForcedChunkFinishedEvent forcedChunkFinishedEvent = new ForcedChunkFinishedEvent(this);
        Bukkit.getPluginManager().callEvent(forcedChunkFinishedEvent);
     //   System.gc(); A VOIR
    }

    public ArrayList<ChunkCoord> getChunksCord() {
        return chunksCord;
    }

    public int getTotalChunk() {
        return totalChunk;
    }

    public int getActualChunk() {
        return actualChunk;
    }

    public World getWorld() {
        return world;
    }

    public int getCalculateChunks() {
        return calculateChunks;
    }
}

