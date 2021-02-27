package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Shawn extends Role {
    public Shawn() {
        super("Shawn Frost");
        setRoleToSpoil(Axel.class);
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
            }

        });

        ItemBuilder itemBuilder = new ItemBuilder(Material.STICK);
        itemBuilder.setName("§3Blizzard Eternel");

        RoleItem blizzardEternel = new RoleItem();


        blizzardEternel.deployVerificationsOnRightClickOnPlayer(blizzardEternel.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));
        blizzardEternel.setRightClickOnPlayer(15,new RoleItem.RightClickOnPlayer() {
            @Override
            public void execute(Player player, Player rightClicked) {
                player.sendMessage(Preset.instance.p.prefixName()+"Vous avez utilisé votre §3Blizzard Eternel§7 sur §c"+ rightClicked.getName());
                rightClicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*7,1));
                rightClicked.sendMessage(Preset.instance.p.prefixName()+"Tu as été touché par le §3Blizzard Eternel");
            }
        });

        blizzardEternel.setItemstack(itemBuilder.toItemStack());
        addRoleItem(blizzardEternel);

    }
}
