package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.host.Host;
import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import be.alexandre01.inazuma.uhc.state.State;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HostCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("host.admin")){
                player.sendMessage("Vous ne pouvez pas ouvrir le menu de host.");
                return false;
            }
            if(cmd.getName().equalsIgnoreCase("host")){
                if(InazumaUHC.get.isHosted && GameState.get().contains(State.PREPARING) && !InazumaUHC.get.worldGen.isGenerating()){
                    Host host = InazumaUHC.get.host;
                    host.createHostPanel(player);

                }else {
                    player.sendMessage("Vous ne pouvez plus ouvrir le menu de host.");
                }

            }
        }

        return false;
    }
}
