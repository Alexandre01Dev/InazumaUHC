package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.PlayerUUIDConverter;
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
                return false;
            }

        }
        if(args[0].equalsIgnoreCase("compo")){
            if(!Role.isDistributed){
                sender.sendMessage(Preset.instance.p.prefixName() +"§cIl n'y a pas de rôle encore distribué.");
                return false;
            }
            sender.sendMessage(Preset.instance.p.prefixName()+" §eVoici la liste des rôles encore en vie");
            for(Role role : Role.getRoles()){
                if(role != null){
                    if(role.getName() != null){
                        if(!role.getInitialPlayers().isEmpty()){
                            role.getInitialPlayers().forEach(player -> {
                                String name = "§b"+ role.getName();
                                if(role.getEliminatedPlayers().contains(player))
                                    name = "§8§m"+role.getRoleCategory().getPrefixColor()+role.getName();


                                sender.sendMessage("§e- "+name+".");
                            });

                        }

                    }
                }

            }
        }

        return false;
    }
}
