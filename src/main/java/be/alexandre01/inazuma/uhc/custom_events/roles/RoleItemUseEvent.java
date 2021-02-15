package be.alexandre01.inazuma.uhc.custom_events.roles;

import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RoleItemUseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private Role role;
    private RoleItem roleItem;
    private boolean isCancelled;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RoleItemUseEvent(Player player, Role linkedRole, RoleItem roleItem){
        this.player = player;
        this.roleItem = roleItem;
        this.role = linkedRole;
    }


    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public Role getLinkedRole() {
        return role;
    }

    public RoleItem getRoleItem() {
        return roleItem;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

