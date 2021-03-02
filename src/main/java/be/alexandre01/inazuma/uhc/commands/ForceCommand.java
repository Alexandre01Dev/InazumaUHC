package be.alexandre01.inazuma.uhc.commands;

import be.alexandre01.inazuma.uhc.presets.IPreset;
import be.alexandre01.inazuma.uhc.presets.Preset;
import be.alexandre01.inazuma.uhc.presets.PresetData;
import be.alexandre01.inazuma.uhc.timers.Timer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;


public class ForceCommand extends Command {
    IPreset preset;
    private HashMap<String,String> varNames;
    private HashMap<String, Timer> timers;
    protected ForceCommand(String s) {
        super(s);
        this.preset = Preset.instance.p;
        this.varNames = new HashMap<>();
        this.timers = new HashMap<>();
    }

    public void addValue(String varName,String link,Timer timer){
        varNames.put(link.toLowerCase(),varName);
        timers.put(link.toLowerCase(),timer);
    }
    public void setValue(String varName, Object value){
        Class c = null;
        try {
            c = Class.forName("be.alexandre01.inazuma.uhc.presets."+preset.getPackageName()+"."+preset.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(c != null){
            try {
                for(Field f : c.getDeclaredFields()){
                    System.out.println("DeclaredF > "+f.getName());
                }
                for(Field f : c.getFields()){
                    System.out.println("F > "+f.getName());
                }
                Field field = c.getField(varName);
                field.set(preset,value);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean execute(CommandSender sender, String msg, String[] args) {
        if(args.length == 0){
            sender.sendMessage(preset.prefixName()+" §cFaites /force [module]");
            for(String varName : varNames.keySet()){
                sender.sendMessage("§e- §c/force "+ varName);
            }
            return false;
        }

        if(varNames.containsKey(args[0].toLowerCase())){
            setValue(varNames.get(args[0].toLowerCase()),5);
            Timer t = timers.get(args[0].toLowerCase());
            t = t.reset();

        }
        return false;
    }

}
