package be.alexandre01.inazuma.uhc.custom_events.chunks;

import be.alexandre01.inazuma.uhc.generations.ChunkGenerator;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ForcedChunkLoadingEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private ChunkGenerator chunkGenerator;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ForcedChunkLoadingEvent(ChunkGenerator chunkGenerator){
        this.chunkGenerator = chunkGenerator;
    }

    public ChunkGenerator get(){
        return chunkGenerator;
    }


    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

}

