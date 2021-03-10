package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.scoreboard.Reflection.PacketType;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EpisodeTimer extends Timer {
    public EpisodeTimer() {
        super("episodeTimer");

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
                System.out.println(builderTimer.getDate().getTime()+" temps timer");
                if(builderTimer.getDate().getTime() <= 0){
                    System.out.println("timeurrr");
                    InazumaUHC.get.tm.getTimer(EpisodeTimeTimer.class).reset();
                    reset();
                }
            }
        });
    }

}
