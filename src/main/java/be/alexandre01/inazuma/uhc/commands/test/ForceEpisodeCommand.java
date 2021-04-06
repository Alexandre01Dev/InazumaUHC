package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.objects.Episode;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers.EpisodeTimeTimer;
import be.alexandre01.inazuma.uhc.presets.inazuma_eleven.timers.EpisodeTimer;
import be.alexandre01.inazuma.uhc.timers.Timer;
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
        try {
            if(m.getChild() == null){
                c = Class.forName(m.getPresetPath()+".timers.EpisodeTimer");
               timer =  InazumaUHC.get.tm.getTimer(c);
               if(timer == null){
                   return false;
               }
               timer.reset();
                c = Class.forName(m.getPresetPath()+".timers.EpisodeTimeTimer");
                timer =  InazumaUHC.get.tm.getTimer(c);

                if(timer == null){

                    return false;
                }
                timer.reset();

            }else {
                c = Class.forName(m.getPresetPath()+".timers.EpisodeTimer", true, m.getChild());
                timer =  InazumaUHC.get.tm.getTimer(c);
                if(timer == null){
                    return false;
                }
                timer.reset();

                c = Class.forName(m.getPresetPath()+".timers.EpisodeTimeTimer", true, m.getChild());
                timer =  InazumaUHC.get.tm.getTimer(c);

                if(timer == null){
                    return false;
                }
                timer.reset();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
      
        sender.sendMessage("§bChangement d'episode.");

        return false;
    }
}
