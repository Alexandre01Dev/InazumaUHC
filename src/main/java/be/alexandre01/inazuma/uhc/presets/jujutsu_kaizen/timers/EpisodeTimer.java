package be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;

public class EpisodeTimer extends Timer {
    public EpisodeTimer() {
        super("episodeTimer");
        DateBuilderTimer builderTimer = new DateBuilderTimer(20*60*1000L);
        setTimer(new ITimer() {
            boolean night = false;
            @Override
            public void preRun() {
            }

            @Override
            public void run() {
                builderTimer.loadDate();
                if(!night){
                    if(builderTimer.getDate().getTime() >= MSToSec.toMili(60*10)){
                        night = true;
                        InazumaUHC.get.tm.addTimer(new DelayedTimeChangeTimer(DelayedTimeChangeTimer.State.DAY,5,20*5));
                    }
                }

                if(builderTimer.getDate().getTime() >= 0){
                    cancel();
                }
            }
        });
    }

}
