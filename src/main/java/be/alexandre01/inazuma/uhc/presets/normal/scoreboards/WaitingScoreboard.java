package be.alexandre01.inazuma.uhc.presets.normal.scoreboards;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.normal.Normal;
import be.alexandre01.inazuma.uhc.scoreboard.IScoreBoard;
import be.alexandre01.inazuma.uhc.scoreboard.ObjectiveSign;
import be.alexandre01.inazuma.uhc.scoreboard.PersonalScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WaitingScoreboard {
    Normal normal;
    public WaitingScoreboard(Normal normal){
        this.normal = normal;
        setScoreboard();
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
                    objectiveSign.setLine(7, "§7 §l» §cEn attentes de joueurs§e");
                    objectiveSign.setLine(8, "§7 §l» §e§l"+Normal.lastModifier+"s.");
                    objectiveSign.setLine(9, "§r§l§8»§8§m------------§l§8«§r");
                    World world = player.getWorld();
                    int borderSize = Preset.instance.p.getBorderSize(world.getEnvironment());
                    objectiveSign.setLine(10,"§7Bordure §l» §e-"+ borderSize+"§7/§e"+borderSize);
                    objectiveSign.setLine(11, "§l§8»§8§m------------§l§8«");
                    if(normal.hasScenario()){
                        if(normal.getScenarios().size()> 1){
                            objectiveSign.setLine(13, "§7Scénarios §l» §8/scenario");
                        }else {
                            objectiveSign.setLine(13, "§7Scénario §l» §a"+normal.getScenarios().get(0).getName());
                        }

                    }else {
                        objectiveSign.setLine(13, "§7Scénario §l» §cAucun");
                    }

                    objectiveSign.setLine(14, "§l§8»§8§m------------§l§8« ");
                    objectiveSign.setLine(15, ip);

                    objectiveSign.updateLines();

                }
            });
            return ps;
        };

    }
}

