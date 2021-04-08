package be.alexandre01.inazuma.uhc.timers.game;


import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;

public class EpisodeTimeTimer extends Timer {
    public static boolean cancel = false;
    PresetData p = Preset.instance.pData;
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

                p.totalTimeValue = builderTimer.getBuild()+"s";
                if(cancel){
                    cancel();
                }
            }
        });
    }

}
