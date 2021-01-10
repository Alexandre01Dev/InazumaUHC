package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCancelEvent;
import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TimerEvent implements Listener {
    @EventHandler
    public void TimerCreateEvent(TimerCreateEvent event){
        event.getTimer().isRunning = true;
    }

    @EventHandler
    public void TimerCancelEvent(TimerCancelEvent event){
        event.getTimer().isRunning = false;
    }
}
