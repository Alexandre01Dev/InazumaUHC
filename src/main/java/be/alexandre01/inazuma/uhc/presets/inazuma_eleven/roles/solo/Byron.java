package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.solo;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Byron extends Role implements Listener {
    public Byron() {
        super("Byron Love");

        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0,false,false), true);
                    player.setMaxHealth(24);
                    player.setHealth(24);
                }
            }
        });

        ItemStack pot1 =  new Potion(PotionType.SPEED, 1).toItemStack(1);
        PotionMeta meta = (PotionMeta) pot1.getItemMeta();
        meta.clearCustomEffects();
        pot1.setItemMeta(meta);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 10*20, 1,false,false), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20*20, 0,false,false), true);
        meta.setDisplayName("§fNectar §7Divin");
        meta.setLore(Arrays.asList("Boisson Divine légué par §fDieu§r lui même","cette boisson vous rendra §fimmortel§r durant un certain moment."));
        List<String> potLore = new ArrayList<String>();
        meta.setLore(potLore);
        pot1.setItemMeta(meta);
        RoleItem potion = new RoleItem();
        potion.setItemstack(pot1);
        addRoleItem(potion);
        addListener(this);
    }
    @EventHandler
    public void onKillEvent(PlayerInstantDeathEvent event){
        Player killer = event.getPlayer().getKiller();
        if(killer != null){
            if (inazumaUHC.rm.getRole(killer.getUniqueId()).getClass().equals(Byron.class)){
                killer.setMaxHealth(killer.getMaxHealth()+1);
            }
        }
    }
}
