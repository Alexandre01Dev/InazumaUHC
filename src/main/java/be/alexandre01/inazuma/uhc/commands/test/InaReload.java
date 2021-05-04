package be.alexandre01.inazuma.uhc.commands.test;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.presets.Preset;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class InaReload extends Command {

    public InaReload(String s) {
        super(s);
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("uhc.reload")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }

        if(!InazumaUHC.get.moduleLoader.reloadModule(InazumaUHC.get.p.m))
            sender.sendMessage("§cUn probleme est servenu :'(");

        return false;
    }
}
