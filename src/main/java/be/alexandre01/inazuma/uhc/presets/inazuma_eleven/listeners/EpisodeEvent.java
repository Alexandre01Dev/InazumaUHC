package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EpisodeEvent implements Listener {

    @EventHandler
    public void onEpisode(EpisodeChangeEvent e){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (Episode.getEpisode() == 2){
                player.playSound(player.getLocation(), "episode2", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 2","§9L'§5Académie Alius§9 passe à l'attaque !");
            }
            if (Episode.getEpisode() == 3){
                player.playSound(player.getLocation(), "episode3", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 3","§6Raimon§9 contre§7-§9attaque !");
            }
            if (Episode.getEpisode() == 4){
                player.playSound(player.getLocation(), "episode4", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 4","§9La menace ressurgit !");
            }
            if (Episode.getEpisode() == 5){
                player.playSound(player.getLocation(), "episode5", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 5","");
            }
            if (Episode.getEpisode() == 6){
                player.playSound(player.getLocation(), "episode6", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 6","");
            }
            if (Episode.getEpisode() == 7){
                player.playSound(player.getLocation(), "episode7", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 7","");
            }
            if (Episode.getEpisode() == 8){
                player.playSound(player.getLocation(), "episode8", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 8","");
            }
            if (Episode.getEpisode() == 9){
                player.playSound(player.getLocation(), "episode9", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 9","");
            }
            if (Episode.getEpisode() == 10){
                player.playSound(player.getLocation(), "episode10", 100, 1);
                TitleUtils.sendTitle(player,20,100,20,"§9Début de l'§eEpisode 10","");
            }

        }
    }
}
