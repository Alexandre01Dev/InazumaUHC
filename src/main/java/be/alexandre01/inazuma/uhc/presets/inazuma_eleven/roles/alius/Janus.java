package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Janus extends Role {
    public Janus() {
        super("Janus");
        setRoleCategory(Alius.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
                }
            }
        });
    }
}
