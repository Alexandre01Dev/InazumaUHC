package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo.Byron;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Axel extends Role {
    public Axel() {
        super("Axel Blaze");
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1,false,false), true);
                }
            }
        });

    }

    @EventHandler
    public void onKillEvent(PlayerInstantDeathEvent event){
        Player killer = event.getPlayer().getKiller();
        if(killer != null){
            if (inazumaUHC.rm.getRole(killer.getUniqueId()).getClass().equals(Axel.class)){
                killer.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE));
            }
        }
    }
}
