package be.alexandre01.inazuma.uhc.presets.speeduhc;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class SpeedUHC implements IPreset {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public ArrayList<Listener> getListeners() {
        return null;
    }

    @Override
    public ArrayList<Timer> getTimers() {
        return null;
    }


    @Override
    public ArrayList<Scenario> getScenarios() {
        return null;
    }

    @Override
    public boolean getNether() {
        return false;
    }

    @Override
    public int getWaitingTime() {
        return 0;
    }


    @Override
    public int getMinPlayerToStart() {
        return 0;
    }

    @Override
    public int getTotalTime() {
        return 0;
    }

    @Override
    public int getPVPTime() {
        return 0;
    }

    @Override
    public int getNetherTime() {
        return 0;
    }

    @Override
    public int getBordureTime() {
        return 0;
    }

    @Override
    public int startBordure() {
        return 0;
    }

    @Override
    public int endBordure() {
        return 0;
    }

    @Override
    public Plateform getPlatform() {
        return null;
    }

    @Override
    public int getChunksArea() {
        return 0;
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
        return 0;
    }

    @Override
    public int getTeamSize() {
        return 0;
    }

    @Override
    public PersonalScoreboard getScoreboard(Player player) {
        return null;
    }

    @Override
    public int getBorderSize(World.Environment environment) {
        return 0;
    }

    @Override
    public String getGeneratorSettings(World.Environment environment) {
        return null;
    }

    @Override
    public String getRandomTickSpeed(World.Environment environment) {
        return null;
    }

    @Override
    public String getNaturalRegeneration(World.Environment environment) {
        return null;
    }
}
