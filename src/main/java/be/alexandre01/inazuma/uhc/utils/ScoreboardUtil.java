package be.alexandre01.inazuma.uhc.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardUtil {
    private HashMap<Scoreboard,ArrayList<Scoreboard>> scoreboards;
    public static ScoreboardUtil get;
    public ScoreboardUtil(){
        scoreboards = new HashMap<>();
    }

    public static void initialize(){
        get = new ScoreboardUtil();
    }
    public void addPlayer(Scoreboard sb, Team team, Player player){
        if(scoreboards.containsKey(sb)){
            ArrayList<Scoreboard> list = scoreboards.get(sb);

            for(Scoreboard newSb : list){
                Team t = newSb.getTeam(team.getName());
                if(t != null){
                    t.addPlayer(player);
                }
            }
        }
    }

    public void removePlayer(Scoreboard sb, Team team, Player player){
        if(scoreboards.containsKey(sb)){
            ArrayList<Scoreboard> list = scoreboards.get(sb);

            for(Scoreboard newSb : list){
                Team t = newSb.getTeam(team.getName());
                if(t != null){
                    t.removePlayer(player);
                }
            }
        }
    }
    public Scoreboard clone(Scoreboard sb,boolean dynamic){
        Scoreboard newSb = Bukkit.getScoreboardManager().getNewScoreboard();
        for(Objective objective : sb.getObjectives()){
            Objective sO = newSb.registerNewObjective(objective.getName(),objective.getCriteria());
            if(objective.getDisplayName() != null){
                sO.setDisplayName(objective.getDisplayName());
            }
            if(objective.getDisplaySlot() != null){
                sO.setDisplaySlot(objective.getDisplaySlot());
            }
        }

        for(Team team : sb.getTeams()){
            Team newTeam = newSb.getTeam(team.getName());
            if(newTeam==null){
                newTeam = newSb.registerNewTeam(team.getName());
            }

            for(OfflinePlayer tPlayer : team.getPlayers()){
                newTeam.addPlayer(tPlayer);
            }
            if(team.getDisplayName() != null){
                newTeam.setDisplayName(team.getDisplayName());
            }

            if(team.getPrefix() != null){
                newTeam.setPrefix(team.getPrefix());
            }

            if(team.getSuffix() != null){
                newTeam.setPrefix(team.getSuffix());
            }

            newTeam.setCanSeeFriendlyInvisibles(team.canSeeFriendlyInvisibles());

            if(team.getNameTagVisibility() != null){
                newTeam.setNameTagVisibility(team.getNameTagVisibility());
            }

            newTeam.setAllowFriendlyFire(team.allowFriendlyFire());

            for(String entry : team.getEntries()){
                newTeam.addEntry(entry);
            }
        }

        if(dynamic){
            if(!scoreboards.containsKey(sb)){
                scoreboards.put(sb, new ArrayList<>());
            }

            ArrayList<Scoreboard> list = scoreboards.get(sb);
            list.add(newSb);
            scoreboards.put(sb,list);
        }
        return newSb;
    }
}
