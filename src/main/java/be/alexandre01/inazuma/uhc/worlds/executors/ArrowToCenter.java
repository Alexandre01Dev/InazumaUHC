package be.alexandre01.inazuma.uhc.worlds.executors;

import be.alexandre01.inazuma.uhc.custom_events.episode.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.custom_events.player.PlayerDelayedMoveEvent;
import be.alexandre01.inazuma.uhc.generations.chunks.ChunksGenerator;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ArrowToCenter {
    private ScheduledExecutorService scheduledExecutorService;
  @Setter private IPreset preset;
    public ArrowToCenter(){
        super();
        preset = Preset.instance.p;
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
    }

    public void schedule(){
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
             for(Player player : Bukkit.getOnlinePlayers()){
                 World world = player.getWorld();

                 Location l1 =  player.getLocation();

                 Bukkit.getPluginManager().callEvent(new PlayerDelayedMoveEvent(player,l1));

                 l1.setPitch(0);
                 l1.setY(0);
                 Vector direction = l1.getDirection();
                 Location l2 =  world.getSpawnLocation();
                 l2.setY(0);
                 Vector loc = l2.subtract(l1).toVector();



                 StringBuilder sb = new StringBuilder();
                 double doubleDistance = l1.distance(l2);
                 int distance = (int) Math.round(doubleDistance/2);
                 String c = "§l•";
                 if(distance > 100){
                     double angleLook = (Math.atan2(direction.getZ(),direction.getX()) / 2 / Math.PI * 360 + 360) % 360;
                     double angleDir = (Math.atan2(loc.getZ(),loc.getX()) / 2 / Math.PI * 360 + 360) % 360;

                     double angle = (angleDir - angleLook + 360) % 360;

                     if(angle >= 337.5 && angle <= 360 || angle >= 0 && angle < 22.5){
                         c = "⬆";
                     }else {
                         if(angle >= 22.5 && angle < 67.5){
                             c = "⬈";
                         }else {
                             if(angle >= 67.5 && angle < 112.5){
                                 c = "➡";
                             }else {
                                 if(angle >= 112.5 && angle < 157.5){
                                     c = "⬊";
                                 }else {
                                     if(angle >= 157.5 && angle < 202.5){//180
                                         c = "⬇";
                                     }else {
                                         if(angle >= 202.5 && angle < 247.5){
                                             c = "⬋";
                                         }else {
                                             if(angle >= 247.5 && angle < 292.5){
                                                 c = "§l⬅";
                                             }else {
                                                 if(angle >= 292.5 && angle < 337.5){
                                                     c = "⬉";
                                                 }
                                             }
                                         }
                                     }
                                 }

                             }
                         }
                     }
                     sb.append(c+" (§c"+distance+"§6)");
                     preset.getArrows().put(player.getUniqueId(),sb.toString());
                     continue;
                 }
                 sb.append(c);
                 preset.getArrows().put(player.getUniqueId(),sb.toString());
             }
            }
        },0,1, TimeUnit.SECONDS);
    }
}
