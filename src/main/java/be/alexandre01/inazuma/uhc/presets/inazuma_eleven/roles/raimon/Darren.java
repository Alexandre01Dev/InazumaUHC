package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Darren extends Role implements Listener {
    private boolean markDead = false;
    private Player tracked = null;

    public Darren(String name) {
        super("Darren LaChance");
        setRoleToSpoil(Mark.class);
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.setMaxHealth(24);
                    player.setHealth(24);
                }
            }
        });

        addListener(this);
        addCommand("mark", new command() {
            @Override
            public void a(String[] args, Player player) {
                if(!markDead){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous ne pouvez pas utiliser cette commande pour le moment");
                    return;
                }

                if(args.length == 0){
                    player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/mark accept §7ou §a/mark refuse");
                    return;
                }

                if(args[0].equalsIgnoreCase("accept")){

                    return;
                }
                if (args[0].equalsIgnoreCase("refuse")) {

                    return;
                }

                player.sendMessage(Preset.instance.p.prefixName()+" Veuillez mettre §a/mark accept §7ou §a/mark refuse");
            }
        });


    }

    @EventHandler
    public void onDeath(PlayerInstantDeathEvent event){
        event.getPlayer().getKiller();
    }
}
