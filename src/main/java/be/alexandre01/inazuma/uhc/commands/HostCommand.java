package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
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

public class HostCommand implements CommandExecutor {
    IPreset iPreset;
    public HostCommand(){
        iPreset = Preset.instance.p;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("host")){
                if(InazumaUHC.get.isHosted && GameState.get().contains(State.PREPARING) && !InazumaUHC.get.worldGen.isGenerating()){
                    player.openInventory(InazumaUHC.get.host.getInv());
                }else {
                    player.sendMessage("Vous ne pouvez plus ouvrir le menu de host");
                }

            }
        }

        return false;
    }
}
