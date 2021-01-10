package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PresetData {
    protected boolean hasScenario = true;
    public boolean hasNether = false;
    public int minPlayerToStart = 10;
    public int playerSize = 20;
    public int totalTime = 60*60;
    protected ArrayList<Scenario> scenarios = new ArrayList<>();
    protected ArrayList<IPersonalScoreBoard> scoreboards = new ArrayList<>();
    protected ArrayList<Listener> listeners = new ArrayList<>();
    protected ArrayList<Timer> timers = new ArrayList<>();
    protected HashMap<UUID,String> arrows = new HashMap<>();
    protected Plateform plateform;
    protected String[] generatorSettings = {"",""};
    public static int lastModifier = 0;
    public static String timerText = "";
    public static String timerValue = "";
    public static String bordureValue ="";
    public static String bordureText ="";
    public static String netherText ="";
    public static String netherValue ="";
    public IPersonalScoreBoard i;


    public boolean hasScenario() {
        return hasScenario;
    }

    public void setHasScenario(boolean hasScenario) {
        this.hasScenario = hasScenario;
    }

    public ArrayList<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(ArrayList<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public ArrayList<IPersonalScoreBoard> getScoreboards() {
        return scoreboards;
    }

    public void setScoreboards(ArrayList<IPersonalScoreBoard> scoreboards) {
        this.scoreboards = scoreboards;
    }

    public ArrayList<Listener> getListeners() {
        return listeners;
    }

    public void setListeners(ArrayList<Listener> listeners) {
        this.listeners = listeners;
    }

    public ArrayList<Timer> getTimers() {
        return timers;
    }

    public void setTimers(ArrayList<Timer> timers) {
        this.timers = timers;
    }

    public HashMap<UUID, String> getArrows() {
        return arrows;
    }

    public void setArrows(HashMap<UUID, String> arrows) {
        this.arrows = arrows;
    }

    public Plateform getPlateform() {
        return plateform;
    }

    public void setPlateform(Plateform plateform) {
        this.plateform = plateform;
    }

    public static int getLastModifier() {
        return lastModifier;
    }

    public static void setLastModifier(int lastModifier) {
        PresetData.lastModifier = lastModifier;
    }

    public static String getTimerText() {
        return timerText;
    }

    public static void setTimerText(String timerText) {
        PresetData.timerText = timerText;
    }

    public static String getTimerValue() {
        return timerValue;
    }

    public static void setTimerValue(String timerValue) {
        PresetData.timerValue = timerValue;
    }

    public static String getBordureValue() {
        return bordureValue;
    }

    public static void setBordureValue(String bordureValue) {
        PresetData.bordureValue = bordureValue;
    }

    public static String getBordureText() {
        return bordureText;
    }

    public static void setBordureText(String bordureText) {
        PresetData.bordureText = bordureText;
    }

    public static String getNetherText() {
        return netherText;
    }

    public static void setNetherText(String netherText) {
        PresetData.netherText = netherText;
    }

    public static String getNetherValue() {
        return netherValue;
    }

    public static void setNetherValue(String netherValue) {
        PresetData.netherValue = netherValue;
    }

    public IPersonalScoreBoard getI() {
        return i;
    }

    public void setI(IPersonalScoreBoard i) {
        this.i = i;
    }

    public boolean hasNether() {
        return hasNether;
    }

    public int getMinPlayerToStart() {
        return minPlayerToStart;
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public String[] getGeneratorSettings() {
        return generatorSettings;
    }
}
