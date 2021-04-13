package be.alexandre01.inazuma.uhc.presets.normal.scoreboards;

import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.scoreboard.IPersonalScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.IScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.ObjectiveSign;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GameScoreboard {
    PresetData normal;
    String scenario;
    public GameScoreboard(PresetData normal){
        this.normal = normal;
        setScoreboard();
        if(normal.hasScenario()){
            if(!normal.getScenarios().isEmpty()){

            if(normal.getScenarios().size()> 1){
                scenario = "§a/scenario";
            }else {
                scenario = "§a"+normal.getScenarios().get(0).getName();
            }
        }else {
            scenario = "§cAucun";
        }

        }else {
            scenario = "§cAucun";
        }
    }

    public void setScoreboard(){
        normal.i = player -> {
            PersonalScoreboard ps = new PersonalScoreboard(player);
            ps.setIScore(new IScoreBoard() {

                @Override
                public void lines(String ip, ObjectiveSign objectiveSign) {
                    objectiveSign.setDisplayName("§8»§6§lInazumaUHC§8«");

                    objectiveSign.setLine(0, "§l§8»§8§m------------§l§8«§r");
                    objectiveSign.setLine(2,"§f§l» §a§lUHC§a CLASSIC §f§l«");
                    objectiveSign.setLine(4, "§r§l§8»§8§m------------§l§8«");
                    objectiveSign.setLine(6, "§7Joueurs §l» §e" + Bukkit.getOnlinePlayers().size() + "§7/§e"+normal.getPlayerSize());
                    objectiveSign.setLine(7, "§r§l§8»§8§m------------§l§8«§f");
                    objectiveSign.setLine(8, "§7"+ normal.pvpText+" §l» §e" + normal.pvpValue);
                    if(normal.hasNether()){
                        objectiveSign.setLine(9,"§7"+normal.netherText+" §l» §e" + normal.netherValue);
                    }
                    objectiveSign.setLine(10,"§7"+normal.bordureText+" §l» §e" + normal.bordureValue);
                    objectiveSign.setLine(16, "§r§l§8»§8§m------------§l§8«§r");
                    World world = player.getWorld();
                    int borderSize = (int) world.getWorldBorder().getSize() /2;
                    objectiveSign.setLine(17,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);

                    objectiveSign.setLine(18,"§7Centre §l» §e "+ normal.getArrows().get(player.getUniqueId()));
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

