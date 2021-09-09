package be.alexandre01.inazuma.uhc.listeners.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamDeathEvent;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamsEvent implements Listener {
    InazumaUHC i;
    public TeamsEvent(){
        i = InazumaUHC.get;
    }
    @EventHandler
    public void onKillTeam(TeamDeathEvent event){
        TeamManager teamManager = i.teamManager;
        if(teamManager.getTeams().size() == 1){
            for(Player player : teamManager.getTeams().get(0).getAllPlayers()){
                //Bukkit.broadcastMessage("§e"+player.getDisplayName()+" a gagné la partie");
            }

        }
    }
}
