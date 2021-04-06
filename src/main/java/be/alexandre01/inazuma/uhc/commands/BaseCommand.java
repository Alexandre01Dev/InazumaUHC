package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand extends Command {
    public BaseCommand(String s,String prefix) {
        super(s);
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(args.length == 0){
            sender.sendMessage(Preset.instance.p.prefixName()+"§7liste des commandes ");
            sender.sendMessage(Preset.instance.p.prefixName()+"§e-§9 /ina me ");
            return false;
        }

        if(args[0].equalsIgnoreCase("me")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(Role.isDistributed){
                    Role role = InazumaUHC.get.rm.getRole(player);
                    if(role == null){
                        return false;
                    }

                   role.spoilRole(player);
                }

            }
        }
        return false;
    }
}
