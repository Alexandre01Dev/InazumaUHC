package be.alexandre01.inazuma.uhc.scenarios.betazombie;

import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BetaZombie extends Scenario implements Listener {
    public BetaZombie() {
        super("BetaZombie", "Drop des plumes lorsqu'un zombie est tué");
        addListener(this);
    }

    @EventHandler
    public void onZombieKill(EntityDeathEvent event){
        if(event.getEntity() instanceof Zombie){
            event.getDrops().add(new ItemStack(Material.FEATHER));
        }
    }
}
