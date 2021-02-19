package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Xavier extends Role {
    public Xavier() {
        super("Xéné");
        setRoleCategory(Alius.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                }
            }
        });

        RoleItem roleItem = new RoleItem();
        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR).setName("§d§lCollier§7§l-§5§lAlius");
        roleItem.setItemstack(itemBuilder.toItemStack());
        roleItem.deployVerificationsOnRightClickOnPlayer(roleItem.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));
        roleItem.setRightClick(player -> {
            player.sendMessage(Preset.instance.p.prefixName()+" Vous venez d'utiliser votre §d§lCollier§7§l-§5§lAlius§7.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60*2*20, 0,false,false), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60*2*20, 0,false,false), true);
        });
        addRoleItem(roleItem);
        setRoleToSpoil(Bellatrix.class, Janus.class, Torch.class, Gazelle.class, Dvalin.class);
    }

}
