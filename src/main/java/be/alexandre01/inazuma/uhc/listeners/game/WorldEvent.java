package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.utils.CustomSkeleton;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class WorldEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntitySpawn(EntitySpawnEvent event){
        if(!(((CraftEntity)event.getEntity()).getHandle() instanceof CustomSkeleton) && (((CraftEntity)event.getEntity()).getHandle() instanceof EntitySkeleton)){
            event.setCancelled(true);
            System.out.println("TRY TO SPAWN");
            CustomSkeleton.spawn(event.getLocation());
            System.out.println("SPAWNED >>" + event.getLocation().getBlockX()+" "+ event.getLocation().getBlockZ());
        }
    }
}
