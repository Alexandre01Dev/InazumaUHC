package be.alexandre01.inazuma.uhc;

import be.alexandre01.inazuma.uhc.commands.HostCommand;
import be.alexandre01.inazuma.uhc.commands.ScenarioCommand;
import be.alexandre01.inazuma.uhc.commands.StartCommand;
import be.alexandre01.inazuma.uhc.commands.test.ChunkCommand;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.generations.NetherPortalsManager;
import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.listeners.ListenersManager;
import be.alexandre01.inazuma.uhc.listeners.game.*;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.Jujutsu_Kaizen;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.roles.SatoruGojo;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.scoreboard.ScoreboardManager;
import be.alexandre01.inazuma.uhc.spectators.SpectatorManager;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import be.alexandre01.inazuma.uhc.timers.TimersManager;
import be.alexandre01.inazuma.uhc.worlds.WorldGen;
import be.alexandre01.inazuma.uhc.worlds.WorldUtils;
import be.alexandre01.inazuma.uhc.worlds.executors.ArrowToCenter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class InazumaUHC extends JavaPlugin {
    public static InazumaUHC get;
    public WorldGen worldGen;
    public Host host = null;
    public NetherPortalsManager npm;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    public ListenersManager lm;
    public TimersManager tm;
    public TeamManager teamManager;
    public SpectatorManager spectatorManager;

    public ArrowToCenter arrowToCenter;
    private ArrayList<Player> remainingPlayers;
    public String ip = "play.inazumauhc.fr";
    public boolean isHosted = true;
    public boolean autoStart = false;
    @Override
    public void onEnable() {
        //Instance
        InazumaUHC.get = this;
        //TOCHANGE

        //GameState
        remainingPlayers = new ArrayList<>();
        //Execute config
        Config config = new Config();
        config.load(this);
        System.out.println(Messages.get("error.stopped"));
        this.getCommand("lChunk").setExecutor(new ChunkCommand());


        this.lm = new ListenersManager();
        lm.addListener(new WorldGenEvent());
        lm.addListener(new NetherEvent());
        lm.addListener(new PlayerEvent());
        for(World world : Bukkit.getWorlds()){
            for(Entity entity : world.getEntities()){
                if(entity instanceof LivingEntity){
                    if(!(entity instanceof Player)){
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.setHealth(0);
                    }
                }
            }
        }
        lm.addListener(new ProtectionEvent());
        lm.addListener(new StateEvent());
        lm.addListener(new TeamsEvent());
        WorldUtils.patchBiomes();
        this.worldGen = new WorldGen(this);
        Preset p = new Preset(new Jujutsu_Kaizen());
        if(p.p.isArrowCalculated()){
            arrowToCenter = new ArrowToCenter();
            arrowToCenter.schedule();
        }



        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

        this.getCommand("scenario").setExecutor(new ScenarioCommand());
        this.getCommand("start").setExecutor(new StartCommand());


        this.tm = new TimersManager();


        tm.searchPresetTimer();


        lm.searchPresetListener();

         teamManager = new TeamManager();

         spectatorManager = new SpectatorManager();

        GameState gameState = new GameState();

        if(isHosted){
            host = new Host();
            this.getCommand("host").setExecutor(new HostCommand());
        }

        //lm.automaticFindListener();

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public ArrayList<Player> getRemainingPlayers() {
        return remainingPlayers;
    }
}
