package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.RejoinManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand extends Command {
    protected ReviveCommand(String s) {
        super(s);
        super.setUsage("§cUtilisez /revive [player]");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(args.length == 0){
            System.out.println(super.usageMessage);
        }

        Player player = Bukkit.getPlayer(args[0]);

        if(player != null){
            if(player.isOnline()){
                RejoinManager r = InazumaUHC.get.getRejoinManager();
                if(r.isValidPlayer(player)){
                    r.revivePlayer(player);
                    r.teleportRandom();
                    player.setExp(player.getExp()/2);
                    player.setLevel(player.getLevel()/2);
                }
            }
        }
        return false;
    }
}
