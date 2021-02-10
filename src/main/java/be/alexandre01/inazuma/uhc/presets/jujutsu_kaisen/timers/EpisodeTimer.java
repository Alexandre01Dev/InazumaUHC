package be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.objects.Episode;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import be.alexandre01.inazuma.uhc.timers.utils.MSToSec;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EpisodeTimer extends Timer {
    public EpisodeTimer() {
        super("episodeTimer");

        setTimer(new ITimer() {
            DateBuilderTimer builderTimer;
            boolean night = false;
            @Override
            public void preRun() {
                builderTimer = new DateBuilderTimer(20*60*1000L);
                Episode.addEpisode();
            }

            @Override
            public void run() {

                builderTimer.loadDate();
                if(!night){
                    System.out.println(MSToSec.toMili(30));
                    System.out.println(builderTimer.getDate().getTime());
                    if(builderTimer.getDate().getTime() <= MSToSec.toMili(60*10)){//60*10)){
                        for(Player player: Bukkit.getOnlinePlayers()){
                            player.sendMessage( p.prefixName()+"Il commence à faire nuit");
                        }
                        night = true;
                        DelayedTimeChangeTimer delayedTimeChangeTimer = (DelayedTimeChangeTimer) InazumaUHC.get.tm.getTimer(DelayedTimeChangeTimer.class);
                        delayedTimeChangeTimer.setState(DelayedTimeChangeTimer.State.DAY);
                        delayedTimeChangeTimer.runTaskTimerAsynchronously(InazumaUHC.get,0,1);

                    }
                }

                if(builderTimer.getDate().getTime() <= 0){
                    for(Player player: Bukkit.getOnlinePlayers()){

                        player.sendMessage( p.prefixName()+"Il commence à faire jour");
                    }
                    DelayedTimeChangeTimer delayedTimeChangeTimer = (DelayedTimeChangeTimer) InazumaUHC.get.tm.getTimer(DelayedTimeChangeTimer.class);
                    delayedTimeChangeTimer.setState(DelayedTimeChangeTimer.State.NIGHT);
                    delayedTimeChangeTimer.runTaskTimerAsynchronously(InazumaUHC.get,0,1);
                    cancel();
                }
            }
        });
    }

}
