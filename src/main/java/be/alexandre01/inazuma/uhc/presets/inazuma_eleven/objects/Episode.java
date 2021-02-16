package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects;

import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.cusom_events.EpisodeChangeEvent;
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
