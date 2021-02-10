package be.alexandre01.inazuma.uhc.roles.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.roles.RoleManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandRole extends Command {
    private final ArrayList<Role> roles;
    private final HashMap<Role,Role.command> commands;
    private final RoleManager roleManager;
    public CommandRole(String name, Role.command command,Role role) {
        super(name);
        roles = new ArrayList<>();
        roles.add(role);
        this.roleManager = InazumaUHC.get.rm;
        this.commands = new HashMap<>();
        addCommand(role,command);
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public void addCommand(Role role, Role.command command){
        commands.put(role,command);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Role role = roleManager.getRole(player.getUniqueId());
            if(!roles.contains(roleManager.getRole(player.getUniqueId()))){
                player.sendMessage(Preset.instance.p.prefixName()+"§cLa commande n'est pas accessible depuis votre rôle.");
            }

            commands.get(role).a(args,player);
            return false;
        }
        sender.sendMessage("§cLa commande est désactivé dans la console.");
        return false;
    }
}
