package be.alexandre01.inazuma.uhc;

import be.alexandre01.inazuma.uhc.commands.ScenarioCommand;
import be.alexandre01.inazuma.uhc.commands.StartCommand;
import be.alexandre01.inazuma.uhc.commands.test.ChunkCommand;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.generations.NetherPortalsManager;
import be.alexandre01.inazuma.uhc.listeners.ListenersManager;
import be.alexandre01.inazuma.uhc.listeners.game.NetherEvent;
import be.alexandre01.inazuma.uhc.listeners.game.PlayerEvent;
import be.alexandre01.inazuma.uhc.listeners.game.WorldGenEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.scoreboard.ScoreboardManager;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import be.alexandre01.inazuma.uhc.timers.TimersManager;
import be.alexandre01.inazuma.uhc.worlds.WorldGen;
import be.alexandre01.inazuma.uhc.worlds.WorldUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class InazumaUHC extends JavaPlugin {
    public static InazumaUHC get;
    public WorldGen worldGen;
    public NetherPortalsManager npm;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    public ListenersManager lm;
    public TimersManager tm;
    public TeamManager teamManager;
    public String ip = "play.inazumauhc.fr";
    @Override
    public void onEnable() {
        //Instance
        InazumaUHC.get = this;
        //TOCHANGE

        //GameState

        //Execute config
        Config config = new Config();
        config.load(this);
        System.out.println(Messages.get("error.stopped"));
        this.getCommand("lChunk").setExecutor(new ChunkCommand());


        this.lm = new ListenersManager();
        lm.addListener(new WorldGenEvent());
        lm.addListener(new NetherEvent());
        lm.addListener(new PlayerEvent());
        WorldUtils.patchBiomes();
        this.worldGen = new WorldGen(this);
        Preset p = new Preset(new Normal());

        this.npm = new NetherPortalsManager();


        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

        this.getCommand("scenario").setExecutor(new ScenarioCommand());
        this.getCommand("start").setExecutor(new StartCommand());

        this.tm = new TimersManager();


        tm.searchPresetTimer();


        lm.searchPresetListener();

         teamManager = new TeamManager();
        GameState gameState = new GameState();
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
}
