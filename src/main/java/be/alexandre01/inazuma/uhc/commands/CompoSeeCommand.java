package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.utils.PlayerUUIDConverter;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class CompoSeeCommand extends Command {

    @Getter private static CompoSeeCommand instance;
    public CompoSeeCommand(String s) {
        super(s);
        super.setPermission("uhc.compo");
        instance = this;
    }
    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("uhc.compo")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }

            for(Role role : Role.getRoles()){
                if(role != null){
                    if(role.getName() != null){
                        if(!role.getInitialPlayers().isEmpty()){
                            role.getInitialPlayers().forEach(player -> {
                                String name = "§b"+ PlayerUUIDConverter.getPlayerName(player);
                                if(role.getEliminatedPlayers().contains(player))
                                    name = "§8§m"+PlayerUUIDConverter.getPlayerName(player);


                                sender.sendMessage("§e- "+name+"§7 est "+ role.getRoleCategory().getPrefixColor()+role.getName());
                            });

                        }

                    }
                }

            }
            return false;

    }

}
