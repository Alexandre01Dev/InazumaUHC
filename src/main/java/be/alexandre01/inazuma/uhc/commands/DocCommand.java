package be.alexandre01.inazuma.uhc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DocCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("doc")){
                player.sendMessage("§e§lInazuma §lEleven §e§7» " +  " §6§lNos §6§lG§7-§6§lDoc §7:");
                player.sendMessage("§3§lInazuma §lEleven §7: §chttps://app.gitbook.com/@inazumauhcpro/s/inazuma-gitbook/inazuma-eleven-uhc/inazuma-eleven-uhc");
            }
        }

        return false;
    }
}