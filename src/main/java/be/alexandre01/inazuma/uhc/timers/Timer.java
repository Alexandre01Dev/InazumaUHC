package be.alexandre01.inazuma.uhc.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCancelEvent;
import be.alexandre01.inazuma.uhc.custom_events.timers.TimerCreateEvent;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.timers.exception.NullTimerException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import spg.lgdev.iSpigot;

import java.time.Instant;

public class Timer extends BukkitRunnable{
    boolean optimisation = true;
    boolean b = false;
    boolean c = false;
    double d = 0;
    boolean isAlreadyLaunched = false;
    Instant now = Instant.now();
    Instant sec = Instant.now();
    iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
    BukkitTask bukkitTask;
    String timerName;
    setup setup = null;
    long delay;
    long period;
    public boolean isRunning = false;
    public InazumaUHC i;
    public IPreset p;
    ITimer run = null;
    public Timer(String timerName){
        this.timerName = timerName;
        this.i = InazumaUHC.get;
        this.p = Preset.instance.p;
    }
    public Timer(String timerName,boolean register){
        this.timerName = timerName;
        this.i = InazumaUHC.get;
        this.p = Preset.instance.p;
        if(register){
            InazumaUHC.get.tm.addTimer(this);
        }
        Class c = getClass();


    }
    public void setTimer(ITimer iTimer){
        run = iTimer;
    }

    @Override
    public void run() {

        if(optimisation){
            double tps = iSpigot.getTPS()[0];
            System.out.println("> Optimisation + "+ tps);

            if(tps > 22 || tps < 16.5){
                if (b) {
               d = d + 20/tps;
               if(d >= 0.78){
                    int round = (int) Math.round(d);
                   for (int i = 0; i < round; i++) {
                       run.run();
                   }
                   d = d-round;


               }
               }
            }else {
                b = true;
                d = 0;
                run.run();
            }
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
            setOptimisation(false);
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
            setOptimisation(false);
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
            setOptimisation(false);
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
            setOptimisation(false);
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
            this.delay = period;
            this.period = period;
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
                this.delay = period;
                this.period = period;
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

        Class c = this.getClass();
        if(!cancelTimerEvent.isCancelled()){
            if(setup == null){
                setSetup(new setup(){
                    @Override
                    public Timer setInstance() {
                        try {
                            return (Timer) c.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    };
                });
            }
            Timer timer = newInstance();
            cancelTimerEvent.setTimer(timer);
            bukkitTask.cancel();
            InazumaUHC.get.tm.timers.put(this.getClass(),timer);
            Bukkit.getPluginManager().callEvent(cancelTimerEvent);
        }
    }

    public String getTimerName() {
        return timerName;
    }

    public void setOptimisation(boolean optimisation) {
        this.optimisation = optimisation;
    }

    public Timer newInstance(){
        return setup.setInstance();
    }

    public void setSetup(Timer.setup setup) {
        this.setup = setup;
    }

    protected interface setup{
        public Timer setInstance();
    }
}
