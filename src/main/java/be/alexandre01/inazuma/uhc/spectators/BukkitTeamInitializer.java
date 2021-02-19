package be.alexandre01.inazuma.uhc.spectators;

import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class BukkitTeamInitializer {
    public static Team setAlive(Player player){
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

        if(score.getTeam(player.getName())==null){
            score.registerNewTeam(player.getName());
        }
        Team tV = score.getTeam(player.getName());
        tV.setPrefix("§7");
        tV.setCanSeeFriendlyInvisibles(false);
        tV.setNameTagVisibility(NameTagVisibility.ALWAYS);
        tV.setDisplayName(player.getName());
        tV.addPlayer(player);
        return tV;
    }
    public static void initialize(){

        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

        if(score.getTeam("0VIVANT")==null){
            score.registerNewTeam("0VIVANT");
        }
        Team tV = score.getTeam("0VIVANT");
        tV.setPrefix("§7");
        tV.setCanSeeFriendlyInvisibles(false);
        tV.setNameTagVisibility(NameTagVisibility.ALWAYS);

        if(score.getTeam("999MORT")==null){
            score.registerNewTeam("999MORT");
        }
        Team tM = score.getTeam("999MORT");
        tM.setPrefix("§8[MORT] ");

        tM.addEntry("collisionRule never");
        tM.setCanSeeFriendlyInvisibles(true);
    }
}
