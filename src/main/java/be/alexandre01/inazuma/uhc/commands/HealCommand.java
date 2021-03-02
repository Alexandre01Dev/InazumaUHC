package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends Command {
    public HealCommand(String s) {
        super(s);
        super.setPermission("uhc.heal");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(args.length == 0){
            sender.sendMessage(Preset.instance.p.prefixName()+ " §cFait /heal ALL ou /heal [Player]");
        }

        if(args[0].equalsIgnoreCase("all")){
            for(Player player : InazumaUHC.get.getRemainingPlayers()){
                player.setHealth(player.getMaxHealth());
            }
            sender.sendMessage(Preset.instance.p.prefixName()+ " §aVous avez heal tout les joueurs connectés.");
        }
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null || !player.isOnline()){
            sender.sendMessage(Preset.instance.p.prefixName()+ " §cLe joueur n'est pas connecté ");
            return false;
        }
        sender.sendMessage(Preset.instance.p.prefixName()+ " §aVous avez heal "+ player.getName()+".");
        player.setHealth(player.getMaxHealth());
        return false;
    }
}
