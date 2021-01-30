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
                ArrayList<Class<?>> c = iPreset.getScenarios();
                player.sendMessage("§9Scénarios activés:");
                if(c.isEmpty()) {
                    return false;
                }

                for(Class<?> sc : c){
                    if(Scenario.getScenarios().containsKey(sc)){
                        Scenario scenario = Scenario.getScenarios().get(sc);
                      //  scenarios.remove(scenario);
                        BaseComponent b = new TextComponent("§e§l- §a"+scenario.getName());
                        b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§e"+scenario.getDescription()).create()));
                        player.spigot().sendMessage(b);
                    }
                }

            }
        }

        return false;
    }
}
