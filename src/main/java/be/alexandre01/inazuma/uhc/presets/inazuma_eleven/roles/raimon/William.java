package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class William extends Role {
    public William() {
        super("William Glass");
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 0,false,false), true);
                }
            }
        });
    }
}
