package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Xavier extends Role {
    public Xavier(String name) {
        super(name);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                }
            }
        });

        RoleItem roleItem = new RoleItem();
        roleItem.setRightClick(player -> {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*2, 0,false,false), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60*2, 0,false,false), true);
        });
        setRoleToSpoil(Bellatrix.class, Janus.class, Torch.class, Gazelle.class, Dvalin.class);
    }

}
