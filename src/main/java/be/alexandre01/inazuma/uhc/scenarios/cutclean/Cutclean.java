package be.alexandre01.inazuma.uhc.scenarios.cutclean;

import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.listeners.EntityKill;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.listeners.OreBreak;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;

public class Cutclean extends Scenario {
    public Cutclean() {
        super("Cutclean", "Cuits les drops de minerais/mobs automatiquement.");
        addListener(new EntityKill(), new OreBreak());

        ItemBuilder itemBuilder = new ItemBuilder(Material.FURNACE);
        setItemStack(itemBuilder.toItemStack());
    }
}
