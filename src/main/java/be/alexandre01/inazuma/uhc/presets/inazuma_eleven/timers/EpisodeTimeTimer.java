package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers;

import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;

public class EpisodeTimeTimer extends Timer {
    public static boolean cancel = false;
    public EpisodeTimeTimer() {
        super("episodeTimeTimer");

        setTimer(new ITimer() {
            DateBuilderTimer builderTimer;

            @Override
            public void preRun() {
                cancel = false;
                builderTimer = new DateBuilderTimer();
            }

            @Override
            public void run() {
                builderTimer.loadDate();

                InazumaEleven.totalTimeValue = builderTimer.getBuild()+"s";
                if(cancel){
                    cancel();
                }
            }
        });
    }

}
