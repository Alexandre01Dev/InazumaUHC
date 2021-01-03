package be.alexandre01.inazuma.uhc.custom_events.chunks;

import be.alexandre01.inazuma.uhc.generations.ChunkGenerator;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ForcedChunkFinishedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private ChunkGenerator chunkGenerator;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ForcedChunkFinishedEvent(ChunkGenerator chunkGenerator){
        this.chunkGenerator = chunkGenerator;
    }

    public ChunkGenerator get(){
        return chunkGenerator;
    }




}
