package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events;

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
