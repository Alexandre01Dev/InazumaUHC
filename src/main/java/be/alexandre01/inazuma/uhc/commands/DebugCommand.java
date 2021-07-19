package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(!sender.hasPermission("debug.use"))
            return false;


            if(cmd.getName().equalsIgnoreCase("debug")){
                if(InazumaUHC.get.debugMode){
                    InazumaUHC.get.debugMode = false;
                    sender.sendMessage("§cLe DEBUG mode a été désactivé !");
                }else {
                    InazumaUHC.get.debugMode = true;
                    sender.sendMessage("§aLe DEBUG mode a été activé !");
                }
            }

        return true;
    }
}