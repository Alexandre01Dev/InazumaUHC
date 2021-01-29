package be.alexandre01.inazuma.uhc.custom_events.teams;

import be.alexandre01.inazuma.uhc.teams.Team;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TeamDeathEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private Team team;
    private Player lastPlayer;
    private boolean isCancelled;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public TeamDeathEvent(Team team,Player lastPlayer){
        this.lastPlayer = lastPlayer;
        this.team = team;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public Team getTeam(){
        return team;
    }
}
