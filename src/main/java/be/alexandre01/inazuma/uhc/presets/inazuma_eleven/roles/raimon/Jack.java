package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Jack extends Role implements Listener {

    private boolean isSneakTimer = false;
    public Jack() {
        super("Jack Wallside");
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                for(Player player : getPlayers()){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
                        player.setMaxHealth(24);
                        player.setHealth(24);
                }
            }
        });

        addListener(this);
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();


            if(getPlayers().contains(damager)){
                damager.removePotionEffect(PotionEffectType.SLOW);
                damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*10,0,false,false),true);
            }
        }
    }
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        event.setAsync(true);
        if(event.isSneaking() && !isSneakTimer){
            Player player = event.getPlayer();
            if(!getPlayers().contains(player)){
                return;
            }

                Timer timer = new Timer("jacksneaktimer",true);
                timer.setSetup(new Timer.setup() {
                    @Override
                    public Timer setInstance() {
                        return null;
                    }
                });

                timer.setTimer(new ITimer() {
                    int i;
                    @Override
                    public void preRun() {
                        i = 1;
                        isSneakTimer = true;
                    }

                    @Override
                    public void run() {
                        if(!player.isSneaking()){
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            isSneakTimer = false;
                            timer.cancel();
                        }
                        if(!PlayerUtils.getNearbyPlayersFromPlayer(player,30,30,30).isEmpty() && i < 10){
                            player.sendMessage(Preset.instance.p.prefixName()+"Il y a un joueur prêt de toi, tu ne peux pas utiliser ta technique");
                            isSneakTimer = false;
                            timer.cancel();
                        }
                        if(i == 10){
                            player.sendMessage(Preset.instance.p.prefixName()+" Vous êtes camouflé.");
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0,false,false), true);
                        }
                        i++;

                    }
                });

                timer.runTaskTimerAsynchronously(inazumaUHC,20,20);
            }

    }
}
