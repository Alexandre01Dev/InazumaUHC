package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Caleb extends Role {
    public Caleb() {
        super("Caleb Stonewall");
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1,false,false), true);
                }
            }
        });
    }
}
