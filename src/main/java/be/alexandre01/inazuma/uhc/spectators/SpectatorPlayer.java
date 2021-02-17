package be.alexandre01.inazuma.uhc.spectators;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.utils.ScoreboardUtil;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SpectatorPlayer{

    private Player player;


    public SpectatorPlayer(Player player){
      this.player = player;
    }
    public void setInvisibleTeam(){

            Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();
            Team oldT = score.getTeam("0VIVANT");
            if(oldT != null){
                oldT.removePlayer(player);
                ScoreboardUtil.get.removePlayer(score,oldT,player);
            }
            String name;

            Team t = null;

            t = score.getTeam("999MORT");

            t.setDisplayName(player.getName());


            t.addPlayer(player);
            ScoreboardUtil.get.addPlayer(score,t,player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2,false,false), true);
            player.setScoreboard(score);

    }
    public void setSpectator(){
        for(Player players : InazumaUHC.get.getRemainingPlayers()){
            if(players != player){
                players.hidePlayer(player);
            }
        }

        for(Player players: InazumaUHC.get.spectatorManager.getPlayers()){
            player.showPlayer(players);
        }
        setInvisibleTeam();
        player.setGameMode(GameMode.ADVENTURE);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public void setCamera(Entity entity){
        PacketPlayOutCamera p = new PacketPlayOutCamera(((CraftEntity)entity).getHandle());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(p);
        if(entity != player){
            TitleUtils.sendActionBar(player,"§eTu es entrain d'observer "+ entity.getName());
        }
    }
}
