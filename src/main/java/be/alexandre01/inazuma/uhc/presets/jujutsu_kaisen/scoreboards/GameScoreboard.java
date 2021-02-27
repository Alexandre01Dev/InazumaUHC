package be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.scoreboards;

import be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.Jujutsu_Kaisen;
import be.alexandre01.inazuma.uhc.presets.jujutsu_kaisen.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.scoreboard.IScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.ObjectiveSign;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class GameScoreboard {
    Jujutsu_Kaisen jujutsu_kaizen;
    String scenario;
    public GameScoreboard(Jujutsu_Kaisen jujutsu_kaizen){
        this.jujutsu_kaizen = jujutsu_kaizen;
        setScoreboard();
        if(jujutsu_kaizen.hasScenario()){
            if(!jujutsu_kaizen.getScenarios().isEmpty()){
            if(jujutsu_kaizen.getScenarios().size()> 1){
                scenario = "§a/scenario";
            }else {
                scenario = "§a"+jujutsu_kaizen.getScenarios().get(0).getName();
            }
        }else {
            scenario = "§cAucun";
        }
    }else {
            scenario = "§cAucun";
        }
    }
    public void setScoreboard(){
        jujutsu_kaizen.i = player -> {
            PersonalScoreboard ps = new PersonalScoreboard(player);
            ps.setIScore(new IScoreBoard() {

                @Override
                public void lines(String ip, ObjectiveSign objectiveSign) {
                    objectiveSign.setDisplayName("§8»§5§lJujutsu Kaisen§8«");
                    objectiveSign.setLine(4, "§r§l§8»§8§m------------§l§8«");
                    objectiveSign.setLine(5, "§7Joueurs §l» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e"+jujutsu_kaizen.getPlayerSize());
                    objectiveSign.setLine(6, "§r§l§8»§8§m------------§l§8«§f");
                    objectiveSign.setLine(7,"§cEpisode §l» §e"+ Episode.getEpisode());
                    objectiveSign.setLine(8, "§7"+ jujutsu_kaizen.pvpText+" §l» §e" + jujutsu_kaizen.pvpValue);
                    if(jujutsu_kaizen.hasNether()){
                        objectiveSign.setLine(9,"§7"+jujutsu_kaizen.netherText+" §l» §e" + jujutsu_kaizen.netherValue);
                    }
                    objectiveSign.setLine(10,"§7"+jujutsu_kaizen.bordureText+" §l» §e" + jujutsu_kaizen.bordureValue);
                    objectiveSign.setLine(16, "§r§l§8»§8§m------------§l§8«§r");
                    World world = player.getWorld();
                    int borderSize = (int) world.getWorldBorder().getSize() /2;
                    objectiveSign.setLine(17,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);

                    objectiveSign.setLine(18,"§7Centre §l» §e "+ jujutsu_kaizen.getArrows().get(player.getUniqueId()));
                    objectiveSign.setLine(19, "§l§8»§8§m------------§l§8«");

                    objectiveSign.setLine(20, "§7Scénario(s) §l» "+scenario);


                    objectiveSign.setLine(21, "§l§8»§8§m------------§l§8« ");
                    objectiveSign.setLine(22, ip);

                    objectiveSign.updateLines();
                }
            });
            return ps;
        };

    }
}

