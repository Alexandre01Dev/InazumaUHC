package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.roles.Role;
import be.alexandre01.inazuma.uhc.timers.Timer;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.HashMap;


public class CompoCommand extends Command {
    IPreset preset;
    @Getter private static CompoCommand instance;
    public CompoCommand(String s) {
        super(s);
        super.setPermission("uhc.compo");
        this.preset = Preset.instance.p;
        instance = this;
    }
    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(!sender.hasPermission("uhc.compo")){
            sender.sendMessage(super.getPermissionMessage());
            return false;
        }

            for(Role role : Role.getRoles()){
                if(role != null){
                    if(role.getName() != null){
                        if(!role.getPlayers().isEmpty()){
                            role.getPlayers().forEach(player -> {
                                sender.sendMessage("§e- §7"+player.getName()+" est "+ role.getRoleCategory().getPrefixColor()+role.getName());
                            });

                        }

                    }
                }

            }
            return false;

    }

}
