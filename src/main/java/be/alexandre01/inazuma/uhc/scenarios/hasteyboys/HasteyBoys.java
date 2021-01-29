package be.alexandre01.inazuma.uhc.scenarios.hasteyboys;

import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class HasteyBoys extends Scenario {
    public HasteyBoys() {
        super("HasteyBoys", "Enchante les pioches,haches,pelles automatiquement");
        addListener(new HasteyBoysListener());
 }
}