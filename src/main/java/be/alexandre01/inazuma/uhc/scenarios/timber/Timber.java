package be.alexandre01.inazuma.uhc.scenarios.timber;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scenarios.timber.listeners.TimberListener;

public class Timber extends Scenario {
    public Timber() {
        super("Timber", "Casse rapidement les arbres");
        addListener(new TimberListener());
        onLoad(new load() {
            @Override
            public void a() {
                InazumaUHC.get.worldGen.defaultWorld.setGameRuleValue("randomTickSpeed", String.valueOf(30));
            }
        });
    }


}
