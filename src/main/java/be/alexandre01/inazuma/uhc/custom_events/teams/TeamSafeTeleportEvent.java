package be.alexandre01.inazuma.uhc.custom_events.teams;

import be.alexandre01.inazuma.uhc.state.State;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamSafeTeleportEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private TeamManager teamManager;
    private boolean isCancelled;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public TeamSafeTeleportEvent(TeamManager teamManager){
        this.teamManager = teamManager;
    }

    public TeamManager get(){
        return teamManager;
    }

}
