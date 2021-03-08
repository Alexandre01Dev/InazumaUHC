package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

public class EpisodeEvent implements Listener {

    @EventHandler
    public void onEpisode(EpisodeChangeEvent e){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (Episode.getEpisode() == 2){
                player.playSound(player.getLocation(), "episode2", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 2","§9La Menace de l'§5Académie Alius§9 !");
            }
            if (Episode.getEpisode() == 3){
                player.playSound(player.getLocation(), "episode3", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 3","§9L'§5Académie Alius§9 passe à l'Attaque !");
            }
            if (Episode.getEpisode() == 4){
                player.playSound(player.getLocation(), "episode4", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 4","§6Raimon§9 contre§7-§9attaque !");
            }
            if (Episode.getEpisode() == 5){
                player.playSound(player.getLocation(), "episode5", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 5","§9La Menace ressurgit !");
            }
            if (Episode.getEpisode() == 6){
                player.playSound(player.getLocation(), "episode6", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 6","§9Les Doutes du capitaine...");
            }
            if (Episode.getEpisode() == 7){
                player.playSound(player.getLocation(), "episode7", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 7","");
            }
            if (Episode.getEpisode() == 8){
                player.playSound(player.getLocation(), "episode8", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 8","");
            }
            if (Episode.getEpisode() == 9){
                player.playSound(player.getLocation(), "episode9", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 9","");
            }
            if (Episode.getEpisode() == 10){
                player.playSound(player.getLocation(), "episode10", Integer.MAX_VALUE, 1);
                TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 10","");
            }

        }
    }
}
