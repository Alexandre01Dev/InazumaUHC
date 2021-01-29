package be.alexandre01.inazuma.uhc.presets.normal.listeners;

import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCancelEvent;
import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCreateEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TimerEvent implements Listener {
    @EventHandler
    public void onTimerCreate(TimerCreateEvent event){

    }

    @EventHandler
    public void onTimerCancel(TimerCancelEvent event){
        System.out.println("CancelTimer");
       if(event.getTimerName().equals("waitingTimer")){
           GameState.get().setTo(State.STARTING);
       }
       if(event.getTimerName().equals("startingTimer")){
           GameState.get().setTo(State.PLAYING);
       }
        if(event.getTimerName().equals("invicibilityTimer")){
            Preset.instance.pData.isInvisible = false;
        }
    }
}
