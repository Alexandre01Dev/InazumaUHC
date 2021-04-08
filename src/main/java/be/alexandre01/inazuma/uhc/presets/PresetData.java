package be.alexandre01.inazuma.uhc.presets;

import be.alexandre01.inazuma.uhc.generations.Plateform;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PresetData {
    protected boolean hasScenario = true;
    public boolean isInvisible = true;
    public boolean hasNether = false;
    public int minPlayerToStart = 10;
    public int playerSize = 20;
    public int totalTime = 60*60;
    public int waitingTime = 120;
    public int pvpTime = 30;
    public int invisibilityTime = 1;
    public int netherTime = 55*60;
    public int bordureTime = 60*60;
    public int borderSize;
    public int borderSizeNether;
    public int endBordureTime = 60*15;
    public int endBordureSize = 250*2;
    public int teamSize = 1;
    protected ArrayList<Class<?>> scenarios = new ArrayList<>();
    protected ArrayList<IPersonalScoreBoard> scoreboards = new ArrayList<>();
    protected ArrayList<Listener> listeners = new ArrayList<>();
    protected ArrayList<Timer> timers = new ArrayList<>();
    protected HashMap<UUID,String> arrows = new HashMap<>();
    protected Plateform plateform;
    protected String[] generatorSettings = {"",""};
    public int lastModifier = 0;
    public String pvpText = "";
    public String pvpValue = "";
    public String bordureValue ="";
    public String bordureText ="";
    public String netherText ="";
    public String netherValue ="";
    public String totalTimeValue ="";
    public IPersonalScoreBoard i;


    public boolean hasScenario() {
        return hasScenario;
    }

    public void setHasScenario(boolean hasScenario) {
        this.hasScenario = hasScenario;
    }

    public ArrayList<Class<?>> getScenarios() {
        return scenarios;
    }

    public void setScenarios(ArrayList<Class<?>> scenarios) {
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

    public int getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(int lastModifier) {
        this.lastModifier = lastModifier;
    }

    public  String getTimerText() {
        return this.pvpText;
    }

    public  void setTimerText(String timerText) {
        this.pvpText = timerText;
    }

    public  String getPVPValue() {
        return this.pvpValue;
    }

    public void setPVPValue(String timerValue) {
        this.pvpValue = timerValue;
    }

    public  String getBordureValue() {
        return bordureValue;
    }

    public  void setBordureValue(String bordureValue) {
        this.bordureValue = bordureValue;
    }

    public  String getBordureText() {
        return bordureText;
    }

    public  void setBordureText(String bordureText) {
        this.bordureText = bordureText;
    }

    public String getNetherText() {
        return netherText;
    }

    public void setNetherText(String netherText) {
        this.netherText = netherText;
    }

    public String getNetherValue() {
        return netherValue;
    }

    public void setNetherValue(String netherValue) {
        this.netherValue = netherValue;
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
