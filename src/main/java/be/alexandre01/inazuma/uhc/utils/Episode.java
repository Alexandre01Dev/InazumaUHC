package be.alexandre01.inazuma.uhc.utils;


import be.alexandre01.inazuma.uhc.custom_events.episode.EpisodeChangeEvent;
import org.bukkit.Bukkit;

public class Episode {
    private static int episode = 0;

    public static void addEpisode(){
        episode++;

        Bukkit.getPluginManager().callEvent(new EpisodeChangeEvent());
    }

    public static int getEpisode(){
        return episode;
    }
}
