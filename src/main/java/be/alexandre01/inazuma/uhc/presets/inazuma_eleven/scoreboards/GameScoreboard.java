package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.scoreboards;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.scoreboard.IScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.ObjectiveSign;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class GameScoreboard {
    InazumaEleven inazuma;
    String scenario;
    public GameScoreboard(InazumaEleven inazuma){
        this.inazuma = inazuma;
        setScoreboard();
        if(inazuma.hasScenario()){
            if(!inazuma.getScenarios().isEmpty()){
            if(inazuma.getScenarios().size()> 1){
                scenario = "§a/scenario";
            }else {
                scenario = "§a"+inazuma.getScenarios().get(0).getName();
            }
        }else {
            scenario = "§cAucun";
        }
    }else {
            scenario = "§cAucun";
        }
    }
    public void setScoreboard(){
        inazuma.i = player -> {
            PersonalScoreboard ps = new PersonalScoreboard(player);
            ps.setIScore(new IScoreBoard() {

                @Override
                public void lines(String ip, ObjectiveSign objectiveSign) {
                    objectiveSign.setDisplayName("§8»§3§lInazuma§8§l-§3§lEleven§8«");

                    objectiveSign.setLine(4, "§r§l§8»§8§m------------§l§8«");
                    objectiveSign.setLine(5, "§7Joueurs §l» §e" + InazumaUHC.get.getRemainingPlayers().size());
                    objectiveSign.setLine(6,"§7Episode §l» §e"+ Episode.getEpisode());
                    objectiveSign.setLine(7, "§7Temps §l» §e" + InazumaEleven.totalTimeValue);
                    objectiveSign.setLine(8,    "§r§8§m*----§7§m--§7§m--§8§m----*");
                    objectiveSign.setLine(9, "§7"+ inazuma.pvpText+" §l» §e" + inazuma.pvpTime);
                    if(inazuma.hasNether()){
                        objectiveSign.setLine(10,"§7"+inazuma.netherText+" §l» §e" + inazuma.netherValue);
                    }
                    objectiveSign.setLine(11,"§7"+inazuma.bordureText+" §l» §e" + inazuma.bordureValue);
                    objectiveSign.setLine(17,    "§8§m*----§7§m--§7§m--§8§m----*");
                    World world = player.getWorld();
                    int borderSize = (int) world.getWorldBorder().getSize() /2;
                    objectiveSign.setLine(18,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);

                    objectiveSign.setLine(19,"§7Centre §l» §e "+ inazuma.getArrows().get(player.getUniqueId()));
                    objectiveSign.setLine(21, "§l§8»§8§m------------§l§8« ");

                    objectiveSign.setLine(21, "§7Scénario(s) §l» "+scenario);


                    objectiveSign.setLine(21, "§l§8»§8§m------------§l§8« ");
                    objectiveSign.setLine(22, ip);

                    objectiveSign.updateLines();
                }
            });
            return ps;
        };

    }
}

