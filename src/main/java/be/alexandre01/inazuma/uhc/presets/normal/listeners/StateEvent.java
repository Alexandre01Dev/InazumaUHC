package be.alexandre01.inazuma.uhc.presets.normal.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.state.PlayingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.StartingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.WaitingEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.presets.normal.timers.NetherTimer;
import be.alexandre01.inazuma.uhc.presets.normal.timers.PVPTimer;
import be.alexandre01.inazuma.uhc.presets.normal.timers.WaitingTimer;
import be.alexandre01.inazuma.uhc.teams.Team;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.TimersManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class StateEvent implements Listener {
    private Normal n;
    private InazumaUHC i;
    private IPreset p;
    private boolean isLaunched = false;
    public StateEvent(Normal normal){
        this.n = normal;
        this.i = InazumaUHC.get;
        this.p = Preset.instance.p;
    }
    @EventHandler
    public void onWaiting(WaitingEvent event){
        System.out.println("Waitingevent called");
        n.waitingScoreboard();
        i.getScoreboardManager().refreshPlayers();
    }
    @EventHandler
    public void onStarting(StartingEvent event){
        System.out.println("StartingEvent called");
        i.worldGen.defaultWorld.getWorldBorder().setSize(p.getBorderSize(World.Environment.NORMAL)*2);
        for(Player player : Bukkit.getOnlinePlayers()){
            Location t = player.getLocation();
            t.add(0,0.1,0);
            player.teleport(t);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(0);
        }
        p.getPlatform().despawn();

        i.teamManager.distributeTeamToPlayer();
        i.teamManager.safeTeamTeleport(0);
        n.gameScoreboard();
        i.getScoreboardManager().refreshPlayers();
    }
    @EventHandler
    public void onPlaying(PlayingEvent event){
        if(isLaunched){
            System.out.println("already launched");
        }
        isLaunched = true;
        for(Team team : i.teamManager.getTeams()){
            for(Player player : team.getPlayers().values()){
                player.setFlying(false);
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(false);
                player.setFlySpeed(0.5f);
            }
            System.out.println("HelloWorld");
            Bukkit.getScheduler().scheduleSyncDelayedTask(i, new BukkitRunnable() {
                        @Override
                        public void run() {
                            team.getPlateform().despawn();

                            Timer timer = i.tm.getTimer(PVPTimer.class);
                            timer.runTaskTimerAsynchronously(InazumaUHC.get,0,10);
                            if(p.getNether()){
                                timer = i.tm.getTimer(NetherTimer.class);
                                timer.runTaskTimerAsynchronously(InazumaUHC.get,0,10);
                            }
                        }
                    });


        }
    }
}
