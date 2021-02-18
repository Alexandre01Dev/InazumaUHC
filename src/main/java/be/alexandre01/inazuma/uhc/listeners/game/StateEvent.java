package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.state.PlayingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.StartingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.StateChangeEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StateEvent implements Listener {
    @EventHandler
    public void onStarting(StartingEvent event){
        InazumaUHC.get.lm.removeListener(ProtectionEvent.class);
    }

    @EventHandler
    public void onPlaying(PlayingEvent event){
        System.out.println("PLAYING SCENARIO");
        if(Preset.instance.p.getPlatform() != null){
            Preset.instance.p.getPlatform().despawn();
        }
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
        }
    }
}
