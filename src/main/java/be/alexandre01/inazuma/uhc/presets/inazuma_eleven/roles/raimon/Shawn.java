package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Shawn extends Role {
    public Shawn() {
        super("Shawn Frost");
        setRoleToSpoil(Axel.class);
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
                }
            }

        });

        ItemBuilder itemBuilder = new ItemBuilder(Material.STICK);
        itemBuilder.setName("§3Blizzard Eternel");

        //A CODER, UN SYSTEME DE RIGHTCLICK AU JOUEUR AU DESSUS DE LA REACH NORMAL.
    }
}
