package be.alexandre01.inazuma.uhc.state;

import be.alexandre01.inazuma.uhc.custom_events.state.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class GameState{
    private static GameState instance;
    private State state = State.NONE;
    public GameState(){
        instance = this;
        setTo(State.WAITING);
    }

    public boolean contains(State state){
        if(this.state == state){
            return true;
        }
           return false;
    }

    public void setTo(State state){
        StateChangeEvent stateChangeEvent = new StateChangeEvent(this.state,state);
        Bukkit.getPluginManager().callEvent(stateChangeEvent);
        if(!stateChangeEvent.isCancelled()){
            Cancellable cancellable = null;
            if(state == State.PREPARING){
                cancellable = new PreparingEvent(this.state,state);
                Bukkit.getPluginManager().callEvent((Event) cancellable);
            }else {
                if(state == State.WAITING){
                    cancellable = new WaitingEvent(this.state,state);
                    Bukkit.getPluginManager().callEvent((Event) cancellable);
                }else {
                    if(state == State.STARTING){
                        cancellable = new StartingEvent(this.state,state);
                        Bukkit.getPluginManager().callEvent((Event) cancellable);
                    }else {
                        if(state == State.PLAYING){
                            cancellable = new PlayingEvent(this.state,state);
                            Bukkit.getPluginManager().callEvent((Event) cancellable);
                        }else {
                            if(state == State.STOPPING){
                                cancellable = new StoppingEvent(this.state,state);
                                Bukkit.getPluginManager().callEvent((Event) cancellable);
                            }
                        }
                    }
                }
            }

            if(cancellable != null){
                if(!cancellable.isCancelled()){
                    this.state = state;
                }
            }


        }
    }

    public static GameState get(){
        return instance;
    }
}