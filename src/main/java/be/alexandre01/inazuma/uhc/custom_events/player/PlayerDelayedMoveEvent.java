package be.alexandre01.inazuma.uhc.custom_events.player;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerDelayedMoveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private List<ItemStack> drops;
    @Getter private Player player;
    @Getter private Location location;


    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PlayerDelayedMoveEvent(Player player, Location location){
        this.player = player;
        this.location = location;
    }


}
