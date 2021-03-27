package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius;

import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon.Jude;
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
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Joseph extends Role {


    public Joseph(IPreset preset) {
        super("Joseph King",preset);

        addDescription("§8- §7Votre objectif est de gagner avec §5§ll'§5§lAcadémie §5§lAlius");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7Vous avez un iteme nommée ");

        BaseComponent morsureButton = new TextComponent("§2Morsure§7-§2Sauvage §7*§8Curseur§7*");

        BaseComponent morsureDesc = new TextComponent();
        morsureDesc.addExtra("§e- §9Utilisation 3 fois uniquement §7[Cooldown de §a10 minutes§7]\n");
        morsureDesc.addExtra("§e- §9Vous donnera §6§lRésistance 2§9 pendant §a2 minutes\n");
        morsureDesc.addExtra("§e- §9La première utilisation vous mettra §8§lFaiblesse 1§7 pendants §a2 minutes\n");
        morsureDesc.addExtra("§e- §9La deuxieme utilisation vous mettra §8§lFaiblesse 1§7 pendants §a5 minutes\n");
        morsureDesc.addExtra("§e- §9La troisieme utilisation vous mettra §8§lFaiblesse 1§7 permanent");
        morsureButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,morsureDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(morsureButton);
        addDescription(c);

        setRoleCategory(Alius.class);

        RoleItem morsure = new RoleItem();
        morsure.setItemstack(new ItemBuilder(Material.GHAST_TEAR).setName("§2Morsure§7-§2Sauvage").toItemStack());
        morsure.setSlot(7);
        morsure.deployVerificationsOnRightClick(morsure.generateVerification(new Tuple<>(RoleItem.VerificationType.COOLDOWN,60*10)));
        morsure.setRightClick(new RoleItem.RightClick() {
            int i = 0;
            @Override
            public void execute(Player player) {
                i++;
                switch (i){
                    case 1:
                        addEffectAfter(player,2*20*60,2*60*20,PotionEffectType.WEAKNESS);
                        break;
                    case 2:
                        addEffectAfter(player,2*20*60,5*60*20,PotionEffectType.WEAKNESS);
                        break;
                    case 3:
                        addEffectAfter(player,2*20*60,Integer.MAX_VALUE,PotionEffectType.WEAKNESS);
                        break;
                    default:
                        player.sendMessage(Preset.instance.p.prefixName()+" Tu as déjà atteint la limite d'utilisation");
                        return;
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2*60*20, 1,false,false), true);
                inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.RESISTANCE,2,120);
                player.sendMessage(Preset.instance.p.prefixName()+" Tu viens de recevoir l'effet RESISTANCE pendant 2 minutes.");
            }
        });
        addRoleItem(morsure);

    }
    private void addEffectAfter(Player player,long l, int t, PotionEffectType p){
        Bukkit.getScheduler().runTaskLaterAsynchronously(inazumaUHC, new Runnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(p,t, 0,false,false), true);
            }
        },l);
    }
}
