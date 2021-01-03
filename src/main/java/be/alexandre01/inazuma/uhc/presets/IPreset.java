package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;


public interface IPreset {
    String getName();

    String getPackageName();

    ArrayList<Listener> getListeners();

    ArrayList<Timer> getTimers();

    ArrayList<Scenario> getScenarios();

    boolean getNether();

    int getWaitingTime();

    int getMinPlayerToStart();

    int getTotalTime();

    int getPVPTime();

    int getNetherTime();

    int getBordureTime();

    int startBordure();

    int endBordure();

    Plateform getPlatform();

    int getChunksArea();

    int nerfPotForce();

    int nerfPotResistance();

    int getPlayerSize();

    int getTeamSize();

    PersonalScoreboard getScoreboard(Player player);

    int getBorderSize(World.Environment environment);

    String getGeneratorSettings(World.Environment environment);

    String getRandomTickSpeed(World.Environment environment);

    String getNaturalRegeneration(World.Environment environment);


}
