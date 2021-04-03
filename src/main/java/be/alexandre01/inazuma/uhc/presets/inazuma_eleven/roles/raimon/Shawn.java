package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.managers.damage.DamageManager;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius.Gazelle;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.alius.Torch;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Shawn extends Role {
    public Shawn(IPreset preset) {
        super("Shawn Frost",preset);

        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous possédez l’effet §6§lRésistance 1§7.");
        addDescription(" ");
        CustomComponentBuilder c = new CustomComponentBuilder("");
        c.append("§8- §7Vous possédez également le ");

        BaseComponent blizzardButton = new TextComponent("§3Blizzard Eternel §7*§8Curseur§7*");

        BaseComponent blizzardDesc = new TextComponent();
        blizzardDesc.addExtra("§e- §9Utilisation par §eEpisode\n");
        blizzardDesc.addExtra("§e- §9Donne au joueur §7ciblé, §8Slowness 2§9 pendant §a7 secondes");
        blizzardButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,blizzardDesc.getExtra().toArray(new BaseComponent[0])));
        c.append(blizzardButton);
        addDescription(c);
        addDescription(" ");
        addDescription("§8- §7Les attaques de §cTorch§7, §bGazelle§7 et §6Axel§7 ne vous atteignent pas.");

        setRoleToSpoil(Axel.class);
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                    inazumaUHC.dm.addEffectPourcentage(player, DamageManager.EffectType.RESISTANCE,1,115);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
            }

        });

        ItemBuilder itemBuilder = new ItemBuilder(Material.PACKED_ICE);
        itemBuilder.setName("§3Blizzard Eternel");

        RoleItem blizzardEternel = new RoleItem();


        blizzardEternel.deployVerificationsOnRightClickOnPlayer(blizzardEternel.generateVerification(new Tuple<>(RoleItem.VerificationType.EPISODES,1)));
        blizzardEternel.setRightClickOnPlayer(15,new RoleItem.RightClickOnPlayer() {
            @Override
            public void execute(Player player, Player rightClicked) {
                player.sendMessage(Preset.instance.p.prefixName()+"Vous avez utilisé votre §3Blizzard Eternel§7 sur §c"+ rightClicked.getName());
                if(!inazumaUHC.rm.getRole(rightClicked).getClass().equals(Torch.class) && !inazumaUHC.rm.getRole(rightClicked).getClass().equals(Axel.class) &&  !inazumaUHC.rm.getRole(rightClicked).getClass().equals(Gazelle.class)){
                rightClicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*10,1));
                    rightClicked.sendMessage(Preset.instance.p.prefixName()+"Tu as été touché par le §3Blizzard Eternel");
                }
                if(inazumaUHC.rm.getRole(rightClicked).getClass().equals(Torch.class) && inazumaUHC.rm.getRole(rightClicked).getClass().equals(Axel.class) &&  inazumaUHC.rm.getRole(rightClicked).getClass().equals(Gazelle.class)){
                    rightClicked.sendMessage(Preset.instance.p.prefixName()+"Tu as été touché par le §3Blizzard Eternel mais en vain");
                }
                player.sendMessage(Preset.instance.p.prefixName()+"Tu as utilisé ton §3Blizzard Eternel sur" + rightClicked.getName());
            }
        });

        blizzardEternel.setItemstack(itemBuilder.toItemStack());
        addRoleItem(blizzardEternel);

    }
}
