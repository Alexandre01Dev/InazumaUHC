package be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.World;

public class DelayedTimeChangeTimer extends Timer {
    long l;
    private State state;
    public enum State{
        DAY,NIGHT;
    }

    public void setState(DelayedTimeChangeTimer.State state) {
        this.state = state;
    }

    public DelayedTimeChangeTimer(State state, long initialDelay, long delay) {
        super("delayedTimeChangeTimer");
        this.setSetup(new setup() {
            @Override
            public Timer setInstance() {
                return new DelayedTimeChangeTimer(state,initialDelay,delay);
            }
        });
        this.l = l;
        this.state = state;

        State finalState = this.state;
        setTimer(new ITimer() {
            World world;
            long from;
            long timesToExecute;
            long to;
            long addition;
            @Override
            public void preRun() {

                if(finalState.equals(DelayedTimeChangeTimer.State.DAY)){
                    from = 1000L;
                    to = 20000L;
                }else {
                    from = 20000L;
                    to = 1000L;
                }
                long timeCalc = 0;
                if(from > to){
                    timeCalc = 24000L-from+to;
                }else {
                    timeCalc = to-from;
                }
                world = InazumaUHC.get.worldGen.defaultWorld;
                timesToExecute = delay/initialDelay;
                addition = timeCalc/timesToExecute;
            }

            @Override
            public void run() {
                if(timesToExecute == 0){
                    world.setTime(to);
                    cancel();
                }
                world.setTime(world.getTime()+addition);
                timesToExecute--;
            }
        });


    }

    public State getState() {
        return state;
    }
}
