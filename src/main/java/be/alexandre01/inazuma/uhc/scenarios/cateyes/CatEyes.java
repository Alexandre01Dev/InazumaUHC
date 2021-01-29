package be.alexandre01.inazuma.uhc.scenarios.cateyes;

import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CatEyes extends Scenario {
    public CatEyes() {
        super("CatEyes", "Voir dans les caves");
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2,false,false), true);
                }
            }
        });
    }
}
