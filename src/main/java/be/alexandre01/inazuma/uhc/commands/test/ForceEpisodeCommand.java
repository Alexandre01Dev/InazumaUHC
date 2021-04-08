package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.presets.Preset;

import be.alexandre01.inazuma.uhc.timers.Timer;
import be.alexandre01.inazuma.uhc.timers.game.EpisodeTimeTimer;
import be.alexandre01.inazuma.uhc.timers.game.EpisodeTimer;
import be.alexandre01.inazuma.uhc.timers.game.NetherTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;



public class ForceEpisodeCommand extends Command {

    public ForceEpisodeCommand(String s) {
        super(s);
        super.setPermission("uhc.episode");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("uhc.episode")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }
        Module m = Preset.instance.m;
        Class<?> c;
        Timer timer;
        InazumaUHC.get.tm.getTimer(EpisodeTimer.class).reset();
        InazumaUHC.get.tm.getTimer(EpisodeTimeTimer.class).reset();
        sender.sendMessage("CHANGEMENT D'EPISODE");
        
      
        sender.sendMessage("§bChangement d'episode.");

        return false;
    }
}
