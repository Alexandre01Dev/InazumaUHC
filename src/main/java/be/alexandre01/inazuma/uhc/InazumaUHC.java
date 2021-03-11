package be.alexandre01.inazuma.uhc;

import be.alexandre01.inazuma.uhc.commands.*;
import be.alexandre01.inazuma.uhc.commands.test.ChunkCommand;
import be.alexandre01.inazuma.uhc.commands.test.ForceEpisodeCommand;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.generations.NetherPortalsManager;
import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.listeners.ListenersManager;
import be.alexandre01.inazuma.uhc.listeners.game.*;
import be.alexandre01.inazuma.uhc.managers.*;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.managers.damage.InvincibilityDamager;
import be.alexandre01.inazuma.uhc.managers.player.InvisibilityInventory;
import be.alexandre01.inazuma.uhc.managers.player.PlayerMovementManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.roles.RoleManager;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.ScoreboardManager;
import be.alexandre01.inazuma.uhc.spectators.SpectatorManager;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import be.alexandre01.inazuma.uhc.timers.TimersManager;
import be.alexandre01.inazuma.uhc.timers.game.*;
import be.alexandre01.inazuma.uhc.utils.CustomExp;
import be.alexandre01.inazuma.uhc.utils.ScoreboardUtil;
import be.alexandre01.inazuma.uhc.utils.Tracker;
import be.alexandre01.inazuma.uhc.worlds.WorldGen;
import be.alexandre01.inazuma.uhc.worlds.utils.WorldUtils;
import be.alexandre01.inazuma.uhc.worlds.executors.ArrowToCenter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class InazumaUHC extends JavaPlugin {
    @Getter @Setter public static InazumaUHC get;
    public WorldGen worldGen;
    public Host host = null;
    public NetherPortalsManager npm;
    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    //MANAGER
    public ListenersManager lm;
    public TimersManager tm;
    public RoleManager rm;
    public DamageManager dm;
    public TeamManager teamManager;
    @Getter  public InvincibilityDamager invincibilityDamager;
    public SpectatorManager spectatorManager;
    @Getter private RejoinManager rejoinManager;
    public InvisibilityInventory invisibilityInventory = new InvisibilityInventory();


    //OPEN LISTENER
    public  PotionEvent potionEvent;

    public Preset p;

    public ArrowToCenter arrowToCenter;
    private ArrayList<Player> remainingPlayers;
    public String ip = "play.inazumauhc.fr";
    public boolean isHosted = true;
    public boolean autoStart = false;
    public boolean loadWorldBefore = true;
    public boolean unloadWorlds = false;
    public PlayerMovementManager playerMovementManager = new PlayerMovementManager();

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
        invincibilityDamager = new InvincibilityDamager();
        lm.addListener(invincibilityDamager);
        registerCommand("revive", new ReviveCommand("revive"));
        registerCommand("heal", new HealCommand("heal"));
        registerCommand("rulestp", new RulesTpCommand("rulestp"));
        registerCommand("addEpisode", new ForceEpisodeCommand("addEpisode"));

        potionEvent = new PotionEvent();
        lm.addListener(potionEvent);
        lm.addListener(new TeamsEvent());

        WorldUtils.patchBiomes();
        this.worldGen = new WorldGen(this);

        Scenario.initialize();
         p = new Preset(new InazumaEleven());


         //DEFAULT TIMERS LOAD
        p.pData.getTimers().add(new BordureTimer());
        p.pData.getTimers().add(new InvincibilityTimer());
        p.pData.getTimers().add(new MoveBordureTimer());
        p.pData.getTimers().add(new NetherTimer());
        p.pData.getTimers().add(new PVPTimer());
        p.pData.getTimers().add(new StabilizationTimer());
        p.pData.getTimers().add(new StartingTimer());
        p.pData.getTimers().add(new WaitingTimer());


        CustomExp.registerEntity();

        //ARROWS
        if(p.p.isArrowCalculated()){
            arrowToCenter = new ArrowToCenter();
            arrowToCenter.schedule();
        }

        if(loadWorldBefore){
            worldGen.gen();
        }

        ScoreboardUtil.initialize();

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

        this.getCommand("scenario").setExecutor(new ScenarioCommand());
        this.getCommand("start").setExecutor(new StartCommand());


        this.tm = new TimersManager();


        tm.searchPresetTimer();

        Bukkit.getWorld("world").setSpawnLocation(-176,90,-211);
        lm.searchPresetListener();

         teamManager = new TeamManager();

         spectatorManager = new SpectatorManager();

         dm = new DamageManager();

        this.rejoinManager = new RejoinManager();
        lm.addListener(rejoinManager);
        GameState gameState = new GameState();

        if(isHosted){
            host = new Host();
            this.getCommand("host").setExecutor(new HostCommand());
        }
        if(p.p.hasRoles() && rm == null){
            rm = new RoleManager();
        }
        Tracker.initialize();
        registerCommand("force", new ForceCommand("force"));

        //lm.automaticFindListener();

    }

    public void registerCommand(String commandName, Command commandClass){
        try{
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(commandName, commandClass);
        }catch (Exception ex){
            ex.printStackTrace();
        }

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
