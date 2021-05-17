package be.alexandre01.inazuma.uhc.custom_events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerEliminatedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private List<ItemStack> drops;
    private Player player;
    private Player killer;
    private int xp;
    private boolean isCancelled;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public PlayerEliminatedEvent(Player player, Player killer){
        this.player = player;
        this.killer = killer;
        this.drops = drops;
        this.xp = xp;
    }

    public int getDroppedExp() {
        return xp;
    }

    public void setDroppedExp(int xp) {
        this.xp = xp;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getKiller() {
        return killer;
    }

    public int getXp() {
        return xp;
    }
}
