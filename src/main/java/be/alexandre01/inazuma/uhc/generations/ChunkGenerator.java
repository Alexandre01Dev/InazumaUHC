package be.alexandre01.inazuma.uhc.generations;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.chunks.ForcedChunkFinishedEvent;
import be.alexandre01.inazuma.uhc.custom_events.chunks.ForcedChunkLoadingEvent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.github.paperspigot.PaperSpigotConfig;
import org.github.paperspigot.PaperSpigotWorldConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChunkGenerator {
    private  ArrayList<ChunkCoord> chunksCord;
    private int totalChunk;
    private int actualChunk = 0;
    private InazumaUHC inazumaUHC;
    private  World world;

    public ChunkGenerator(){
        inazumaUHC = InazumaUHC.get;
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
        return chunks;

    }

    public void generate(final Chunk origin, final int radius, boolean n) {
        if (n) {
            this.world = origin.getWorld();
            this.chunksCord = around(origin, radius);
            this.totalChunk = this.chunksCord.size();
            ForcedChunkLoadingEvent forcedChunkLoadingEvent = new ForcedChunkLoadingEvent(this);
            Bukkit.getPluginManager().callEvent(forcedChunkLoadingEvent);
            if(forcedChunkLoadingEvent.isCancelled()){
                return;
            }
        }
        Instant before = Instant.now();
        for (int i = this.actualChunk; i < this.chunksCord.size(); i++) {
            ChunkCoord cc = this.chunksCord.get(i);
            this.chunksCord.remove(cc);
            Chunk c = this.world.getChunkAt(cc.getX(), cc.getZ());
            c.load(true);
            if (Duration.between(before, Instant.now()).toMillis() >= 500) {
                int r = this.totalChunk - this.chunksCord.size();
                int pourcentage = r * 100 / this.totalChunk;
                System.out.println("La map (" + (this.totalChunk / 16) + "/" + (this.totalChunk / 16) + ") est gen à " + pourcentage + "%");

                new BukkitRunnable() {
                    public void run() {
                        ChunkGenerator.this.generate(origin, radius, false);
                    }

                }.runTaskLater(inazumaUHC, 7);
                return;
            }
        }
        System.out.println("finish");
        ForcedChunkFinishedEvent forcedChunkFinishedEvent = new ForcedChunkFinishedEvent(this);
        Bukkit.getPluginManager().callEvent(forcedChunkFinishedEvent);
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
}

    class ChunkCoord{
        private int x;
        private int z;
        public ChunkCoord(int x, int z){
            this.x = x;
            this.z =z;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }
}

