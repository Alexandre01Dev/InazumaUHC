package be.alexandre01.inazuma.uhc.presets.inazuma_eleven.scoreboards;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.InazumaEleven;
import be.alexandre01.inazuma.uhc.scoreboard.IScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.ObjectiveSign;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class PreparingScoreboard {
    InazumaEleven inazuma;
    String scenario;
    public PreparingScoreboard(InazumaEleven inazuma){
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
                    objectiveSign.setDisplayName("§3»§b§lInazuma§8-§3Eleven§8«");

                    objectiveSign.setLine(4, "§r§l§8»§8§m------------§l§8«");
                    objectiveSign.setLine(6, "§7Joueurs §l» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e"+inazuma.getPlayerSize());
                    objectiveSign.setLine(7, "§7 §l» §cEn attentes du host§e");
                    objectiveSign.setLine(9, "§r§l§8»§8§m------------§l§8«§r");
                    World world = player.getWorld();
                    int borderSize = Preset.instance.p.getBorderSize(world.getEnvironment());
                    objectiveSign.setLine(10,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);
                    objectiveSign.setLine(11, "§l§8»§8§m------------§l§8«");

                    objectiveSign.setLine(13, "§7Scénario(s) §l» "+scenario);


                    objectiveSign.setLine(14, "§l§8»§8§m------------§l§8« ");
                    objectiveSign.setLine(15, ip);

                    objectiveSign.updateLines();

                }
            });
            return ps;
        };

    }
}

