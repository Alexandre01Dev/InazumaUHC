package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Jack extends Role implements Listener {

    private boolean isSneakTimer = false;
    private boolean invisible = false;
    public Jack(IPreset preset) {
        super("Jack Wallside",preset);
        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous disposez de §6§lRésistance 1§7 et également de §c§l2 §4❤§7 en plus.");
        addDescription(" ");
        addDescription("§8- §7Lorsque vous vous trouvez proche d'un joueur dans un rayon de 20 blocks, étant très peureux vous aurez Vitesse pendant 1 minute.");
        addDescription("§8- §7Si vous restez accroupit, au bout de 10 secondes vous deviendrez invisible sauf si un joueur se trouve dans un rayon de 30 blocks durant les 10 secondes.");
        addDescription("§8- §7Il vous suffira de vous desneak pour redevenir visible (Votre armure sera invisible ainsi que vos items dans votre main.");

        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a(Player player) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0,false,false), true);
                        player.setMaxHealth(24);
                        player.setHealth(24);
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
                damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*3,0,false,false),true);
            }
        }
    }

    @EventHandler
    public void onChangeItemSlot(PlayerItemHeldEvent event){
        if(invisible){
            Player player = event.getPlayer();
            if(getPlayers().contains(player)){
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        inazumaUHC.invisibilityInventory.setInventoryInvisibleToOther(player,0);
                    }
                }.runTaskLaterAsynchronously(InazumaUHC.get,1);

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
                            invisible = false;
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            inazumaUHC.invisibilityInventory.setInventoryToInitialToOther(player);
                            isSneakTimer = false;
                            Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getName());
                            t.setNameTagVisibility(NameTagVisibility.ALWAYS);
                            timer.cancel();
                        }
                        if(!PlayerUtils.getNearbyPlayersFromPlayer(player,30,30,30).isEmpty() && i < 10){
                            player.sendMessage(Preset.instance.p.prefixName()+"Il y a un joueur prêt de toi, tu ne peux pas utiliser ta technique");
                            isSneakTimer = false;
                            timer.cancel();
                        }
                        if(i == 10){
                            invisible = true;
                            player.sendMessage(Preset.instance.p.prefixName()+" Vous êtes camouflé.");
                            inazumaUHC.invisibilityInventory.setInventoryInvisibleToOther(player);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0,false,false), true);
                            Player p = event.getPlayer(); //Player to hide name
                            Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getName());
                            t.setNameTagVisibility(NameTagVisibility.NEVER);
                        }
                        i++;

                    }
                });

                timer.runTaskTimerAsynchronously(inazumaUHC,20,20);
            }

    }
}
