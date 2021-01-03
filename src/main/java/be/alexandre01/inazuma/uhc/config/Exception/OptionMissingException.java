package be.alexandre01.inazuma.uhc.config.Exception;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Messages;
import org.bukkit.Bukkit;

public class OptionMissingException extends Exception{
    public OptionMissingException(String msg){
        super(msg);
        System.out.println("The plugin should be stopped due to an error ?.");
        System.out.println(this.getCause());
        Bukkit.getPluginManager().disablePlugin(InazumaUHC.get);
        return;
    }
}
