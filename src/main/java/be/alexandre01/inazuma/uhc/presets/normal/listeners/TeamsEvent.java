package be.alexandre01.inazuma.uhc.presets.normal.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamDeathEvent;
import be.alexandre01.inazuma.uhc.custom_events.teams.TeamSafeTeleportEvent;
import be.alexandre01.inazuma.uhc.presets.normal.timers.StartingTimer;
import be.alexandre01.inazuma.uhc.presets.normal.timers.WaitingTimer;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamsEvent implements Listener {
    private InazumaUHC i;
    public TeamsEvent(){
         i = InazumaUHC.get;
    }
    @EventHandler
    public void onTeamTeleport(TeamSafeTeleportEvent event){
        Timer timer = i.tm.getTimer(StartingTimer.class);
        timer.runTaskTimerAsynchronously(i,0,20);
    }


}
