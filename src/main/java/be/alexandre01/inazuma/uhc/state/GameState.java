package be.alexandre01.inazuma.uhc.state;

import be.alexandre01.inazuma.uhc.custom_events.state.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class GameState{
    private static GameState instance;
    @Getter private State state = State.NONE;
    private ArrayList<preparing> preparings;
    private ArrayList<waiting> waitings;
    private ArrayList<starting> startings;
    private ArrayList<playing> playings;
    private ArrayList<stopping> stoppings;
    public GameState(){
        instance = this;
        preparings = new ArrayList<>();
        waitings = new ArrayList<>();
        startings = new ArrayList<>();
        playings = new ArrayList<>();
        stoppings = new ArrayList<>();
        setTo(State.PREPARING);
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
                for(preparing preparing : preparings){
                    preparing.a();
                }
            }else {
                if(state == State.WAITING){
                    cancellable = new WaitingEvent(this.state,state);
                    Bukkit.getPluginManager().callEvent((Event) cancellable);
                    for(waiting waiting : waitings){
                        waiting.a();
                    }
                }else {
                    if(state == State.STARTING){
                        cancellable = new StartingEvent(this.state,state);
                        Bukkit.getPluginManager().callEvent((Event) cancellable);
                        for(starting starting : startings){
                            starting.a();
                        }
                    }else {
                        if(state == State.PLAYING){
                            cancellable = new PlayingEvent(this.state,state);
                            Bukkit.getPluginManager().callEvent((Event) cancellable);
                            for(playing playing : playings){
                                playing.a();
                            }
                        }else {
                            if(state == State.STOPPING){
                                cancellable = new StoppingEvent(this.state,state);
                                Bukkit.getPluginManager().callEvent((Event) cancellable);
                                for(stopping stopping : stoppings){
                                    stopping.a();
                                }
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

    //LOAD EVENTS
    public void onPreparing(preparing preparing){
        preparings.add(preparing);
    }

    public void onWaiting(waiting waiting){
        waitings.add(waiting);
    }

    public void onStarting(starting starting){
        startings.add(starting);
    }

    public void onPlaying(playing playing){
        playings.add(playing);
    }

    public void onStopping(stopping stopping){
        stoppings.add(stopping);
    }

    //GETTER
    public static GameState get(){
        return instance;
    }

    //INTERFACES
    public interface preparing{
        public void a();
    }
    public interface waiting{
        public void a();
    }
    public interface starting{
        public void a();
    }
    public interface playing{
        public void a();
    }
    public interface stopping{
        public void a();
    }
}