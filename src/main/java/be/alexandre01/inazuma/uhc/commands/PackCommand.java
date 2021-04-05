package be.alexandre01.inazuma.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("pack")){
                player.sendMessage("§e§lInazuma §lEleven §e§7» " +  " §6§lNotre Pack §7:");
                player.sendMessage("§3§lInazuma §lEleven §7: §cbit.ly/3meCaAo");
            }
        }

        return false;
    }
}
