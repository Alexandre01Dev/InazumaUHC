package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.modules.Module;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.state.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ModuleCommand extends Command {

    public ModuleCommand(String s) {
        super(s);
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if (!sender.hasPermission("uhc.module")) {
            return false;
        }

        if(args.length == 0){

            sender.sendMessage("§eLes commandes disponibles pour les modules:");
            sender.sendMessage("§e- §9Module list");
            sender.sendMessage("§e- §9Module set [Module-Name]");
            sender.sendMessage("§e- §9Module reload [Module-Name]");
            return false;
        }
        if(args[0].equalsIgnoreCase("list")){
            for(Module m : Preset.instance.modules.values()){
                sender.sendMessage("§b- §a"+m.getModuleName()+" §b|§a "+ m.getVersion());
            }
            return false;
        }

        if(args[0].equalsIgnoreCase("set")){
            if(args.length < 2){
                sender.sendMessage("§e- §9Module set [Module-Name]");
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                String a = args[i];
                stringBuilder.append(a);
                if(i != args.length-1){
                    stringBuilder.append(" ");
                }
            }
            System.out.println(stringBuilder.toString());
            Collection<Module> ms = Preset.instance.modules.values().stream().filter(module -> module.getModuleName().equals(stringBuilder.toString())).collect(Collectors.toList());


            if(!ms.isEmpty()){
                Preset.instance.set(new ArrayList<>(ms).get(0));
                InazumaUHC.get.onLoadPreset();
                GameState.get().setTo(GameState.get().getState());
            }else {
                sender.sendMessage("§cModule non trouvé (Oops).");
            }
        }

        if(args[0].equalsIgnoreCase("reload")){
            if(args.length < 2){
                sender.sendMessage("§e- §9Module reload [Module-Name]");
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                String a = args[i];
                stringBuilder.append(a);
                if(i != args.length-1){
                    stringBuilder.append(" ");
                }
            }
            System.out.println(stringBuilder.toString());
            Collection<Module> ms = Preset.instance.modules.values().stream().filter(module -> module.getModuleName().equals(stringBuilder.toString())).collect(Collectors.toList());


            if(!ms.isEmpty()){
                if(!InazumaUHC.get.moduleLoader.reloadModule(new ArrayList<>(ms).get(0)))
                    sender.sendMessage("§cUn probleme est servenu :'(");
            }else {
                sender.sendMessage("§cModule non trouvé");
            }
        }

        return false;
    }
}
