package be.alexandre01.inazuma.uhc.presets.normal;

import be.alexandre01.inazuma.uhc.custom_events.state.PlayingEvent;
import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.listeners.*;
import be.alexandre01.inazuma.uhc.presets.normal.timers.NetherTimer;
import be.alexandre01.inazuma.uhc.presets.normal.timers.PVPTimer;
import be.alexandre01.inazuma.uhc.presets.normal.timers.StartingTimer;
import be.alexandre01.inazuma.uhc.presets.normal.timers.WaitingTimer;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.Cutclean;
import be.alexandre01.inazuma.uhc.scoreboard.IScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.ObjectiveSign;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Normal implements IPreset {
    private boolean hasScenario = true;
    private ArrayList<Scenario> scenarios = new ArrayList<>();
    private ArrayList<Listener> listeners = new ArrayList<>();
    private ArrayList<Timer> timers = new ArrayList<>();
    private Plateform plateform;
    public static int lastModifier = 0;
    public static String timerText = "";
    public static String timerValue = "";
    public static String bordureValue ="";
    public static String bordureText ="";
    public static String netherText ="";
    public static String netherValue ="";
    IPersonalScoreBoard i;

    @Override
    public String getName() {
        return "Normal";
    }

    @Override
    public String getPackageName() {
        return "normal";
    }

    @Override
    public ArrayList<Listener> getListeners() {
        if(listeners.isEmpty()){
            listeners.add(new JoinEvent());
            listeners.add(new StateEvent(this));
            listeners.add(new ChunkEvent());
            listeners.add(new TimerEvent());
            listeners.add(new TeamsEvent());

        }
        return listeners;
    }

    @Override
    public ArrayList<Timer> getTimers() {
        if(timers.isEmpty()){
            timers.add(new WaitingTimer());
            timers.add(new StartingTimer());
            timers.add(new PVPTimer());
            timers.add(new NetherTimer());
        }
        return timers;
    }

    @Override
    public ArrayList<Scenario> getScenarios() {
        if(scenarios.isEmpty()){
            scenarios.add(new Cutclean());
        }

        return scenarios;
    }

    @Override
    public boolean getNether() {
        return true;
    }

    @Override
    public int getWaitingTime() {
        return 120;
    }

    @Override
    public int getMinPlayerToStart() {
        return 2;
    }

    @Override
    public int getTotalTime() {
        return 60*60;
    }

    @Override
    public int getPVPTime() {
        return 40;//60*2;
    }

    @Override
    public int getNetherTime() {
        return 50;
    }

    @Override
    public int getBordureTime() {
        return 90;
    }

    @Override
    public int startBordure() {
        return 60*20*60*60;
    }

    @Override
    public int endBordure() {
        return (60*20*60+20)+(20*20*60);
    }

    @Override
    public Plateform getPlatform() {
        if(plateform != null){
            return plateform;
        }

        plateform = new Plateform(Plateform.PlateformType.CUBE,0,140,0);
        plateform.setCube(20,20,20);
        return plateform;
    }

    @Override
    public int getChunksArea() {
        return 300;
    }

    @Override
    public int nerfPotForce() {
        return 0;
    }

    @Override
    public int nerfPotResistance() {
        return 0;
    }

    @Override
    public int getPlayerSize() {
        return 20;
    }

    @Override
    public int getTeamSize() {
        return 1;
    }
    public void waitingScoreboard(){
        i = player -> {
            PersonalScoreboard ps = new PersonalScoreboard(player);
            ps.setIScore(new IScoreBoard() {
                @Override
                public void lines(String ip, ObjectiveSign objectiveSign) {
                    objectiveSign.setDisplayName("§8»§6§lInazumaUHC§8«");

                    objectiveSign.setLine(0, "§l§8»§8§m------------§l§8«§r");
                    objectiveSign.setLine(2,"§f§l» §a§lUHC§a CLASSIC §f§l«");
                    objectiveSign.setLine(4, "§r§l§8»§8§m------------§l§8«");
                    objectiveSign.setLine(6, "§7Joueurs §l» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e"+getPlayerSize());
                    objectiveSign.setLine(7, "§7 §l» §cEn attentes de joueurs§e");
                    objectiveSign.setLine(8, "§7 §l» §e§l"+Normal.lastModifier+"s.");
                    objectiveSign.setLine(9, "§r§l§8»§8§m------------§l§8«§r");
                    World world = player.getWorld();
                    int borderSize = Preset.instance.p.getBorderSize(world.getEnvironment());
                    objectiveSign.setLine(10,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);
                    objectiveSign.setLine(11, "§l§8»§8§m------------§l§8«");
                    if(hasScenario){
                        if(scenarios.size()> 1){
                            objectiveSign.setLine(13, "§7Scénarios §l» §8/scenario");
                        }else {
                            objectiveSign.setLine(13, "§7Scénario §l» §a"+getScenarios().get(0).getName());
                        }

                    }else {
                        objectiveSign.setLine(13, "§7Scénario §l» §cAucun");
                    }

                    objectiveSign.setLine(14, "§l§8»§8§m------------§l§8« ");
                    objectiveSign.setLine(15, ip);

                    objectiveSign.updateLines();

                }
            });
            return ps;
        };

    }
    public void gameScoreboard(){
        i = player -> {
            PersonalScoreboard ps = new PersonalScoreboard(player);
            ps.setIScore(new IScoreBoard() {
                @Override
                public void lines(String ip, ObjectiveSign objectiveSign) {
                    objectiveSign.setDisplayName("§8»§6§lInazumaUHC§8«");

                    objectiveSign.setLine(0, "§l§8»§8§m------------§l§8«§r");
                    objectiveSign.setLine(2,"§f§l» §a§lUHC§a CLASSIC §f§l«");
                    objectiveSign.setLine(4, "§r§l§8»§8§m------------§l§8«");
                    objectiveSign.setLine(6, "§7Joueurs §l» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e"+getPlayerSize());
                    objectiveSign.setLine(7, "§r§l§8»§8§m------------§l§8«§f");
                    objectiveSign.setLine(8, "§7"+Normal.timerText+" §l» §e" + Normal.timerValue);
                    if(getNether()){
                        objectiveSign.setLine(9,"§7"+Normal.netherText+" §l» §e" + Normal.netherValue);
                    }
                    objectiveSign.setLine(10,"§7"+Normal.bordureText+" §l» §e" + Normal.bordureValue);
                    objectiveSign.setLine(16, "§r§l§8»§8§m------------§l§8«§r");
                    World world = player.getWorld();
                    int borderSize = Preset.instance.p.getBorderSize(world.getEnvironment());
                    objectiveSign.setLine(17,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);

                    Location l1 =  player.getLocation();
                    l1.setPitch(0);
                    l1.setY(0);
                    Vector direction = l1.getDirection();
                    Location l2 =  world.getSpawnLocation();
                    l2.setY(0);
                    Vector loc = l2.subtract(l1).toVector();

                    double angleDir = (Math.atan2(loc.getZ(),loc.getX()) / 2 / Math.PI * 360 + 360) % 360;

                    double angleLook = (Math.atan2(direction.getZ(),direction.getX()) / 2 / Math.PI * 360 + 360) % 360;

                    double angle = (angleDir - angleLook + 360) % 360;
                    String c = "§l•";
                    if(l1.distance(l2)> 20){
                        if(angle >= 337.5 && angle <= 360 || angle >= 0 && angle < 22.5){
                            c = "⬆";
                        }else {
                            if(angle >= 22.5 && angle < 67.5){
                                c = "⬈";
                            }else {
                                if(angle >= 67.5 && angle < 112.5){
                                    c = "➡";
                                }else {
                                    if(angle >= 112.5 && angle < 157.5){
                                        c = "⬊";
                                    }else {
                                        if(angle >= 157.5 && angle < 202.5){//180
                                            c = "⬇";
                                        }else {
                                            if(angle >= 202.5 && angle < 247.5){
                                                c = "⬋";
                                            }else {
                                                if(angle >= 247.5 && angle < 292.5){
                                                    c = "§l⬅";
                                                }else {
                                                    if(angle >= 292.5 && angle < 337.5){
                                                        c = "⬉";
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                    objectiveSign.setLine(18,"§7Centre §l» §e "+ c);
                    objectiveSign.setLine(19, "§l§8»§8§m------------§l§8«");
                    if(hasScenario){

                        if(scenarios.size()> 1){
                            objectiveSign.setLine(20, "§7Scénarios §l» §8/scenario");
                        }else {
                            objectiveSign.setLine(20, "§7Scénario §l» §a"+getScenarios().get(0).getName());
                        }
                    }else {
                        objectiveSign.setLine(20, "§7Scénario §l» §cAucun");
                    }

                    objectiveSign.setLine(21, "§l§8»§8§m------------§l§8« ");
                    objectiveSign.setLine(22, ip);

                    objectiveSign.updateLines();
                }
            });
            return ps;
        };

    }
    @Override
    public PersonalScoreboard getScoreboard(Player player) {
        return i.actual(player);
    }

    @Override
    public int getBorderSize(World.Environment environment) {
        int i = 0;
        switch (environment){
            case NORMAL:
                i = 250;
                break;
            case NETHER:
                i = 150;
                break;
        }
        return i;
    }



    @Override
    public String getGeneratorSettings(World.Environment environment) {
        String s = "";
        switch (environment){
            case NORMAL:
                s = "";
                break;
            case NETHER:
                s = "";
                break;
        }
        return s;
    }

    @Override
    public String getRandomTickSpeed(World.Environment environment) {
        String s = "";
        switch (environment){
            case NORMAL:
                s = "250";
                break;
            case NETHER:
                s = "20";
                break;
        }
        return s;
    }

    @Override
    public String getNaturalRegeneration(World.Environment environment) {
        String s = "";
        switch (environment){
            case NORMAL:
                s = "false";
                break;
            case NETHER:
                s = "false";
                break;
        }
        return s;
    }


}

interface IPersonalScoreBoard{
    public PersonalScoreboard actual(Player player);
}
