package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
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

        RoleItem blizzardEternel = new RoleItem();
        blizzardEternel.setRightClickOnPlayer(15,new RoleItem.RightClickOnPlayer() {
            int lastEpisode = 0;
            @Override
            public void execute(Player player, Player rightClicked) {
                if(lastEpisode == Episode.getEpisode()){
                    player.sendMessage("§cTu ne peux pas faire ta technique plus de 1 fois par Episode.");
                    return;
                }


                rightClicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*7,1));
                rightClicked.sendMessage(Preset.instance.p.prefixName()+"Tu as été touché par le §3Blizzard Eternel");
                lastEpisode = Episode.getEpisode();
            }
        });

        blizzardEternel.setItemstack(itemBuilder.toItemStack());
        addRoleItem(blizzardEternel);

        //A CODER, UN SYSTEME DE RIGHTCLICK AU JOUEUR AU DESSUS DE LA REACH NORMAL.
    }
}
