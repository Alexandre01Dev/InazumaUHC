package be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.listeners.*;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.timers.*;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scenarios.cateyes.CatEyes;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.Cutclean;
import be.alexandre01.inazuma.uhc.scenarios.hasteyboys.HasteyBoys;
import be.alexandre01.inazuma.uhc.scenarios.timber.Timber;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Jujutsu_Kaizen extends PresetData implements IPreset{

    public Jujutsu_Kaizen(){
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
    public String getPackageName() {
        return "normal";
    }

    @Override
    public ArrayList<Listener> getListeners() {
        if(listeners.isEmpty()){
            listeners.add(new PlayerEvent());
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
            timers.add(new BordureTimer());
            timers.add(new MoveBordureTimer());
            timers.add(new InvincibilityTimer());
            timers.add(new DelayedTimeChangeTimer(DelayedTimeChangeTimer.State.DAY,1,20*5));
        }
        return timers;
    }

    @Override
    public ArrayList<Scenario> getScenarios() {
        if(scenarios.isEmpty()){
            System.out.println("SCENARIO CREATE!");
            scenarios.add(new Cutclean());
            scenarios.add(new CatEyes());
            scenarios.add(new HasteyBoys());
            scenarios.add(new Timber());
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
        return null;
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
