package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.timers.ITimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.utils.PlayerUtils;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
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
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class Jack extends Role implements Listener {
    private BukkitTask b;
    private boolean isSneakTimer = false;
    private boolean invisible = false;
    public Jack(IPreset preset) {
        super("Jack Wallside",preset);
        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous disposez de §6§lRésistance 1§7 et également de §c§l2 §4❤§7 permanents.");
        addDescription(" ");
        addDescription("§8- §7Lorsque vous vous trouvez proche d'un joueur ayant activé son collier-alius dans un rayon de 20 blocks, étant très peureux vous aurez §b§lSpeed 1§7 pendant 1 minute.");
        addDescription(" ");
        addDescription("§8- §7Si vous restez accroupi, au bout de 10 secondes vous deviendrez invisible sauf si un joueur se trouve dans un rayon de 30 blocks durant les 10 secondes.");
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
                if (invisible){

                    invisible = false;
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    inazumaUHC.invisibilityInventory.setInventoryToInitialToOther(player);
                    isSneakTimer = false;
                    Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(0+player.getName());
                    t.setNameTagVisibility(NameTagVisibility.ALWAYS);
                    b.cancel();

                }
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


            isSneakTimer = true;
            if(b != null){
                try {
                    b.cancel();
                }catch (Exception ignored){

                }

            }
             b = new BukkitRunnable() {
                int i = 10;
                @Override
                public void run() {
                    if(!player.isSneaking()){
                        invisible = false;
                        player.removePotionEffect(PotionEffectType.INVISIBILITY);
                        inazumaUHC.invisibilityInventory.setInventoryToInitialToOther(player);
                        isSneakTimer = false;
                        Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(0+player.getName());
                        t.setNameTagVisibility(NameTagVisibility.ALWAYS);
                        b.cancel();
                    }
                    if(!PlayerUtils.getNearbyPlayersFromPlayer(player,20,12,20).isEmpty() && i < 10){
                        player.sendMessage(Preset.instance.p.prefixName()+"Il y a un joueur prêt de toi, tu ne peux pas utiliser ta technique");
                        isSneakTimer = false;
                        b.cancel();
                    }
                    if (i > 0){
                        TitleUtils.sendActionBar(player,"§7Vous allez être Invisible dans §f" + i + " §7seconde(s)");
                    }
                    if(i == 0){
                        invisible = true;
                        TitleUtils.sendActionBar(player,"§7Vous êtes camouflé");
                        player.sendMessage(Preset.instance.p.prefixName()+" Vous êtes camouflé.");
                        inazumaUHC.invisibilityInventory.setInventoryInvisibleToOther(player);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0,false,false), true);
                        Player p = event.getPlayer();
                        Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(0+player.getName());
                        t.setNameTagVisibility(NameTagVisibility.NEVER);
                    }
                    i--;
                }
            }.runTaskTimerAsynchronously(inazumaUHC,20,20);
        }
    }
}
