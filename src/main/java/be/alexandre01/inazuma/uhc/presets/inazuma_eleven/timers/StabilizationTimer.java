package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.utils.DateBuilderTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import spg.lgdev.iSpigot;

import java.util.ArrayList;

public class StabilizationTimer extends Timer {
    public StabilizationTimer() {
        super("stabilizationTimer");
            InazumaEleven p = (InazumaEleven) Preset.instance.p;
            be.alexandre01.inazuma.uhc.InazumaUHC i = be.alexandre01.inazuma.uhc.InazumaUHC.get;
            spg.lgdev.iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
            ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            super.setOptimisation(false);
            super.setTimer(new ITimer() {
                boolean activate = false;
                DateBuilderTimer dateBuilderTimer;
                @Override
                public void preRun() {
                    for(Player player: Bukkit.getOnlinePlayers()){
                        player.sendMessage("§7Stabilisation du serveur.");
                    }
                }

                @Override
                public void run() {
                    double tps = iSpigot.getTPS()[0];


                    if(tps < 20.5 || tps > 19.5){
                        if(!activate){
                            dateBuilderTimer = new DateBuilderTimer(3*1000);
                        }
                        dateBuilderTimer.loadDate();


                        if(dateBuilderTimer.getDate().getTime() <= 0){
                            cancel();
                        }
                    }
                }
            });
}
}
