package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public interface IPreset  {
    boolean autoJoinWorld();

    String getName();

    String prefixName();

    boolean hasRoles();

    String getPackageName();

    ArrayList<Listener> getListeners();

    ArrayList<Timer> getTimers();

    ArrayList<Class<?>> getScenarios();

    ArrayList<IPersonalScoreBoard> getScoreboards();

    boolean getNether();

    boolean canRespawnOnRejoin();

    boolean isInvicible();

    int getWaitingTime();

    int getInvisibleTime();

    int getMinPlayerToStart();

    int getTotalTime();

    int getPVPTime();

    int getNetherTime();

    int getBordureTime();

    int getEndBordureTime();

    int getEndBordure();

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

    boolean isArrowCalculated();

    HashMap<UUID,String> getArrows();

}
