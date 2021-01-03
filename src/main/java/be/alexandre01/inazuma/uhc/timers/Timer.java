package be.alexandre01.inazuma.uhc.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCancelEvent;
import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCreateEvent;
import be.alexandre01.inazuma.uhc.timers.exception.NullTimerException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import spg.lgdev.iSpigot;

import java.time.Duration;
import java.time.Instant;

public class Timer extends BukkitRunnable{
    boolean optimisation = true;
    boolean b = false;
    boolean c = false;
    boolean isAlreadyLaunched = false;
    Instant now = Instant.now();
    Instant sec = Instant.now();
    iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
    BukkitTask bukkitTask;
    String timerName;
    ITimer run = null;
    public Timer(String timerName){
        this.timerName = timerName;
    }

    public void setTimer(ITimer iTimer){
        run = iTimer;
    }

    @Override
    public void run() {
        if(optimisation){
            if (!b && iSpigot.getTPS()[0] >= 18) {
                Instant i = Instant.now();
                if(Duration.between(now,i).toMillis() >= 900){
                    b = true;
                }
                if(Duration.between(sec,i).toMillis() >= 1000){
                    sec = Instant.now();
                    c = true;
                }
            }
            if(b||c){
                run.run();
            }
            c = false;
            now = Instant.now();
        }else {
            run.run();
        }

    }

    @Override
    public synchronized BukkitTask runTask(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        if(run == null){
            try {
                throw new NullTimerException("No timer run found, check if the run value is set.");
            } catch (NullTimerException e) {
                e.printStackTrace();
            }
            return null;
        }

        TimerCreateEvent createTimerEvent = new TimerCreateEvent(this);
        Bukkit.getPluginManager().callEvent(createTimerEvent);
        if(!createTimerEvent.isCancelled()){
            run.preRun();

            bukkitTask = super.runTask(plugin);
            return bukkitTask;
        }
     return null;
    }

    @Override
    public synchronized BukkitTask runTaskAsynchronously(Plugin plugin) throws IllegalArgumentException, IllegalStateException {

        if(run == null){
            try {
                throw new NullTimerException("No timer run found, check if the run value is set.");
            } catch (NullTimerException e) {
                e.printStackTrace();
            }
            return null;
        }
        TimerCreateEvent createTimerEvent = new TimerCreateEvent(this);
        Bukkit.getPluginManager().callEvent(createTimerEvent);
        if(!createTimerEvent.isCancelled()){
            run.preRun();
            bukkitTask = super.runTaskAsynchronously(plugin);
            return bukkitTask;
        }
      return null;
    }

    @Override
    public synchronized BukkitTask runTaskLater(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        if(run == null){
            try {
                throw new NullTimerException("No timer run found, check if the run value is set.");
            } catch (NullTimerException e) {
                e.printStackTrace();
            }
            return null;
        }
        TimerCreateEvent createTimerEvent = new TimerCreateEvent(this);
        Bukkit.getPluginManager().callEvent(createTimerEvent);
        if(!createTimerEvent.isCancelled()){
            run.preRun();
            bukkitTask = super.runTaskLater(plugin, delay);
            return bukkitTask;
        }
        return null;
    }

    @Override
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        if(run == null){
            try {
                throw new NullTimerException("No timer run found, check if the run value is set.");
            } catch (NullTimerException e) {
                e.printStackTrace();
            }
            return null;
        }
        TimerCreateEvent createTimerEvent = new TimerCreateEvent(this);
        Bukkit.getPluginManager().callEvent(createTimerEvent);
        if(!createTimerEvent.isCancelled()){
            run.preRun();
            bukkitTask = super.runTaskLaterAsynchronously(plugin, delay);
            return bukkitTask;
        }
       return null;
    }

    @Override
    public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        if(run == null){
            try {
                throw new NullTimerException("No timer run found, check if the run value is set.");
            } catch (NullTimerException e) {
                e.printStackTrace();
            }
            return null;
        }
        TimerCreateEvent createTimerEvent = new TimerCreateEvent(this);
        Bukkit.getPluginManager().callEvent(createTimerEvent);
        if(!createTimerEvent.isCancelled()){
            run.preRun();
            bukkitTask = super.runTaskTimer(plugin, delay,period);
            return bukkitTask;
        }
        return null;
    }

    @Override
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        if(run == null){
            try {
                throw new NullTimerException("No timer run found, check if the run value is set.");
            } catch (NullTimerException e) {
                e.printStackTrace();
            }
            return null;
        }
        if(!isAlreadyLaunched){
            TimerCreateEvent createTimerEvent = new TimerCreateEvent(this);
            Bukkit.getPluginManager().callEvent(createTimerEvent);
            if(!createTimerEvent.isCancelled()){
                run.preRun();
                bukkitTask = super.runTaskTimerAsynchronously(plugin, delay, period);
                isAlreadyLaunched = true;
                return bukkitTask;
            }
        }
        return null;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        TimerCancelEvent cancelTimerEvent = new TimerCancelEvent(this);
        Bukkit.getPluginManager().callEvent(cancelTimerEvent);
        if(!cancelTimerEvent.isCancelled()){

            bukkitTask.cancel();
            try {
                InazumaUHC.get.tm.timers.put(this.getClass(),this.getClass().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTimerName() {
        return timerName;
    }

    public void setOptimisation(boolean optimisation) {
        this.optimisation = optimisation;
    }

}
