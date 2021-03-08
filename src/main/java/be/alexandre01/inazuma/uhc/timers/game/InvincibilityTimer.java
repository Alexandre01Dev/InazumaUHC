package be.alexandre01.inazuma.uhc.timers.game;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import spg.lgdev.iSpigot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvincibilityTimer extends Timer {
    public InvincibilityTimer() {
        super("invicibilityTimer");
        PresetData p = Preset.instance.pData;
        be.alexandre01.inazuma.uhc.InazumaUHC i = be.alexandre01.inazuma.uhc.InazumaUHC.get;
        iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        super.setTimer(new ITimer() {
            Format h = new SimpleDateFormat("hh");
            Format m = new SimpleDateFormat("mm");
            Format s = new SimpleDateFormat("ss");

            int modifier = 20;
            float lenght = 0.8f;
            long time = 0;
            int t = 0;

            @Override
            public void preRun() {

            }

            @Override
            public void run() {
                long now = new Date().getTime();
                if(this.time == 0){
                    time = (30 * 1000L)+now;
                }
                Date date = new Date(time-now);
                int hour =  (int) ((date.getTime() / (1000*60*60)) % 24);
                String minute = m.format(date);
                String second = s.format(date);
                StringBuilder sb = new StringBuilder();
                if(hour > 0){
                    sb.append("0"+hour+":");
                }
                sb.append(minute+":");
                sb.append(second);



                if(date.getTime() <= 0){
                    cancel();
                    return;
                }
                if(date.getTime() < 10000) {
                    for(Player player : Bukkit.getOnlinePlayers()){
                        TitleUtils.sendActionBar(player,"§c§lL'invicibilité§e sera désactivé dans "+second+" seconde(s)");
                    }
                    lenght = lenght + 0.05f;
                }

                if(date.getTime() < 5000 ){
                    if(t==1){
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.playSound(player.getLocation(), Sound.NOTE_PLING,1,lenght);
                        }
                        t=2;
                    }else {
                        t=1;
                    }

                }

            }
        });
    }
}
