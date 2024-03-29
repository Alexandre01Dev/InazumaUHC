package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.PlayerUUIDConverter;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class CompoCommand extends Command {

    @Getter private static CompoCommand instance;
    public CompoCommand(String s) {
        super(s);
        super.setPermission("uhc.compo");
        instance = this;
    }
    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {

            if(!Role.isDistributed){
                sender.sendMessage(Preset.instance.p.prefixName() +"§cIl n'y a pas de rôle encore distribué.");
                return false;
            }
            sender.sendMessage(Preset.instance.p.prefixName()+" §7Voici la liste des rôles:");
            for(Role role : Role.getRoles()){
                if(role != null){
                    if(role.getName() != null){
                        if(!role.getInitialPlayers().isEmpty()){
                            role.getInitialPlayers().forEach(player -> {
                                String name = role.getRoleCategory().getPrefixColor()+role.getName();
                                if(role.getEliminatedPlayers().contains(player))
                                    name = "§8§m"+role.getName();


                                sender.sendMessage("§e- "+name+".");
                            });
                        }

                    }
                }

            }
            return false;
    }

}
