package be.alexandre01.inazuma.uhc.presets.jujutsu_kaizen.objects;

public class Episode {
    private static int episode = 0;

    public static void addEpisode(){
        episode++;
    }

    public static int getEpisode(){
        return episode;
    }
}
