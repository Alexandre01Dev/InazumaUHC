package be.alexandre01.inazuma.uhc.presets.normal.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.chunks.ForcedChunkFinishedEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.timers.WaitingTimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChunkEvent implements Listener {
    private InazumaUHC i;
    private int world = 0;
    public ChunkEvent(){
        this.i = InazumaUHC.get;
    }
    @EventHandler
    public void onForceChunkStopped(ForcedChunkFinishedEvent event){
        IPreset p = Preset.instance.p;
        if(p.getNether() && world == 1){
            Timer timer = i.tm.getTimer(WaitingTimer.class);
            timer.runTaskTimer(i,0,20);
        }
        if(!p.getNether() && world == 0){
            Timer timer = i.tm.getTimer(WaitingTimer.class);
            timer.runTaskTimer(i,0,20);
        }

        world++;
        System.out.println("ChunkFinished");

    }
}
