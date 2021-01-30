package be.alexandre01.inazuma.uhc.custom_events.timers;

import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class TimerCancelEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private Timer timer;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public TimerCancelEvent(Timer timer){
        this.timer = timer;
    }

    public Timer getTimer() {
        return timer;
    }

    public String getTimerName(){
        return timer.getTimerName();
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}

