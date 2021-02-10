package be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.Jujutsu_Kaisen;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
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

public class PVPTimer extends Timer {
    public PVPTimer() {
        super("pvpTimer");
        Jujutsu_Kaisen p = (Jujutsu_Kaisen) Preset.instance.p;
        InazumaUHC i = InazumaUHC.get;
        iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        super.setTimer(new ITimer() {
            Format h = new SimpleDateFormat("hh");
            Format m = new SimpleDateFormat("mm");
            Format s = new SimpleDateFormat("ss");

            int modifier = 20;
            float lenght = 0.8f;;
            long pvpTime = 0;
            int t = 0;

            @Override
            public void preRun() {

            }

            @Override
            public void run() {
                long now = new Date().getTime();
                if(this.pvpTime == 0){
                    pvpTime = (p.getPVPTime()* 1000L)+now;
                }

                Normal.timerText = "§cPVP: ";
                Normal.timerValue = "§e"+modifier+"s";
                Date date = new Date(pvpTime-now);
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
                    i.worldGen.defaultWorld.setPVP(true);
                    Normal.timerText="§cPVP: ";
                    Normal.timerValue="§a§lON";
                    cancel();
                    return;
                }
              if(date.getTime() < 10000) {
                  for(Player player : Bukkit.getOnlinePlayers()){
                      TitleUtils.sendActionBar(player,"§eLe §c§lPVP§e sera actif dans "+second+" seconde(s)");
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
                Normal.timerValue = "§e"+sb.toString()+"s";








                }
        });
    }

}
