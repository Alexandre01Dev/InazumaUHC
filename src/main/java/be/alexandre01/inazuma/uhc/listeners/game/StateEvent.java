package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.state.PlayingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.StartingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.StateChangeEvent;
import be.alexandre01.inazuma.uhc.listeners.host.ClickInventory;
import be.alexandre01.inazuma.uhc.listeners.host.CloseInventory;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class StateEvent implements Listener {
    @EventHandler
    public void onStarting(StartingEvent event){
        InazumaUHC.get.lm.removeListener(ClickInventory.class);
        InazumaUHC.get.lm.removeListener(CloseInventory.class);
    }

    @EventHandler
    public void onPlaying(PlayingEvent event){

        InazumaUHC.get.lm.removeListener(ProtectionEvent.class);
        System.out.println("PLAYING SCENARIO");


        Bukkit.getScheduler().scheduleSyncDelayedTask(InazumaUHC.get, new Runnable() {

            @Override
            public void run() {
                if(Preset.instance.p.getPlatform() != null){
                    Preset.instance.p.getPlatform().despawn();
                }
            }
        });

        if(!Scenario.deployed){
            if(!Preset.instance.pData.getScenarios().isEmpty()){
                for(Class<?> c : Preset.instance.pData.getScenarios()){
                    if(Scenario.getScenarios().containsKey(c)){
                        Scenario scenario = Scenario.getScenarios().get(c);
                        if(scenario.getLoad() != null){
                            scenario.getLoad().a();
                        }

                        scenario.deployListener();
                    }
                }
                Scenario.deployed = true;
            }
        }
    }
}
