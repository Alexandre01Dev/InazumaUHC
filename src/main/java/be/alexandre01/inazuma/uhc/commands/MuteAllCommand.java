package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteAllCommand extends Command {
    public MuteAllCommand(String s) {
        super(s);
        super.setPermission("uhc.muteall");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("uhc.heal")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }
        if(args.length == 0){
            sender.sendMessage(Preset.instance.p.prefixName()+ " §cFaites /muteall on ou /muteall off");
        }

        if(args[0].equalsIgnoreCase("on")){
            return false;
        }
        if(args[0].equalsIgnoreCase("off")){
            return false;
        }
        return false;
    }
}
