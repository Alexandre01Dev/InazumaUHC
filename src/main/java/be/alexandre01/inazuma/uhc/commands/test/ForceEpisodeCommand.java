package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers.EpisodeTimeTimer;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers.EpisodeTimer;
import be.alexandre01.inazuma.uhc.timers.game.NetherTimer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Timer;

public class ForceEpisodeCommand extends Command {

    public ForceEpisodeCommand(String s) {
        super(s);
        super.setPermission("uhc.episode");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {

        InazumaUHC.get.tm.getTimer(EpisodeTimer.class).reset();
        InazumaUHC.get.tm.getTimer(EpisodeTimeTimer.class).reset();
        sender.sendMessage("CHANGEMENT D'EPISODE PD");

        return false;
    }
}
