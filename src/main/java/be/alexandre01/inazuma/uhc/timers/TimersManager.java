package be.alexandre01.inazuma.uhc.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;

public class TimersManager {
    public HashMap<Class, Timer> timers;
    public HashMap<Class,Timer> oldTimer;
    private Plugin pl;
    private PluginManager pluginManager;
    public TimersManager(){
        this.timers = new HashMap<>();
        this.oldTimer = new HashMap<>();
        this.pl = InazumaUHC.get;
        this.pluginManager = pl.getServer().getPluginManager();
    }

    public void addTimer(Timer timer){
        System.out.println("ADD TIMER >> "+ timer.getTimerName());

        if(oldTimer.containsKey(timer.getClass())){
            Timer t = oldTimer.get(timer.getClass());
            if(t.isRunning){
                System.out.println("Est lancé >> "+ timer.getTimerName());
                timers.put(t.getClass(),t);
                return;
            }
        }
        timers.put(timer.getClass(),timer);
    }
    public Timer getTimer(Class c){
        return timers.get(c);
    }
    public void removeTimer(Class timer){
        timers.remove(timer);
    }

    public void stop(Timer timer){
        timer.cancel();
        timers.remove(timer.getClass());
    }
    public void searchPresetTimer(){
        IPreset p = Preset.instance.p;
        ArrayList<Timer> timers = p.getTimers();
        if(timers != null){
            if(!timers.isEmpty()){
                for(Timer t : timers){
                    addTimer(t);
                }
            }
        }
    }

    public void clear(){
        oldTimer.clear();
        oldTimer.putAll(timers);
        timers.clear();
    }
}
