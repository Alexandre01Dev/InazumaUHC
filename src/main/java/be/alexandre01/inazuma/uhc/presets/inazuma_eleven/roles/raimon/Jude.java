package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.roles.raimon;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.categories.Raimon;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.ScoreboardSign;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

public class Jude extends Role {
    public Jude() {
        super("Jude Sharp");
        setRoleCategory(Raimon.class);
        onLoad(new load() {
            @Override
            public void a() {
                Bukkit.getScheduler().scheduleSyncDelayedTask(InazumaUHC.get, new Runnable() {
                    @Override
                public void run() {
                  for(Player player : getPlayers()){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0,false,false), true);
                    Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
                    for(Player opposants : Bukkit.getOnlinePlayers()){
                                if(score.getObjective("§c❤")==null){
                                    Objective o = score.registerNewObjective("§c❤",Criterias.HEALTH+" §c❤");
                                    o.setDisplaySlot(DisplaySlot.BELOW_NAME);
                                }
                        net.minecraft.server.v1_8_R3.Scoreboard s = ((CraftScoreboard)score).getHandle();
                        ScoreboardObjective o = s.getObjective("§c❤");

                        new ScoreboardScore(s, o, "§c❤").setScore(2);
                        PacketPlayOutScoreboardDisplayObjective p = new PacketPlayOutScoreboardDisplayObjective(2,o);
                        PacketPlayOutScoreboardObjective po = new PacketPlayOutScoreboardObjective(o,2);
                        PacketPlayOutScoreboardScore scorePacket = new PacketPlayOutScoreboardScore(   s.getPlayerScoreForObjective("§c❤",o));


                        EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
                        EntityPlayer nmsOpposant = ((CraftPlayer)opposants).getHandle();
                        nmsPlayer.playerConnection.sendPacket(scorePacket);
                        nmsPlayer.playerConnection.sendPacket(p);
                        nmsOpposant.playerConnection.sendPacket(po);
                        nmsPlayer.playerConnection.sendPacket(po);
                        nmsOpposant.playerConnection.sendPacket(p);
                        nmsOpposant.playerConnection.sendPacket(scorePacket);

                    }
                  }

                    }

                   });


                }

        });
    }
}
