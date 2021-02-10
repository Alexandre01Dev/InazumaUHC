package be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;

public class DelayedTimeChangeTimer extends Timer {
    long l;
    private State state;
    private long delay;
    private long initialDelay;
    public enum State{
        DAY,NIGHT;
    }

    public void setState(DelayedTimeChangeTimer.State state) {
        this.state = state;
        deploy();
    }

    public DelayedTimeChangeTimer(State state, long initialDelay, long delay) {
        super("delayedTimeChangeTimer");
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.setSetup(new setup() {
            @Override
            public Timer setInstance() {
                return new DelayedTimeChangeTimer(state,initialDelay,delay);
            }
        });
        this.l = l;
        this.state = state;

        State finalState = this.state;
        deploy();
    }

    public void deploy(){
        setTimer(new ITimer() {
            World world;
            long from;
            long timesToExecute;
            long to;
            long addition;
            @Override
            public void preRun() {
                if(state.equals(DelayedTimeChangeTimer.State.DAY)){
                    from = 1000L;
                    to = 20000L;
                }else {
                    from = 20000L;
                    to = 1000L;
                }
                System.out.println(state);
                long timeCalc = 0;
                if(from > to){
                    timeCalc = 24000L-from+to;
                }else {
                    timeCalc = to-from;
                }
                world = InazumaUHC.get.worldGen.defaultWorld;
                timesToExecute = delay/initialDelay;
                addition = timeCalc/timesToExecute;

                world.setFullTime(from);
            }

            @Override
            public void run() {
                world.setFullTime(world.getTime()+addition);
                if(timesToExecute == 0){
                    world.setFullTime(to);
                    cancel();
                }
                timesToExecute--;
            }
        });

    }

    public State getState() {
        return state;
    }
}
