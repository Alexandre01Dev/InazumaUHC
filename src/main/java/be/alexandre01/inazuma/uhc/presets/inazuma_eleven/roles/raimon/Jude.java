package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.CustomComponentBuilder;
import be.alexandre01.inazuma.uhc.utils.ScoreboardSign;
import be.alexandre01.inazuma.uhc.utils.ScoreboardUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

public class Jude extends Role implements Listener {

    private Scoreboard score = null;
    public Jude(IPreset preset) {
        super("Jude Sharp",preset);
        setRoleCategory(Raimon.class);
        addListener(this);

        addDescription("§8- §7Votre objectif est de gagner avec §6§lRaimon");
        addDescription("§8- §7Vous possédez l’effet §b§lSpeed 1§7.");
        addDescription("§8- §7Vous voyez également la §4vie§7 des joueurs au dessus de leurs têtes.");
        addDescription("§8- §7Vous recevrez les coordonnées approximatives à chaque utilisation d'un §d§lCollier§7§l-§5§lAlius§7.");


        onLoad(new load() {
            @Override
            public void a(Player player) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(InazumaUHC.get, new Runnable() {
                    @Override
                public void run() {
                        score = ScoreboardUtil.get.clone(Bukkit.getScoreboardManager().getMainScoreboard(),true);

                        if(score.getObjective("showhealth")==null){
                            Objective o = score.registerNewObjective("showhealth","dummy");
                            System.out.println("SHOWHEALTH SETUP");
                            o.setDisplayName("%§c❤");
                            o.setDisplaySlot(DisplaySlot.BELOW_NAME);
                        }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
                      player.setScoreboard(score);
                      setHealth(player);
                    for(Player opposants : Bukkit.getOnlinePlayers()){
                       // new ScoreboardScore(s, o, "§c❤").setScore(2);
                        setHealth(opposants);

                    }

                    }

                   });


                }

        });


    }
    private void setHealth(Player player){
        int pourcentage  = (int) ((100*Math.round(player.getHealth())) /Math.round(player.getMaxHealth()));
        Score s = score.getObjective("showhealth").getScore(player);
        s.setScore(pourcentage);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            setHealth(player);
        }
    }

    @EventHandler
    public void onHeart(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            setHealth(player);
        }
    }
}