package be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.Jujutsu_Kaizen;
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

public class BordureTimer extends Timer {
    public BordureTimer() {
        super("bordureTimer");
        Jujutsu_Kaizen p = (Jujutsu_Kaizen) Preset.instance.p;
        InazumaUHC i = InazumaUHC.get;
        iSpigot iSpigot = spg.lgdev.iSpigot.INSTANCE;
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        super.setTimer(new ITimer() {
            Format h = new SimpleDateFormat("hh");
            Format m = new SimpleDateFormat("mm");
            Format s = new SimpleDateFormat("ss");

            int modifier = 20;
            float lenght = 0.8f;;
            long time = 0;
            int t = 0;

            @Override
            public void preRun() {

            }

            @Override
            public void run() {
                long now = new Date().getTime();
                if(this.time == 0){
                    time = (p.getBordureTime()* 1000L)+now;
                }

                Normal.bordureText = "§cBordure: ";
                Normal.bordureValue= "§e"+modifier+"s";
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

                   Timer timer = i.tm.getTimer(MoveBordureTimer.class);
                    timer.runTaskTimerAsynchronously(InazumaUHC.get,0,10);
                    cancel();
                    return;
                }
              if(date.getTime() < 10000) {
                  for(Player player : Bukkit.getOnlinePlayers()){
                      TitleUtils.sendActionBar(player,"§eLa §c§lBordure§e se déplacera dans "+second+" seconde(s)");
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
                Normal.bordureValue = "§e"+sb.toString()+"s";
                }
        });
    }

}
