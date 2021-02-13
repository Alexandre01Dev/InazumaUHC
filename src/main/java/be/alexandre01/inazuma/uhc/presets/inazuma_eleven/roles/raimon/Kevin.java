package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kevin extends Role {
    public Kevin() {
        super("Kevin Dragonfly");
        setRoleCategory(Raimon.class);
        addCommand("intimidate", new command() {
        public int i = 0;
            @Override
        public void a(String[] args, Player player) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(Preset.instance.p.prefixName()+" Le joueur n'est pas en game.");
                    return;
                }
                if(target == player){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous essayez de vous intimider vous même mais en vain. Vous êtes (un peu) chelou.");
                    return;
                }
                if(i > 3){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous avez dépassé le nombre d'utilisation de cette commande");
                    return;
                }
                target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30*20, 1,false,false), true);
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10*20, 1,false,false), true);
                player.sendMessage(Preset.instance.p.prefixName()+"Vous avez intimidé"+target+".");
                target.sendMessage(Preset.instance.p.prefixName()+"Kevin vous a intimidé");
                i++;
            }
        });
    }
}
