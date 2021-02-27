package be.alexandre01.inazuma.uhc.presets.normal;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.normal.listeners.*;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scenarios.cateyes.CatEyes;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.Cutclean;
import be.alexandre01.inazuma.uhc.scenarios.hasteyboys.HasteyBoys;
import be.alexandre01.inazuma.uhc.scenarios.timber.Timber;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Normal extends PresetData implements IPreset {

    public Normal(){
        //DefaultSettings Value
        generatorSettings = new String[]{"", ""};
        hasNether = true;
        minPlayerToStart = 2;
        playerSize = 30;
        totalTime = 60*60;
        teamSize = 1;
        pvpTime = 60*20;
        netherTime = 55*60;
        bordureTime = 60*60;
        borderSize = 500;
        borderSizeNether = 150;
        endBordureTime = 60*15;
        endBordureSize = 250*2;
        scenarios = null;
        getScenarios();
    }

    @Override
    public boolean autoJoinWorld() {
        return false;
    }

    @Override
    public String getName() {
        return "Normal";
    }

    @Override
    public String prefixName() {
        return "§aClassico»§7 ";
    }

    @Override
    public boolean hasRoles() {
        return false;
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
        }
        return timers;
    }

    @Override
    public ArrayList<Class<?>> getScenarios() {
        if(scenarios == null){
            scenarios = new ArrayList<>();
            System.out.println("SCENARIO CREATE!");
            scenarios.add(Cutclean.class);
            scenarios.add(CatEyes.class);
            scenarios.add(HasteyBoys.class);
            scenarios.add(Timer.class);
        }
        System.out.println("SCENARIO RETURN!");
        return scenarios;
    }

    @Override
    public ArrayList<IPersonalScoreBoard> getScoreboards() {
        return null;
    }

    @Override
    public boolean getNether() {
        return hasNether;
    }

    @Override
    public boolean canRespawnOnRejoin() {
        return true;
    }

    @Override
    public boolean isInvicible() {
        return isInvisible;
    }

    @Override
    public int getWaitingTime() {
        return 120;
    }

    @Override
    public int getInvisibleTime() {
        return invisibilityTime*60;
    }

    @Override
    public int getMinPlayerToStart() {
        return minPlayerToStart;
    }

    @Override
    public int getTotalTime() {
        return totalTime;
    }

    @Override
    public int getPVPTime() {
        return pvpTime;//60*2;
    }

    @Override
    public int getNetherTime() {
        return netherTime;
    }

    @Override
    public int getBordureTime() {
        return 60*60;
    }

    @Override
    public int getEndBordure() {
        return 250*2;
    }
    @Override
    public int getEndBordureTime() {
        return 60*15;
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
        return playerSize;
    }

    @Override
    public int getTeamSize() {
        return teamSize;
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
                i = borderSize;
                break;
            case NETHER:
                i = borderSizeNether;
                break;
        }
        return i;
    }



    @Override
    public String getGeneratorSettings(World.Environment environment) {
        String s = "";
        switch (environment){
            case NORMAL:
                s = generatorSettings[0];
                break;
            case NETHER:
                s = generatorSettings[1];
                break;
        }
        return s;
    }

    @Override
    public String getRandomTickSpeed(World.Environment environment) {
        String s = "";
        switch (environment){
            case NORMAL:
                s = "1";
                break;
            case NETHER:
                s = "2";
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

    @Override
    public boolean isArrowCalculated() {
        return true;
    }

    @Override
    public HashMap<UUID, String> getArrows() {
        return arrows;
    }
}



