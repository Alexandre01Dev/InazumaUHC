package be.alexandre01.inazuma.uhc.scenarios;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.event.Listener;

public class Scenario {
    String name;
    String description;
    InazumaUHC inazumaUHC = InazumaUHC.get;
    public Scenario(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addListener(Listener... listeners){
        for (Listener l : listeners){
            inazumaUHC.lm.addListener(l);
        }

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
