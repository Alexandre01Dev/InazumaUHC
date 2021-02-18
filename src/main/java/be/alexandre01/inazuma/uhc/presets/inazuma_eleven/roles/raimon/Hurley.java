package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Hurley extends Role {

    public Hurley() {
        super("Hurley Kane");
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
                    ItemStack it = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) it.getItemMeta();
                    meta.addStoredEnchant(Enchantment.DEPTH_STRIDER,2,true);
                    it.setItemMeta(meta);
                    player.getInventory().addItem(it);
                }
            }
        });

        RoleItem roleItem = new RoleItem();
        ItemBuilder itemBuilder = new ItemBuilder(Material.BLAZE_ROD);
        itemBuilder.setName("?");

        roleItem.deployVerificationsOnRightClickOnPlayer(roleItem.generateMultipleVerification(new Tuple<>(RoleItem.VerificationType.COOLDOWN,3),new Tuple<>(RoleItem.VerificationType.USAGES,2)));
        roleItem.setRightClickOnPlayer(25,(player, rightClicked) -> {
            player.sendMessage(Preset.instance.p.prefixName()+" Voici les effets de §e"+rightClicked.getName()+"§7:");
            for(PotionEffect potionEffect : rightClicked.getActivePotionEffects()){
               player.sendMessage("§e-§9"+potionEffect.getType().getName());
            }

            player.sendMessage(Preset.instance.p.prefixName()+" §cAttention§7, celui-ci sera prévenu dans 1 minute et 30 secondes que ton rôle à regarder ses effets.");
            Bukkit.getScheduler().runTaskLaterAsynchronously(inazumaUHC, new BukkitRunnable() {
                @Override
                public void run() {
                    rightClicked.sendMessage(Preset.instance.p.prefixName() +" Tu viens d'apprendre qu' "+getRoleCategory().getPrefixColor()+getName()+" connait désormais tes effets.");
                }
            }, 20 * 90);
        });
    }
}
