package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.custom_events.state.PlayingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.PreparingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.StartingEvent;
import be.alexandre01.inazuma.uhc.custom_events.state.WaitingEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.scoreboards.GameScoreboard;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.scoreboards.PreparingScoreboard;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.scoreboards.WaitingScoreboard;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers.*;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.teams.Team;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class StateEvent implements Listener {
    private InazumaEleven n;
    private be.alexandre01.inazuma.uhc.InazumaUHC i;
    private IPreset p;
    private boolean isLaunched = false;
    public StateEvent(InazumaEleven inazuma){
        this.n = inazuma;
        this.i = be.alexandre01.inazuma.uhc.InazumaUHC.get;
        this.p = Preset.instance.p;
    }
    @EventHandler
    public void onPreparing(PreparingEvent event){
        System.out.println("PreparingEvent called");
        PreparingScoreboard preparingScoreboard = new PreparingScoreboard(n);
        preparingScoreboard.setScoreboard();
        i.getScoreboardManager().refreshPlayers();
    }
    @EventHandler
    public void onWaiting(WaitingEvent event){
        System.out.println("Waitingevent called");
        WaitingScoreboard waitingScoreboard = new WaitingScoreboard(n);
        waitingScoreboard.setScoreboard();
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
            player.setWalkSpeed(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0,false,false), true);
        }



        i.teamManager.distributeTeamToPlayer();
        i.teamManager.safeTeamTeleport(0);
        GameScoreboard gameScoreboard = new GameScoreboard(n);
        gameScoreboard.setScoreboard();
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
                player.setFlySpeed(0.2f);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(i, new BukkitRunnable() {
                @Override
                public void run() {
                    team.getPlateform().despawn();
                }
            });
        }
            Bukkit.getScheduler().scheduleSyncDelayedTask(i, new BukkitRunnable() {
                        @Override
                        public void run() {
                            Timer timer = i.tm.getTimer(PVPTimer.class);
                            timer.runTaskTimerAsynchronously(be.alexandre01.inazuma.uhc.InazumaUHC.get,0,10);
                            if(p.getNether()){
                                timer = i.tm.getTimer(NetherTimer.class);
                                timer.runTaskTimerAsynchronously(be.alexandre01.inazuma.uhc.InazumaUHC.get,0,10);
                            }
                            timer = i.tm.getTimer(BordureTimer.class);
                            timer.runTaskTimerAsynchronously(be.alexandre01.inazuma.uhc.InazumaUHC.get,0,10);

                            timer = i.tm.getTimer(InvincibilityTimer.class);
                            timer.runTaskTimerAsynchronously(be.alexandre01.inazuma.uhc.InazumaUHC.get,0,10);

                            timer = i.tm.getTimer(EpisodeTimer.class);
                            timer.runTaskTimerAsynchronously(be.alexandre01.inazuma.uhc.InazumaUHC.get,0,20*10);
                        }
                    });


            i.rm.distributeRole(i.getRemainingPlayers());
            for(Role role : Role.getRoles()){
                role.spoilRole();
                role.giveItem();
            }
            Role.isDistributed = true;
    }
}
