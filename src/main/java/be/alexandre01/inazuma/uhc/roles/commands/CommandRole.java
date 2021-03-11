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
    private ArrayList<Role> roles;
    private final HashMap<Role,Role.command> commands;
    private final RoleManager roleManager;
    private InazumaUHC inazumaUHC;
    public CommandRole(String name, Role.command command,Role role) {
        super(name);
        roles = new ArrayList<>();
        roles.add(role);
        this.roleManager = InazumaUHC.get.rm;
        this.commands = new HashMap<>();
        this.inazumaUHC = InazumaUHC.get;
        addCommand(role,command);
    }

    public void addRole(Role role){
        if(!roles.contains(role)){
            roles.add(role);

            System.out.println("ADDED ROLE >>"+  role );
        }
    }

    public void addCommand(Role role, Role.command command){
        commands.put(role,command);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Role role = roleManager.getRole(player.getUniqueId());
            if(inazumaUHC.spectatorManager.getPlayers().contains(sender)){
                player.sendMessage(inazumaUHC.p.p.prefixName()+"Désolé, mais tu ne peux plus utiliser la commande, t'es un homme mort.");
            }
            for(Role r : roles){
                System.out.println("roles: >>"+ r.getName());
            }
            System.out.println("role: >>"+ role.getName());
            if(!roles.contains(role)){
                player.sendMessage(Preset.instance.p.prefixName()+"§cLa commande n'est pas accessible depuis votre rôle.");
                return false;
            }

            commands.get(role).a(args,player);
            return false;
        }
        sender.sendMessage("§cLa commande est désactivé dans la console.");
        return false;
    }
}
