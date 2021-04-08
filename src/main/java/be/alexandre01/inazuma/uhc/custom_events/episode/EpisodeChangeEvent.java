package be.alexandre01.inazuma.uhc.custom_events.episode;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EpisodeChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
