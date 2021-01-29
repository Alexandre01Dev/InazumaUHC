package be.alexandre01.inazuma.uhc.scenarios;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;

public class Scenario {
    String name;
    String description;
    InazumaUHC inazumaUHC = InazumaUHC.get;
    ArrayList<Listener> listeners = new ArrayList<>();
    load load = null;
    public Scenario(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addListener(Listener... listeners){
        this.listeners.addAll(Arrays.asList(listeners));
    }

    public void deployListener(){
        if(!listeners.isEmpty()){
            for(Listener listener : listeners){
                inazumaUHC.lm.addListener(listener);
            }
        }
    }

    public load getLoad(){
        return load;
    }

    public void onLoad(load load){
        this.load = load;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public interface load{
        void a();
    }
}
