package be.alexandre01.inazuma.uhc.presets.normal.timers;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import spg.lgdev.iSpigot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NetherTimer extends Timer {
    public NetherTimer() {
        super("nether");
        Normal p = (Normal) Preset.instance.p;
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
                    time = (p.getNetherTime()* 1000L)+now;
                }

                Normal.netherText = "§cDésac. Nether: ";
                Normal.netherValue = "§e"+modifier+"s";
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
                    HashMap<Player, Location> hashMap = i.npm.portalUsedByPlayer;
                    i.npm.active = false;
                    if(!hashMap.isEmpty()){
                        Bukkit.getScheduler().scheduleSyncDelayedTask(i, new BukkitRunnable() {
                            @Override
                            public void run() {
                                for(Player player : hashMap.keySet()){
                                    if(player.getWorld().getEnvironment().equals(World.Environment.NETHER)){
                                        player.teleport(hashMap.get(player));
                                    }
                                }
                            }
                        });

                    }

                    Normal.netherText="§cNether: ";
                    Normal.netherValue="§c§lOFF";
                    cancel();
                    return;
                }
              if(date.getTime() < 10000) {
                  for(Player player : Bukkit.getOnlinePlayers()){
                      TitleUtils.sendActionBar(player,"§eLe §c§lNETHER§e sera désactivé dans "+second+" seconde(s)");
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
                Normal.netherValue = "§e"+sb.toString()+"s";








                }
        });
    }

}
