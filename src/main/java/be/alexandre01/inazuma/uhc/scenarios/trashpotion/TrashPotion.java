package be.alexandre01.inazuma.uhc.scenarios.trashpotion;

import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class TrashPotion extends Scenario implements Listener {
    public TrashPotion() {
        super("TrashPotion", "Clear la potion une fois bu.");
        addListener(this);
        ItemBuilder itemBuilder = new ItemBuilder(Material.GLASS_BOTTLE);
        setItemStack(itemBuilder.toItemStack());
    }
    @EventHandler
    public void onDrinkPotion(PlayerItemConsumeEvent e){
        if (e.getItem().getType().equals(Material.GLASS_BOTTLE)){
            e.getItem().setType(Material.AIR);
        }
    }

}