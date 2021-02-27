package be.alexandre01.inazuma.uhc.timers.game;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import spg.lgdev.iSpigot;

import java.util.ArrayList;

public class StartingTimer extends Timer {
    public StartingTimer() {
        super("startingTimer");
        PresetData p = Preset.instance.pData;
        be.alexandre01.inazuma.uhc.InazumaUHC i = be.alexandre01.inazuma.uhc.InazumaUHC.get;
        iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        super.setTimer(new ITimer() {

            int modifier = 5;
            float lenght = 0.8f;

            @Override
            public void preRun() {
                p.pvpText = "§cLancement dans:";
                p.pvpValue = "§e"+modifier+"s";
            }

            @Override
            public void run() {
               for(Player player : Bukkit.getOnlinePlayers()){
                   TitleUtils.sendTitle(player,0,15,5,"§e"+modifier," ");
                   player.playSound(player.getLocation(), Sound.NOTE_PLING,1,lenght);
               }
                p.pvpValue = "§e"+modifier+"s";

                   lenght = lenght + 0.05f;

                    if(modifier == 0){
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.playSound(player.getLocation(), Sound.GLASS,1.1f,0.9f);
                        }
                        cancel();
                    }


                    modifier--;


                }
        });
    }

}
