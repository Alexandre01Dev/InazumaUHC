package be.alexandre01.inazuma.uhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvSeeCommand extends Command {
    public InvSeeCommand(String s) {
        super(s);
        super.setPermission("uhc.invsee");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        Player player = (Player)sender;

        if (args.length != 1) {
           sender.sendMessage("Veuillez ne pas foutre de truc après le pseudonyme de notre cher compatriote.");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            sender.sendMessage("Joueur nul");
            return false;
        }

        if (!target.isOnline()){
            sender.sendMessage("Joueur pas co");
        }

        player.openInventory((Inventory)target.getInventory());
        sender.sendMessage("Ouverture de l'inventaire de " + target.getName());
        return false;
    }
}
