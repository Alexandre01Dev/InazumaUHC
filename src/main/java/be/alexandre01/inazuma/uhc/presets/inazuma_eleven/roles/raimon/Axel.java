package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo.Byron;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import be.alexandre01.inazuma.uhc.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Axel extends Role {
    public Axel() {
        super("Axel Blaze");
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
                }
            }
        });
        RoleItem roleItem = new RoleItem();
        ItemBuilder itemBuilder = new ItemBuilder(Material.BLAZE_ROD).setName("§4§lTornade §c§lDe §4§lFeu");
        roleItem.setItemstack(itemBuilder.toItemStack());
        roleItem.setRightClick(player -> {
            player.sendMessage(Preset.instance.p.prefixName()+" Vous venez d'utiliser la §4§lTornade §c§lDe §4§lFeu§7.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 90*20, 1,false,false), true);
        });
        addRoleItem(roleItem);
    }

    @EventHandler
    public void onKillEvent(PlayerInstantDeathEvent event){
        Player killer = event.getPlayer().getKiller();
        if(killer != null){
            if (inazumaUHC.rm.getRole(killer.getUniqueId()).getClass().equals(Axel.class)){
                killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
            }
        }
    }
}
