package be.alexandre01.inazuma.uhc.spectators;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class BukkitTeamInitializer {
    public static void initialize(){

        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

        if(score.getTeam("0VIVANT")==null){
            score.registerNewTeam("0VIVANT");
        }
        Team tV = score.getTeam("0VIVANT");
        tV.setPrefix("§7");
        tV.setCanSeeFriendlyInvisibles(false);


        if(score.getTeam("999MORT")==null){
            score.registerNewTeam("999MORT");
        }
        Team tM = score.getTeam("999MORT");
        tM.setPrefix("§8[MORT] ");

        tM.addEntry("collisionRule never");
        tM.setCanSeeFriendlyInvisibles(true);
    }
}
