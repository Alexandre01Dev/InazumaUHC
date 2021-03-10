package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.managers.DamageManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.custom_events.EpisodeChangeEvent;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class David extends Role {
    boolean hasChoose = false;
    public David() {
        super("David Samford");
        setRoleToSpoil(Caleb.class);
        setRoleCategory(Alius.class);
        addCommand("manchot", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(hasChoose){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }
                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/power accept §7ou §a/mark refuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("accept")){
                    hasChoose = true;





                    return;
                }
                if (args[0].equalsIgnoreCase("refuse")) {
                    hasChoose = true;

                    return;
                }
                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/power accept §7ou §a/power refuse");
            }
        });
    }

    public void accept(Player player){

    }

    public void refuse(Player player){

    }

    @EventHandler
    public void onEpisode(EpisodeChangeEvent event){

    }
}
