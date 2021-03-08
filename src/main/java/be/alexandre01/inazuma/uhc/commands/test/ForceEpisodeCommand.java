package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ForceEpisodeCommand extends Command {

    public ForceEpisodeCommand(String s) {
        super(s);
        super.setPermission("uhc.episode");
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {

        Episode.addEpisode();
        sender.sendMessage("CHANGEMENT D'EPISODE PD");

        return false;
    }
}
