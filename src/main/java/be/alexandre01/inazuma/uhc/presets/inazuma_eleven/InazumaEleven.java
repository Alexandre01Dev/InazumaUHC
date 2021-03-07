package be.alexandre01.inazuma.uhc.presets.inazuma_eleven;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Solo;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners.*;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius.*;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.*;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo.Byron;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers.*;
import be.alexandre01.inazuma.uhc.scenarios.betazombie.BetaZombie;
import be.alexandre01.inazuma.uhc.scenarios.cateyes.CatEyes;
import be.alexandre01.inazuma.uhc.scenarios.cutclean.Cutclean;
import be.alexandre01.inazuma.uhc.scenarios.diamondlimit.DiamondLimit;
import be.alexandre01.inazuma.uhc.scenarios.hasteyboys.HasteyBoys;
import be.alexandre01.inazuma.uhc.scenarios.rodless.RodLess;
import be.alexandre01.inazuma.uhc.scenarios.timber.Timber;
import be.alexandre01.inazuma.uhc.scenarios.trashpotion.TrashPotion;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InazumaEleven extends PresetData implements IPreset{
    public static String totalTimeValue ="";

    public InazumaEleven(){
        //DefaultSettings Value
        generatorSettings = new String[]{"", ""};
        hasNether = false;
        minPlayerToStart = 2;
        playerSize = 30;
        totalTime = 60*60;
        teamSize = 1;
        pvpTime = 1*30;
        netherTime = 55*60;
        bordureTime = 60*60;
        borderSize = 500;
        borderSizeNether = 150;
        endBordureTime = 60*15;
        endBordureSize = 250*2;
        scenarios = null;
        getScenarios();
        //INITIALIZE ROLESCATEGORIES
        new Raimon("Équipe Raimon","§6");
        new Alius("Académie-Alius","§5");
        new Solo("Solo","§c");
        //INITIALIZE ROLES
      //  new Xavier();
        new Mark();
        new Darren();
        new Xavier();
    }

    @Override
    public boolean autoJoinWorld() {
        return false;
    }

    @Override
    public String getName() {
        return "InazumaEleven";
    }

    @Override
    public String prefixName() {
        return "§3Inazuma§8-§3Eleven§8»§7 ";
    }

    @Override
    public boolean hasRoles() {
        return true;
    }

    @Override
    public String getPackageName() {
        return "inazuma_eleven";
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
            timers.add(new EpisodeTimer());
            timers.add(new EpisodeTimeTimer());
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
            scenarios.add(Timber.class);
            scenarios.add(RodLess.class);
            scenarios.add(BetaZombie.class);
            scenarios.add(TrashPotion.class);
            scenarios.add(DiamondLimit.class);
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
        return waitingTime;
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
