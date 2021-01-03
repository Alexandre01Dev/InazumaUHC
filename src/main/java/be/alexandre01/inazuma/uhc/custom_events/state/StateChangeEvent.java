package be.alexandre01.inazuma.uhc.custom_events.state;

import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class StateChangeEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private State pState;
    private State nState;
    private boolean isCancelled;
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public StateChangeEvent(State pState, State nState){
        this.pState = pState;
        this.nState = nState;
    }

    public State getFrom(){
        return pState;
    }

    public State getTo(){
       return nState;
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
