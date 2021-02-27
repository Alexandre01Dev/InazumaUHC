package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.managers.RejoinManager;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand extends Command {
    public ReviveCommand(String s) {
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
                    player.sendMessage(Preset.instance.p.prefixName()+" §aVous venez d'être réssucité.");
                    InazumaUHC.get.invincibilityDamager.addPlayer(player, 1000*7);
                    player.sendMessage(Preset.instance.p.prefixName()+" §eVous avez 7 secondes d'invincibilité.");
                    r.teleportRandom(player);
                    player.setExp(player.getExp()/2);
                    player.setLevel(player.getLevel()/2);
                }else {
                    player.sendMessage(Preset.instance.p.prefixName()+" §cLe joueur ne peut pas se faire réssuciter.");
                }
            }else {
                player.sendMessage(Preset.instance.p.prefixName()+" §cLe joueur en question n'est pas connecté");
            }
        }else {
            player.sendMessage(Preset.instance.p.prefixName()+" §cLe joueur en question n'est pas connecté");
        }
        return false;
    }
}
