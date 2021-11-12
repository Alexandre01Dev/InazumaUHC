package be.alexandre01.inazuma.uhc;

import be.alexandre01.inazuma.uhc.commands.*;
import be.alexandre01.inazuma.uhc.commands.test.ChunkCommand;
import be.alexandre01.inazuma.uhc.commands.test.ForceEpisodeCommand;
import be.alexandre01.inazuma.uhc.commands.test.InaReload;
import be.alexandre01.inazuma.uhc.commands.test.StuffMeetupCommand;
import be.alexandre01.inazuma.uhc.config.Config;
import be.alexandre01.inazuma.uhc.config.CustomExceptionHandler;
import be.alexandre01.inazuma.uhc.config.Messages;
import be.alexandre01.inazuma.uhc.generations.NetherPortalsManager;
import be.alexandre01.inazuma.uhc.handler.GlobalMoveHandler;
import be.alexandre01.inazuma.uhc.handler.RessourcePackHandler;
import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.host.gui.WorkingPlace;
import be.alexandre01.inazuma.uhc.listeners.ListenersManager;
import be.alexandre01.inazuma.uhc.listeners.game.*;
import be.alexandre01.inazuma.uhc.listeners.host.ClickInventory;
import be.alexandre01.inazuma.uhc.listeners.host.CloseInventory;
import be.alexandre01.inazuma.uhc.managers.*;
import be.alexandre01.inazuma.uhc.managers.chat.ChatManager;
import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.managers.damage.InvincibilityDamager;
import be.alexandre01.inazuma.uhc.managers.damage.NoFallDamager;
import be.alexandre01.inazuma.uhc.managers.player.InvisibilityInventory;
import be.alexandre01.inazuma.uhc.managers.player.PlayerMovementManager;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.modules.ModuleLoader;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.roles.RoleManager;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.ScoreboardManager;
import be.alexandre01.inazuma.uhc.spectators.SpectatorManager;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.teams.TeamManager;
import be.alexandre01.inazuma.uhc.timers.TimersManager;
import be.alexandre01.inazuma.uhc.timers.game.*;
import be.alexandre01.inazuma.uhc.utils.*;
import be.alexandre01.inazuma.uhc.utils.fastinv.FastInv;
import be.alexandre01.inazuma.uhc.worlds.WorldGen;
import be.alexandre01.inazuma.uhc.worlds.utils.WorldUtils;
import be.alexandre01.inazuma.uhc.worlds.executors.ArrowToCenter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

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
    public ChatManager cm;
    public TimersManager tm;
    public RoleManager rm;
    public DamageManager dm;
    public TeamManager teamManager;
    @Getter  public InvincibilityDamager invincibilityDamager;
    @Getter  public NoFallDamager noFallDamager;
    public SpectatorManager spectatorManager;
    @Getter private RejoinManager rejoinManager;
    public InvisibilityInventory invisibilityInventory = new InvisibilityInventory();
    @Getter
    private GlobalMoveHandler globalMoveHandler = new GlobalMoveHandler();


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

    public ModuleLoader moduleLoader;

    public boolean debugMode = true;

    @Override
    public void onEnable() {
        try {

        //Instance
        InazumaUHC.get = this;
        //TOCHANGE
        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler());
        Config.createDir(getDataFolder().getAbsolutePath()+"/modules");

        //OBJECTIVES && TEAM RESET
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for(Team team : scoreboard.getTeams()){
            team.unregister();
        }
        for(Objective objective : scoreboard.getObjectives()){
            objective.unregister();
        }

        //GameState
        remainingPlayers = new ArrayList<>();
        //Execute config
        Config config = new Config();
        config.load(this);
        System.out.println(Messages.get("error.stopped"));
        this.getCommand("lChunk").setExecutor(new ChunkCommand());

        this.cm = new ChatManager();
        this.lm = new ListenersManager();
        lm.addListener(new PlayerUUIDConverter());
        lm.addListener(new WorldGenEvent());
        lm.addListener(new NetherEvent());
        lm.addListener(new PlayerEvent());
        lm.addListener(new WorldEvent());
        lm.addListener(new TimerEvent());

        getServer().imanity().registerPacketHandler(this,new RessourcePackHandler());
        //lm.addListener(new BoatEvent());
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


        playerMovementManager.init();
        lm.addListener(new ProtectionEvent());
        lm.addListener(new StateEvent());
        invincibilityDamager = new InvincibilityDamager();
        noFallDamager = new NoFallDamager();
        lm.addListener(noFallDamager);
        lm.addListener(invincibilityDamager);
        registerCommand("revive", new ReviveCommand("revive"));
        registerCommand("heal", new HealCommand("heal"));
        registerCommand("rulestp", new RulesTpCommand("rulestp"));
        registerCommand("addEpisode", new ForceEpisodeCommand("addEpisode"));
        registerCommand("say", new SayCommand("say"));
        registerCommand("groupe", new GroupCommand("groupe"));
        registerCommand("ireload", new InaReload("ireload"));
        potionEvent = new PotionEvent();
        lm.addListener(potionEvent);
        lm.addListener(new TeamsEvent());

        WorldUtils.patchBiomes();
        this.worldGen = new WorldGen(this);

        Scenario.initialize();

        p = new Preset();

        Module normal = new Module();
        normal.setModuleName("Normal");
        normal.setPresetPath("be.alexandre01.inazuma.uhc.presets.normal.Normal");
        normal.setVersion("Beta 1.0");
        normal.setAuthors(new String[]{"Alexandre01"});
        normal.setMaterial(Material.APPLE);
        normal.setDescription("Mode de jeu normal");
        normal.setChild(null);
        normal.setPresetClass(Normal.class);
        p.add("Normal",normal);

        moduleLoader = new ModuleLoader(this);

        String defaultModule = getConfig().getString("config.default-module");

        Collection<Module> ms = Preset.instance.modules.values().stream().filter(module -> module.getModuleName().equals(defaultModule)).collect(Collectors.toList());

        if(!ms.isEmpty()){
            p.set(new ArrayList<>(ms).get(0));
        }else {
            p.set(normal);
        }




        PatchedEntity.init();
        CustomExp.registerEntity();
        CustomSkeleton.registerEntity();
        CustomBoat.registerEntity();
        //ARROWS
        this.tm = new TimersManager();

        onLoadPreset();

        if(loadWorldBefore){
            worldGen.gen();
        }

        ScoreboardUtil.initialize();

            System.out.println("Scheduled GO to");
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();
            System.out.println("Scheduled SET to");
        this.getCommand("scenario").setExecutor(new ScenarioCommand());
        this.getCommand("start").setExecutor(new StartCommand());
        this.getCommand("doc").setExecutor(new DocCommand());
        this.getCommand("debug").setExecutor(new DebugCommand());
        this.getCommand("pack").setExecutor(new PackCommand());
        this.getCommand("mumble").setExecutor(new MumbleCommand());
        //this.getCommand("rules").setExecutor(new RulesCommand());

        registerCommand("pregen", new PregenCommand("pregen"));




        Bukkit.getWorld("world").setSpawnLocation(1001,33,1001);

         teamManager = new TeamManager();

         spectatorManager = new SpectatorManager();

         dm = new DamageManager();

        this.rejoinManager = new RejoinManager();
        lm.addListener(rejoinManager);
        GameState gameState = new GameState();







        Tracker.initialize();
        registerCommand("force", new ForceCommand("force"));
        registerCommand("compo", new CompoCommand("compo"));
        registerCommand("composee", new CompoSeeCommand("composee"));
        registerCommand("ina",new BaseCommand("ina","ina"));
        registerCommand("ina",new StuffMeetupCommand("stuffmeetup"));
        registerCommand("invsee",new InvSeeCommand( "invsee"));
        registerCommand("module", new ModuleCommand("module"));
        registerCommand("changerole", new ChangeRoleCommand("changerole"));
        registerCommand("hrtp", new HRTPCommand("hrtp"));
        registerCommand("mutechat", new MuteChatCommand("mutechat"));
        //lm.automaticFindListener();
        }catch (Exception e){

            e.printStackTrace();
            if(debugMode){
                System.out.println("Debug");
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(player.hasPermission("debug.view")){
                       player.sendMessage("§cERREUR >> "+e.getMessage()+" §4||§c "+ e.getClass().getSimpleName());
                        for(StackTraceElement s : e.getStackTrace()){
                           player.sendMessage("----->");
                           player.sendMessage("§cERREUR DANS>> §f" +s.getClassName()+":"+s.getMethodName()+":"+s.getLineNumber());
                        }
                    }
                }
            }
        }
    }

    public void onLoadPreset(){
        //DEFAULT TIMERS LOAD
       /* p.pData.getTimers().add(new BordureTimer());
        p.pData.getTimers().add(new InvincibilityTimer());
        p.pData.getTimers().add(new MoveBordureTimer());
        p.pData.getTimers().add(new NetherTimer());
        p.pData.getTimers().add(new PVPTimer());
        p.pData.getTimers().add(new StabilizationTimer());
        p.pData.getTimers().add(new StartingTimer());
        p.pData.getTimers().add(new WaitingTimer());*/

        lm.clearPresetListeners();
        lm.searchPresetListener();
        if(InazumaUHC.get.scoreboardManager != null){
            InazumaUHC.get.scoreboardManager.setPreset(Preset.instance.p);
            if(!InazumaUHC.get.scoreboardManager.getScoreboards().isEmpty()){
                //InazumaUHC.get.scoreboardManager.refreshPlayers();
            }

        }

        if(!Bukkit.getOnlinePlayers().isEmpty()){
            for(Player player : Bukkit.getOnlinePlayers()){
                //player.closeInventory();
            }
        }
        if(p.p.isArrowCalculated()){
            if(arrowToCenter == null){
            arrowToCenter = new ArrowToCenter();
            arrowToCenter.schedule();
            }else {
                arrowToCenter.setPreset(Preset.instance.p);
            }
        }

       // tm.clear();

        p.pData.getTimers().add(new BordureTimer());
        p.pData.getTimers().add(new InvincibilityTimer());
        p.pData.getTimers().add(new MoveBordureTimer());
        p.pData.getTimers().add(new NetherTimer());
        p.pData.getTimers().add(new PVPTimer());
        p.pData.getTimers().add(new StabilizationTimer());
        p.pData.getTimers().add(new StartingTimer());
        p.pData.getTimers().add(new WaitingTimer());
        p.pData.getTimers().add(new EpisodeTimer());
        p.pData.getTimers().add(new EpisodeTimeTimer());

        tm.searchPresetTimer();


        if(isHosted){
            lm.removeListener(ClickInventory.class);
            lm.removeListener(CloseInventory.class);
            if(host != null){
                HashMap<Player, FastInv> playersCurrentInv = host.getInstances();
                HashMap<Player, WorkingPlace> playersCurrentWP = host.getWorkingPlaces();
                host = new Host();
                System.out.println("YES0");
                for(Player player : Bukkit.getOnlinePlayers()){
                    System.out.println("YES1");
                    if(playersCurrentInv.containsKey(player)){
                        System.out.println("YES2");
                        FastInv fastInv = playersCurrentInv.get(player);
                        if(playersCurrentWP.containsKey(player)){
                            System.out.println("YES3");
                            WorkingPlace workingPlace =  host.getMenu(playersCurrentWP.get(player).getClass());
                            workingPlace.addInstance(player,fastInv);
                            host.getWorkingPlaces().put(player,workingPlace);
                            host.getInstances().put(player,fastInv);
                        }
                    }
                }

            }else {
                host = new Host();
                this.getCommand("host").setExecutor(new HostCommand());
            }
        }



        if(p.p.hasRoles() && rm == null){
            rm = new RoleManager();
        }

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
