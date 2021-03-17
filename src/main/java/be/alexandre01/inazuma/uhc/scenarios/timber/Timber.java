package be.alexandre01.inazuma.uhc.scenarios.timber;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scenarios.timber.listeners.TimberListener;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;

public class Timber extends Scenario {
    public Timber() {
        super("Timber", "Casse rapidement les arbres");
        addListener(new TimberListener());
        ItemBuilder itemBuilder = new ItemBuilder(Material.LOG);
        setItemStack(itemBuilder.toItemStack());
    }


}
