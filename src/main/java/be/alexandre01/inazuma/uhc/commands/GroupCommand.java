package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupCommand  extends Command {
    public GroupCommand(String s) {
        super(s);
        super.setPermission("uhc.groupe");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission("uhc.groupe")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("uhc.groupe")) {
                player.sendMessage("§cVous n'avez pas la permission d'exécuter cette commande");
            } else {
                player.hasPermission("uhc.groupe");

                if (args.length == 0) {
                    player.sendMessage("§e La commande est: /groupe §f [Nombre]");
                }

                if (args.length > 0) {
                    int args_num;
                    String reas = "";
                    for (args_num = 0; args_num < args.length; ++args_num) {
                        reas = reas + args[args_num] + " ";
                    }
                    for(Player players : Bukkit.getOnlinePlayers()){
                    TitleUtils.sendTitle(players, 10,30,25,"§c§l⚠ §cGroupe de §c§l" + reas + "§c§l⚠","§6Merci de respecter les groupes !");
                    players.playSound(player.getLocation(), Sound.NOTE_PLING, 1,1);
                }
                }

            }
        }
        return false;
    }
}