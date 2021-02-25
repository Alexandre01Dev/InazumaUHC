package be.alexandre01.inazuma.uhc.custom_events.player;

import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInstantDeathEvent extends Event implements Cancellable {
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

    public PlayerInstantDeathEvent(Player player, List<ItemStack> drops,int xp,Player killer){
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

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
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

    public void setPlayer(Player player) {
        this.player = player;
    }
}
