package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MumbleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("mumble")){
                player.sendMessage("§e§lInazuma §lEleven §e§7» " + " §6§lLe §lmumble §7:");
                player.sendMessage("§9§lAdresse §7: §e§linazumauhc.mumble.gg");
                player.sendMessage("§9§lPort §7: §e§l10011");
                player.sendMessage("§9§lChannel §7: §e§lInazuma §lEleven");
            }
        }

        return false;
    }
}
