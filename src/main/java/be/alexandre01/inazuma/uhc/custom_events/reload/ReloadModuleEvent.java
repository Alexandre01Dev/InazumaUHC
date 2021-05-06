package be.alexandre01.inazuma.uhc.custom_events.reload;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ReloadModuleEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
