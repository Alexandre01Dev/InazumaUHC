package be.alexandre01.inazuma.uhc.scenarios.hasteyboys;

import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

public class HasteyBoys extends Scenario {
    public HasteyBoys() {
        super("HasteyBoys", "Enchante les pioches,haches,pelles automatiquement");
        addListener(new HasteyBoysListener());
        ItemBuilder itemBuilder = new ItemBuilder(Material.IRON_PICKAXE);
        setItemStack(itemBuilder.toItemStack());
 }
}