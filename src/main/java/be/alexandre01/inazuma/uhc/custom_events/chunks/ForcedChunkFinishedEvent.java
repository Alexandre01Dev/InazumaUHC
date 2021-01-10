package be.alexandre01.inazuma.uhc.custom_events.chunks;

import be.alexandre01.inazuma.uhc.generations.chunks.ChunksGenerator;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ForcedChunkFinishedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private ChunksGenerator chunkGenerator;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ForcedChunkFinishedEvent(ChunksGenerator chunkGenerator){
        this.chunkGenerator = chunkGenerator;
    }

    public ChunksGenerator get(){
        return chunkGenerator;
    }




}
