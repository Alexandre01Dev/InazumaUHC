package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous possédez l’effet §b§lSpeed 1 §7ainsi qu'un livre §3Depth Strider II§7.");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7Vous possédez également l' ");

        BaseComponent seaeffectButton = new TextComponent("§3§lAqua §3§lSea §7*§8Curseur§7*");

        BaseComponent seaeffectDesc = new TextComponent();
        seaeffectDesc.addExtra("§e- §9Utilisation 2 fois uniquement\n");
        seaeffectDesc.addExtra("§e- §9Permet de voir les §deffets§9 d'un joueur §9[§525 blocks§9]");
        seaeffectButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,seaeffectDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(seaeffectButton);
        addDescription(c);
        addDescription("§8- §7Toute les attaques de §4feu§7 ne vous atteignent pas.");

        onLoad(new load() {
            @Override
            public void a(Player player) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
                    ItemStack it = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) it.getItemMeta();
                    meta.addStoredEnchant(Enchantment.DEPTH_STRIDER,2,true);
                    it.setItemMeta(meta);
                    player.getInventory().addItem(it);
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
