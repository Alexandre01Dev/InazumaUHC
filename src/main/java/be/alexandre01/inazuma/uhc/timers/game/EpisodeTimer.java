package be.alexandre01.inazuma.uhc.timers.game;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.utils.Episode;


public class EpisodeTimer extends Timer {
    public EpisodeTimer() {
        super("episodeTimer");
        PresetData p = Preset.instance.pData;
        setTimer(new ITimer() {
            DateBuilderTimer builderTimer;

            @Override
            public void preRun() {
                builderTimer = new DateBuilderTimer(20*60*1000L);
                Episode.addEpisode();
            }

            @Override
            public void run() {
                builderTimer.loadDate();
                if(builderTimer.getDate().getTime() <= 0){
                    System.out.println("timeurrr");
                    InazumaUHC.get.tm.getTimer(EpisodeTimeTimer.class).reset();
                    reset();
                }
            }
        });
    }

}
