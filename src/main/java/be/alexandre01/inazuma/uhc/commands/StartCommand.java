package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.scenarios.Scenario;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StartCommand implements CommandExecutor{

        IPreset iPreset;
        public StartCommand(){
            iPreset = Preset.instance.p;
        }
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(cmd.getName().equalsIgnoreCase("start")){
                    player.sendMessage("Start de la game");
                    GameState.get().setTo(State.STARTING);
                    }
                }


            return false;
        }
    }

