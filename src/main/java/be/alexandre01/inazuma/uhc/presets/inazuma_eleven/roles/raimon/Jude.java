package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.ScoreboardSign;
import be.alexandre01.inazuma.uhc.utils.ScoreboardUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

public class Jude extends Role implements Listener {
    public Jude() {
        super("Jude Sharp");
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                Bukkit.getScheduler().scheduleSyncDelayedTask(InazumaUHC.get, new Runnable() {
                    @Override
                public void run() {
                        Scoreboard score = ScoreboardUtil.get.clone(Bukkit.getScoreboardManager().getMainScoreboard(),true);

                        if(score.getObjective("showhealth")==null){
                            Objective o = score.registerNewObjective("showhealth","health");
                            System.out.println("SHOWHEALTH SETUP");
                            o.setDisplayName("§c❤");
                            o.setDisplaySlot(DisplaySlot.BELOW_NAME);
                        }
                  for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
                      player.setScoreboard(score);
                    for(Player opposants : Bukkit.getOnlinePlayers()){
                       // new ScoreboardScore(s, o, "§c❤").setScore(2);

                        player.setHealth(player.getHealth());
                        opposants.setHealth(opposants.getHealth());
                    }
                  }

                    }

                   });


                }

        });
    }
}