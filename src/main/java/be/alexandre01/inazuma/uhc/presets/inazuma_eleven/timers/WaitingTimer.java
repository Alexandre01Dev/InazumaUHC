package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import spg.lgdev.iSpigot;

import java.util.ArrayList;

public class WaitingTimer extends Timer {
    public WaitingTimer() {
        super("waitingTimer");
        InazumaEleven p = (InazumaEleven) Preset.instance.p;
        be.alexandre01.inazuma.uhc.InazumaUHC i = be.alexandre01.inazuma.uhc.InazumaUHC.get;
        iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        super.setTimer(new ITimer() {
            int modifier = p.getWaitingTime();

            @Override
            public void preRun() {
                if(!players.isEmpty()){
                    for(Player player : players){
                        player.sendMessage("§cLes chunks viennent de charger, veillez patienter..");
                    }
                }
            }

            @Override
            public void run() {
                    int size = Bukkit.getOnlinePlayers().size();
                    if(modifier == 0){
                        cancel();
                    }
                    if(modifier > 10 && size >= p.getMinPlayerToStart()){
                        modifier = 10;
                        Normal.lastModifier = modifier;
                    }
                    if(size == 0){
                        modifier= p.getWaitingTime();
                        Normal.lastModifier = modifier;
                    }else {

                        Normal.lastModifier = modifier;
                        modifier--;
                    }
                }
        });
    }

}
