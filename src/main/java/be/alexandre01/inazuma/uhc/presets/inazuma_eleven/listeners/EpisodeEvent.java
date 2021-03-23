package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.listeners;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EpisodeEvent implements Listener {

    @EventHandler
    public void onEpisode(EpisodeChangeEvent e){
        World world = ((CraftWorld)InazumaUHC.get.worldGen.defaultWorld).getHandle();
        switch (Episode.getEpisode()){
            case 1:
                world.makeSound(0,0,0,"episode1", (Preset.instance.p.getBorderSize(org.bukkit.World.Environment.NORMAL)/10f)*3,1);
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,30,90,10,"§9Début de l'§eEpisode 1","§9Les §5extraterrestres §9débarquent !");
                }
                break;
            case 2:
                world.makeSound(0,0,0,"episode2", (Preset.instance.p.getBorderSize(org.bukkit.World.Environment.NORMAL)/10f)*3,1);
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 2","§9La Menace de l'§5Académie Alius§9 !");
                }
                break;
            case 3:
                world.makeSound(0,0,0,"episode3",(Preset.instance.p.getBorderSize(org.bukkit.World.Environment.NORMAL)/10f)*3,1);
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 3","§9L'§5Académie Alius§9 passe à l'Attaque !");
                }
                break;
            case 4:
                world.makeSound(0,0,0,"episode4",(Preset.instance.p.getBorderSize(org.bukkit.World.Environment.NORMAL)/10f)*3,1);
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 4","§6Raimon§9 contre§7-§9attaque !");
                }
                break;
            case 5:
                world.makeSound(0,0,0,"episode5",(Preset.instance.p.getBorderSize(org.bukkit.World.Environment.NORMAL)/10f)*3,1);
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 5","§9La Menace ressurgit !");
                }
                break;
            case 6:
                world.makeSound(0,0,0,"episode6",(Preset.instance.p.getBorderSize(org.bukkit.World.Environment.NORMAL)/10f)*3,1);
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode 6","§9Les Doutes du capitaine...");
                }
                break;

            default:
                for (Player player : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(player,40,100,10,"§9Début de l'§eEpisode "+Episode.getEpisode(),"");
                }

        }

    }
}
