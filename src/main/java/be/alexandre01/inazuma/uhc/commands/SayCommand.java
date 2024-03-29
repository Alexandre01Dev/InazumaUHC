package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCommand  extends Command {
    public SayCommand(String s) {
        super(s);
        super.setPermission("uhc.say");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission("uhc.say")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;



                if (!player.hasPermission("uhc.say")) {
                    player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande (Héhé)");
                } else {
                    player.hasPermission("uhc.say");

                    if (args.length == 0) {
                        player.sendMessage("§e La commande est: /say §f [Message]");
                    }

                    if (args.length > 0) {
                        int args_num;
                        String reas = "";
                        for (args_num = 0; args_num < args.length; ++args_num) {
                            reas = reas + args[args_num] + " ";
                        }
                        Bukkit.broadcastMessage(" ");
                        Bukkit.broadcastMessage("§c§lHOST " + player.getName() + "§c§» " + reas);
                        Bukkit.broadcastMessage(" ");
                    }

                }
            }
        return false;
    }
}
