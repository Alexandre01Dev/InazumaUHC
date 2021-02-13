package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.custom_events.player.PlayerInstantDeathEvent;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Alius;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Mark extends Role implements Listener {

    public Mark() {
        super("Mark Evans");
        setRoleCategory(Raimon.class);
       // setRoleToSpoil(Victoria);
        addListener(this);

        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
                }
            }
        });

        addCommand("corrupt", new command() {
            public int i = 0;
            @Override
            public void a(String[] args, Player player) {
                if(i > 2){
                    player.sendMessage(Preset.instance.p.prefixName()+" Vous avez dépassé le nombre d'utilisation de cette commande");
                    return;
                }
                int a = 0;
                for(Entity entity : player.getWorld().getNearbyEntities(player.getLocation(),25,25,25)){
                    if(entity instanceof Player){
                        Player p = (Player) entity;
                        if(inazumaUHC.rm.getRole(p).getRoleCategory().getClass().equals(Alius.class)){
                            a++;
                        }
                    }
                }
                player.sendMessage(Preset.instance.p.prefixName()+"Il y a "+a+" joueurs de l'Académie-Alius proche de vous.");
                i++;
            }
        });


    }
    @EventHandler
    public void onPlayerDeath(PlayerInstantDeathEvent event){
        if(inazumaUHC.rm.getRole(event.getPlayer()).getRoleCategory().getClass().equals(Raimon.class)){
            for(Player player : inazumaUHC.rm.getRole(event.getPlayer().getUniqueId()).getPlayers()){
                if(player.getMaxHealth() > 10){
                    player.setMaxHealth(player.getMaxHealth()-1);
                }
            }
        }
        if(getPlayers().contains(event.getPlayer())){
            for(Role role : inazumaUHC.rm.getRoleCategory(Raimon.class).getRoles()){
                role.getPlayers().forEach(p -> {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,60*2,1),true);
                });
            }
        }
    }
}
