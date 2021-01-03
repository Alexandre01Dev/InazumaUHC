package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ScenarioCommand implements CommandExecutor {
    IPreset iPreset;
    public ScenarioCommand(){
        iPreset = Preset.instance.p;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("scenario")){
                ArrayList<Scenario> scenarios = iPreset.getScenarios();
                player.sendMessage("§9Scénarios activés:");
                for(Scenario s : scenarios){
                    BaseComponent b = new TextComponent("§e§l- §a"+s.getName());
                    b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§e"+s.getDescription()).create()));
                    player.spigot().sendMessage(b);

                }
            }
        }

        return false;
    }
}
